package com.park.web.user.controller;


import java.util.Date;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.social.google.connect.GoogleOAuth2Template;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.park.web.admin.service.AdminService;
import com.park.web.exception.NotMatchedLoginException;
import com.park.web.interceptor.SessionName;
import com.park.web.login.db.LoginDTO;
import com.park.web.oauth.KakaoApi;
import com.park.web.oauth.SnsLogin;
import com.park.web.oauth.SnsValue;
import com.park.web.user.db.UserVO;
import com.park.web.user.service.UserService;

@Controller
@RequestMapping("/login")
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Inject
	private UserService userservice;
	
	@Inject
	private AdminService aservice;
	
	@Inject
	private SnsValue snsNaver;
	@Inject
	private SnsValue snsGoogle;
	@Inject
	private SnsValue snsKakao;
	
	@Inject
	private GoogleOAuth2Template googleOAuth2Template;
	@Inject
	private OAuth2Parameters googleOauth2Parameters;
	
	

	@RequestMapping(value="/auth/{snsService}/callback", method= {RequestMethod.GET, RequestMethod.POST})
	public String snsLogin(@PathVariable String snsService, @RequestParam String code, Model model, 
			HttpSession session, HttpServletRequest request, RedirectAttributes rttr) throws Exception {
		logger.info("snsLogin : service : " + snsService);
		
		
		SnsValue sns = null;
		if(StringUtils.equals("naver", snsService)) {
			sns = snsNaver;
		}
		else if(StringUtils.equals("google", snsService)) {
			sns = snsGoogle;
		}
		else if(StringUtils.equals("kakao", snsService)) {
			sns = snsKakao;
		}

		
		//카카오 로그인
		if(snsService.equals("kakao")) {
			//code 이용 access_token 받음
			JsonNode node = KakaoApi.getAccessToken(code);
			JsonNode accessToken = node.get("access_token");
			
			//access_token 으로 사용자 profile 가져옴  => 사용자 정보
			JsonNode userInfo = KakaoApi.getKakaoUserProfile(accessToken);
			
			//파싱
			Gson gson = new Gson();
			UserVO userVO = gson.fromJson(userInfo.toString(), UserVO.class);
	
			//get 유저정보
			JsonNode id = userInfo.path("id"); 
			JsonNode properties = userInfo.path("properties");
			JsonNode kakao_account = userInfo.path("kakao_account");
			
			String kakao_id = id.asText();
			String kakao_name = properties.path("nickname").asText();
			String kakao_email = kakao_account.path("email").asText();
			
			userVO.setId(kakao_id);
			userVO.setName(kakao_name);
			userVO.setEmail(kakao_email);
		
			//DB 해당 유저 존재 체크
			UserVO user = userservice.getByKakao(userVO);
			
			if(user == null) {
				//유저 info 없을 경우 가입 페이지
				rttr.addAttribute("result", "존재하지 않는 회원입니다. 가입 후 이용해주세요");
				rttr.addFlashAttribute("member_section", "kakao");
				session.setAttribute("snsUser", userVO);
				
				return "redirect:/memberShip/memberShipJoinSns";
			}
			else {
				//정지 기한 끝난 후 정지 해제
				aservice.userSuspendClear(aservice.getSuspendUserInfo(user.getId()));
				
				//정지 된 회원
				if(aservice.getSuspendUserInfo(user.getId()) != null) {
					
					if(aservice.isAccountSuspend(user.getId()).equals("Y")) {
						
						rttr.addFlashAttribute("errormsg", 
								"제재된 회원입니다." +
										"\r\n ( 내용 : " + aservice.getSuspendUserInfo(user.getId()).getSuspend_term() + "일 정지 )");
						
						return "redirect:/login/login";
					}
					
				}
				else {
					
					//유저 정보 존재 시 로그인
					//Remember Me
					Date expired = new Date(System.currentTimeMillis() + SessionName.EXPIRE * 1000);
					userservice.loginSession(userVO.getId(), session.getId(), expired);
					
					session.setAttribute(SessionName.LOGIN, user);
				
				}
			}
			return "redirect:/main/mainPage";
			
		}
		//구글 로그인
		else if(snsService.equals("google")) {
			 
	    	MultiValueMap<String, String> param = new LinkedMultiValueMap<String, String>();
	    	param.add("grant_type", "authorization_code");
	    	param.add("client_id", "********************");
	    	param.add("client_secret", "**************");
	    	param.add("redirect_uri", "http://chparkland.com/park_project_1/login/auth/google/callback");
	    	param.add("code", code);
	    	
	    	//RestTemplate 이용 Access Token , profile 요청
	    	RestTemplate restTemplate = new RestTemplate();
	    	
	    	HttpHeaders headers = new HttpHeaders();
	    	headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
	    	
	    	HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String,String>>(param, headers);
	    	@SuppressWarnings("rawtypes")
			ResponseEntity<Map> responseEntity = restTemplate.exchange("https://www.googleapis.com/oauth2/v4/token", HttpMethod.POST, requestEntity, Map.class);	
	    	@SuppressWarnings("unchecked")
			Map<String, Object> responseMap = responseEntity.getBody();
	    	
	    	//id_token : 사용자 정보
	    	//받아온 결과는 JWT(Json Web Token)형식
	    	//accessToken[1] : 사용자 정보 
	    	String[] accessToken = ((String) responseMap.get("id_token")).split("\\.");
	    	
	    	Base64 base64 = new Base64(true);
	        String body = new String(base64.decode(accessToken[1]), "utf-8");
	        
	        ObjectMapper mapper = new ObjectMapper();
			JsonNode node = mapper.readTree(body);
	        System.out.println("node : " + node);
	        
	        //파싱
			Gson gson = new Gson();
			UserVO userVO = gson.fromJson(node.toString(), UserVO.class);
	        
			//get 유저정보
	        String google_id = node.path("sub").asText();
			String google_name = node.get("family_name").asText() + node.get("given_name").asText();
			
			userVO.setId(google_id);
			userVO.setName(google_name);
			
			
			//DB 해당 유저 존재 체크
			UserVO user = userservice.getByGoogle(userVO);
			
			if(user == null) {
				//유저 info 없을 경우 가입 페이지
				rttr.addAttribute("result", "존재하지 않는 회원입니다. 가입 후 이용해주세요");
				rttr.addFlashAttribute("member_section", "google");
				session.setAttribute("snsUser", userVO);
				
				return "redirect:/memberShip/memberShipJoinSns";
			}
			else {
				//정지 기한 끝난 후 정지 해제
				aservice.userSuspendClear(aservice.getSuspendUserInfo(user.getId()));
				
				//정지 된 회원
				if(aservice.getSuspendUserInfo(user.getId()) != null) {
					
					if(aservice.isAccountSuspend(user.getId()).equals("Y")) {
						
						rttr.addFlashAttribute("errormsg", 
								"제재된 회원입니다." +
										"\r\n ( 내용 : " + aservice.getSuspendUserInfo(user.getId()).getSuspend_term() + "일 정지 )");
						
						return "redirect:/login/login";
					}
					
				}else {					
					//유저 정보 존재 시 로그인
					//Remember Me
					Date expired = new Date(System.currentTimeMillis() + SessionName.EXPIRE * 1000);
					userservice.loginSession(userVO.getId(), session.getId(), expired);
					
					session.setAttribute(SessionName.LOGIN, user);
				}
			}
			
			return "redirect:/main/mainPage";
			
		}
		else {
			//네이버 로그인
			//code 이용 access_token 받음 -> access_token 으로 사용자 profile 가져옴
			SnsLogin snsLogin = new SnsLogin(sns);
			UserVO userVO = snsLogin.getUserProfile(code);
			
			System.out.println("Profile : " + userVO);
			
			//DB 해당 유저 존재 체크
			UserVO user = userservice.getBySns(userVO);
			 
			if(user == null) {
				//유저 info 없을 경우 가입 페이지
				rttr.addAttribute("result", "존재하지 않는 회원입니다. 가입 후 이용해주세요");
				rttr.addFlashAttribute("member_section", "naver");
				session.setAttribute("snsUser", userVO);
		
				return "redirect:/memberShip/memberShipJoinSns";
			}
			else {
				//정지 기한 끝난 후 정지 해제
				aservice.userSuspendClear(aservice.getSuspendUserInfo(user.getId()));	
				
				//정지 된 회원
				if(aservice.getSuspendUserInfo(user.getId()) != null) {
					
					if(aservice.isAccountSuspend(user.getId()).equals("Y")) {
						
						rttr.addFlashAttribute("errormsg", 
								"제재된 회원입니다." +
										"\r\n ( 내용 : " + aservice.getSuspendUserInfo(user.getId()).getSuspend_term() + "일 정지 )");
						
						return "redirect:/login/login";
					}
					
				}
				else {
					
					//유저 정보 존재 시 로그인
					//Remember Me
					Date expired = new Date(System.currentTimeMillis() + SessionName.EXPIRE * 1000);
					userservice.loginSession(user.getId(), session.getId(), expired);
					
					session.setAttribute(SessionName.LOGIN, user);
				}
			}
		}
		
		return "redirect:/main/mainPage";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public void login(LoginDTO loginDTO, Model model, HttpSession session) throws Exception {
		logger.info("login : get");
		
		//네이버
		SnsLogin snsLoginNaver = new SnsLogin(snsNaver);
		model.addAttribute("naverUrl", snsLoginNaver.getNaverAuth());
		
		//구글
		String url = googleOAuth2Template.buildAuthorizeUrl(GrantType.AUTHORIZATION_CODE, googleOauth2Parameters);
		
		model.addAttribute("googleUrl", url);
		
		//카카오
		String kakaoUrl = KakaoApi.getAuthorizationUrl();
		model.addAttribute("kakaoUrl", kakaoUrl);
		
	}
	
	@RequestMapping(value="/postLogin", method=RequestMethod.POST)
	public ModelAndView postLogin(@ModelAttribute("loginDTO") @Valid LoginDTO loginDTO, BindingResult bindingResult, Model model, HttpSession session) throws Exception {
		logger.info("PostLogin : loginDTO >>" + loginDTO);
		
		if(bindingResult.hasErrors()) {
			ModelAndView mav = new ModelAndView("login/login");
			return mav;
		}
		
		//login
		try {	
			UserVO user = userservice.login(loginDTO);
			
			//로그인 성공
			if(user != null && BCrypt.checkpw(loginDTO.getPw(), user.getPassword())) {
				System.out.println("user != null");
				
				//계정 잠긴 경우 10분 후 해제 
				userservice.loginSuccessResetAfter(loginDTO.getId());
				
				//정지 기한 끝난 후 정지 해제
				aservice.userSuspendClear(aservice.getSuspendUserInfo(loginDTO.getId()));
				
				//로그인 시도 방지
				if(userservice.isAccountLock(loginDTO.getId()).equals("Y") ) {
					bindingResult.rejectValue("pw", "Locked", "계정이 비활성화 되었습니다. 잠시 후 다시 로그인 해주세요.");
					
					ModelAndView mav = new ModelAndView("login/login");				
					return mav;
			
				}
				else {
					
					//정지 된 회원
					if(aservice.getSuspendUserInfo(loginDTO.getId()) != null) {
						
						if(aservice.isAccountSuspend(loginDTO.getId()).equals("Y")) {
							bindingResult.rejectValue("pw", "Suspend", 
								"제재된 회원입니다." +
								"\r\n ( 내용 : " + aservice.getSuspendUserInfo(loginDTO.getId()).getSuspend_term() + "일 정지 )");
							
							ModelAndView mav = new ModelAndView("login/login");				
							return mav;
						}
						else {
							//Remember Me
							Date expired = new Date(System.currentTimeMillis() + SessionName.EXPIRE * 1000);
							userservice.loginSession(user.getId(), session.getId(), expired);
							
							model.addAttribute("user", user);
							
						}
					}
					
					//실패 횟수 리셋
					userservice.loginSuccessReset(loginDTO.getId());
					
					//Remember Me
					Date expired = new Date(System.currentTimeMillis() + SessionName.EXPIRE * 1000);
					userservice.loginSession(user.getId(), session.getId(), expired);
					
					model.addAttribute("user", user);
				
				}
				
				
			} //로그인 실패
			else if(user == null || !BCrypt.checkpw(loginDTO.getPw(), user.getPassword())){
				System.out.println("user == null");
				
				//계정 잠긴 경우 10분 후 해제 
				userservice.loginSuccessResetAfter(loginDTO.getId());
				
				//로그인 시도 시
				userservice.loginFailCnt(loginDTO.getId());
				userservice.updateLoginAccountLock(loginDTO.getId());
				
				if(user == null) {
					bindingResult.rejectValue("pw", "notUser", "존재하지 않는 아이디 입니다.");
				}
				else {
					
					if(userservice.isAccountLock(loginDTO.getId()).equals("N")) {
						bindingResult.rejectValue("pw", "notMatch", "아이디 또는 비밀번호가 일치하지 않습니다.");
					}				
					else if(userservice.isAccountLock(loginDTO.getId()).equals("Y") ) {
						//로그인 시도 방지
						bindingResult.rejectValue("pw", "Locked", "계정이 비활성화 되었습니다. 잠시 후 다시 로그인 해주세요.");
					}
				}
					
				ModelAndView mav = new ModelAndView("login/login");
				return mav;
				
			}
			ModelAndView mav = new ModelAndView("login/login");
			return mav;
			
		}
		catch(NotMatchedLoginException e) {
			bindingResult.rejectValue("pw", "notMatch", "아이디 또는 비밀번호가 일치하지 않습니다.");
			ModelAndView mav = new ModelAndView("login/login");
			return mav;
		}
	
		
	}
	
	
	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public String logout(LoginDTO loginDTO, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception  {
		logger.info("logout : get");
		
		session.removeAttribute(SessionName.LOGIN);
		session.invalidate();
		
		Cookie loginCookie = WebUtils.getCookie(request, SessionName.LOGIN);
		
		if(loginCookie != null) {
			loginCookie.setPath("/");
			loginCookie.setMaxAge(0);
			
			response.addCookie(loginCookie);
			
			UserVO user = (UserVO) session.getAttribute(SessionName.LOGIN);
			userservice.loginSession(user.getId(), session.getId(), new Date());
		}
		
		return "forward:/main/mainPage";
	}
	
	
	
	/* AJAX */
	/*
	@ResponseBody
	@RequestMapping(value="/loginAjax", method=RequestMethod.POST)
	public ResponseEntity<UserVO> loginAjax(@RequestBody LoginDTO loginDTO, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		logger.info("loginAjax");
		
		try {
			UserVO user = userservice.login(loginDTO);
			
			if(user != null) {
				user.setPassword(null);
	
				session.setAttribute("loginUser", user);
				
				Cookie loginCookie = new Cookie("loginCookie", session.getId());
				loginCookie.setPath("/");
				loginCookie.setMaxAge(7 * 24 * 60 * 60);
				response.addCookie(loginCookie);
				
				return new ResponseEntity<>(user, HttpStatus.OK);
				
			}
			else {
				return new ResponseEntity<>(user, HttpStatus.UNAUTHORIZED);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}
	
	
	@ResponseBody
	@RequestMapping(value="/logoutAjax", method=RequestMethod.GET)
	public ResponseEntity<String> logoutAjax(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		logger.info("logoutAjax : " + session.getAttribute("loginUser"));
		session.removeAttribute("loginUser");
		
		UserVO user = (UserVO) session.getAttribute(SessionName.LOGIN);
		
		if(user != null) {
			session.removeAttribute(SessionName.LOGIN);
			session.invalidate();
			
			Cookie loginCookie = WebUtils.getCookie(request, "loginCookie");
			if(loginCookie != null) {
				loginCookie.setPath("/");
				loginCookie.setMaxAge(0);
				response.addCookie(loginCookie);
			}
		}
		
		return new ResponseEntity<>("logoutA", HttpStatus.OK);
	}
	
	*/
	
	
	
	
	
	
}
