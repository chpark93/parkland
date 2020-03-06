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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

		
		//īī�� �α���
		if(snsService.equals("kakao")) {
			//code �̿� access_token ����
			JsonNode node = KakaoApi.getAccessToken(code);
			JsonNode accessToken = node.get("access_token");
			
			//access_token ���� ����� profile ������  => ����� ����
			JsonNode userInfo = KakaoApi.getKakaoUserProfile(accessToken);
			
			//�Ľ�
			Gson gson = new Gson();
			UserVO userVO = gson.fromJson(userInfo.toString(), UserVO.class);
	
			//get ��������
			JsonNode id = userInfo.path("id"); 
			JsonNode properties = userInfo.path("properties");
			JsonNode kakao_account = userInfo.path("kakao_account");
			
			String kakao_id = id.asText();
			String kakao_name = properties.path("nickname").asText();
			String kakao_email = kakao_account.path("email").asText();
			
			userVO.setId(kakao_id);
			userVO.setName(kakao_name);
			userVO.setEmail(kakao_email);
		
			//DB �ش� ���� ���� üũ
			UserVO user = userservice.getByKakao(userVO);
			
			if(user == null) {
				//���� info ���� ��� ���� ������
				rttr.addAttribute("result", "�������� �ʴ� ȸ���Դϴ�. ���� �� �̿����ּ���");
				rttr.addFlashAttribute("member_section", "kakao");
				session.setAttribute("snsUser", userVO);
				
				return "redirect:/memberShip/memberShipJoinSns";
			}
			else {
				//���� ���� ���� �� ���� ����
				aservice.userSuspendClear(aservice.getSuspendUserInfo(user.getId()));
				
				//���� �� ȸ��
				if(aservice.getSuspendUserInfo(user.getId()) != null) {
					
					if(aservice.isAccountSuspend(user.getId()).equals("Y")) {
						
						rttr.addFlashAttribute("errormsg", 
								"����� ȸ���Դϴ�." +
										"\r\n ( ���� : " + aservice.getSuspendUserInfo(user.getId()).getSuspend_term() + "�� ���� )");
						
						return "redirect:/login/login";
					}
					
				}
				else {
					
					//���� ���� ���� �� �α���
					//Remember Me
					Date expired = new Date(System.currentTimeMillis() + SessionName.EXPIRE * 1000);
					userservice.loginSession(userVO.getId(), session.getId(), expired);
					
					session.setAttribute(SessionName.LOGIN, user);
				
				}
			}
			return "redirect:/main/mainPage";
			
		}
		//���� �α���
		else if(snsService.equals("google")) {
			 
	    	MultiValueMap<String, String> param = new LinkedMultiValueMap<String, String>();
	    	param.add("grant_type", "authorization_code");
	    	param.add("client_id", "*************************");
	    	param.add("client_secret", "*************************");
	    	param.add("redirect_uri", "http://chparkland.com/park_project_1/login/auth/google/callback");
	    	param.add("code", code);
	    	
	    	//RestTemplate �̿� Access Token , profile ��û
	    	RestTemplate restTemplate = new RestTemplate();
	    	
	    	HttpHeaders headers = new HttpHeaders();
	    	headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
	    	
	    	HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String,String>>(param, headers);
	    	@SuppressWarnings("rawtypes")
			ResponseEntity<Map> responseEntity = restTemplate.exchange("https://www.googleapis.com/oauth2/v4/token", HttpMethod.POST, requestEntity, Map.class);	
	    	@SuppressWarnings("unchecked")
			Map<String, Object> responseMap = responseEntity.getBody();
	    	
	    	//id_token : ����� ����
	    	//�޾ƿ� ����� JWT(Json Web Token)����
	    	//accessToken[1] : ����� ���� 
	    	String[] accessToken = ((String) responseMap.get("id_token")).split("\\.");
	    	
	    	Base64 base64 = new Base64(true);
	        String body = new String(base64.decode(accessToken[1]), "utf-8");
	        
	        ObjectMapper mapper = new ObjectMapper();
			JsonNode node = mapper.readTree(body);
	        System.out.println("node : " + node);
	        
	        //�Ľ�
			Gson gson = new Gson();
			UserVO userVO = gson.fromJson(node.toString(), UserVO.class);
	        
			//get ��������
	        String google_id = node.path("sub").asText();
			String google_name = node.get("family_name").asText() + node.get("given_name").asText();
			
			userVO.setId(google_id);
			userVO.setName(google_name);
			
			
			//DB �ش� ���� ���� üũ
			UserVO user = userservice.getByGoogle(userVO);
			
			if(user == null) {
				//���� info ���� ��� ���� ������
				rttr.addAttribute("result", "�������� �ʴ� ȸ���Դϴ�. ���� �� �̿����ּ���");
				rttr.addFlashAttribute("member_section", "google");
				session.setAttribute("snsUser", userVO);
				
				return "redirect:/memberShip/memberShipJoinSns";
			}
			else {
				//���� ���� ���� �� ���� ����
				aservice.userSuspendClear(aservice.getSuspendUserInfo(user.getId()));
				
				//���� �� ȸ��
				if(aservice.getSuspendUserInfo(user.getId()) != null) {
					
					if(aservice.isAccountSuspend(user.getId()).equals("Y")) {
						
						rttr.addFlashAttribute("errormsg", 
								"����� ȸ���Դϴ�." +
										"\r\n ( ���� : " + aservice.getSuspendUserInfo(user.getId()).getSuspend_term() + "�� ���� )");
						
						return "redirect:/login/login";
					}
					
				}else {					
					//���� ���� ���� �� �α���
					//Remember Me
					Date expired = new Date(System.currentTimeMillis() + SessionName.EXPIRE * 1000);
					userservice.loginSession(userVO.getId(), session.getId(), expired);
					
					session.setAttribute(SessionName.LOGIN, user);
				}
			}
			
			return "redirect:/main/mainPage";
			
		}
		else {
			//���̹� �α���
			//code �̿� access_token ���� -> access_token ���� ����� profile ������
			SnsLogin snsLogin = new SnsLogin(sns);
			UserVO userVO = snsLogin.getUserProfile(code);
			
			System.out.println("Profile : " + userVO);
			
			//DB �ش� ���� ���� üũ
			UserVO user = userservice.getBySns(userVO);
			 
			if(user == null) {
				//���� info ���� ��� ���� ������
				rttr.addAttribute("result", "�������� �ʴ� ȸ���Դϴ�. ���� �� �̿����ּ���");
				rttr.addFlashAttribute("member_section", "naver");
				session.setAttribute("snsUser", userVO);
		
				return "redirect:/memberShip/memberShipJoinSns";
			}
			else {
				//���� ���� ���� �� ���� ����
				aservice.userSuspendClear(aservice.getSuspendUserInfo(user.getId()));	
				
				//���� �� ȸ��
				if(aservice.getSuspendUserInfo(user.getId()) != null) {
					
					if(aservice.isAccountSuspend(user.getId()).equals("Y")) {
						
						rttr.addFlashAttribute("errormsg", 
								"����� ȸ���Դϴ�." +
										"\r\n ( ���� : " + aservice.getSuspendUserInfo(user.getId()).getSuspend_term() + "�� ���� )");
						
						return "redirect:/login/login";
					}
					
				}
				else {
					
					//���� ���� ���� �� �α���
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
		
		//���̹�
		SnsLogin snsLoginNaver = new SnsLogin(snsNaver);
		model.addAttribute("naverUrl", snsLoginNaver.getNaverAuth());
		
		//����
		String url = googleOAuth2Template.buildAuthorizeUrl(GrantType.AUTHORIZATION_CODE, googleOauth2Parameters);
		
		model.addAttribute("googleUrl", url);
		
		//īī��
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
			
			//�α��� ����
			if(user != null && BCrypt.checkpw(loginDTO.getPw(), user.getPassword())) {
				System.out.println("user != null");
				
				//���� ��� ��� 10�� �� ���� 
				userservice.loginSuccessResetAfter(loginDTO.getId());
				
				//���� ���� ���� �� ���� ����
				aservice.userSuspendClear(aservice.getSuspendUserInfo(loginDTO.getId()));
				
				//�α��� �õ� ����
				if(userservice.isAccountLock(loginDTO.getId()).equals("Y") ) {
					bindingResult.rejectValue("pw", "Locked", "������ ��Ȱ��ȭ �Ǿ����ϴ�. ��� �� �ٽ� �α��� ���ּ���.");
					
					ModelAndView mav = new ModelAndView("login/login");				
					return mav;
			
				}
				else {
					
					//���� �� ȸ��
					if(aservice.getSuspendUserInfo(loginDTO.getId()) != null) {
						
						if(aservice.isAccountSuspend(loginDTO.getId()).equals("Y")) {
							bindingResult.rejectValue("pw", "Suspend", 
								"����� ȸ���Դϴ�." +
								"\r\n ( ���� : " + aservice.getSuspendUserInfo(loginDTO.getId()).getSuspend_term() + "�� ���� )");
							
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
					
					//���� Ƚ�� ����
					userservice.loginSuccessReset(loginDTO.getId());
					
					//Remember Me
					Date expired = new Date(System.currentTimeMillis() + SessionName.EXPIRE * 1000);
					userservice.loginSession(user.getId(), session.getId(), expired);
					
					model.addAttribute("user", user);
				
				}
				
				
			} //�α��� ����
			else if(user == null || !BCrypt.checkpw(loginDTO.getPw(), user.getPassword())){
				System.out.println("user == null");
				
				//���� ��� ��� 10�� �� ���� 
				userservice.loginSuccessResetAfter(loginDTO.getId());
				
				//�α��� �õ� ��
				userservice.loginFailCnt(loginDTO.getId());
				userservice.updateLoginAccountLock(loginDTO.getId());
				
				if(user == null) {
					bindingResult.rejectValue("pw", "notUser", "�������� �ʴ� ���̵� �Դϴ�.");
				}
				else {
					
					if(userservice.isAccountLock(loginDTO.getId()).equals("N")) {
						bindingResult.rejectValue("pw", "notMatch", "���̵� �Ǵ� ��й�ȣ�� ��ġ���� �ʽ��ϴ�.");
					}				
					else if(userservice.isAccountLock(loginDTO.getId()).equals("Y") ) {
						//�α��� �õ� ����
						bindingResult.rejectValue("pw", "Locked", "������ ��Ȱ��ȭ �Ǿ����ϴ�. ��� �� �ٽ� �α��� ���ּ���.");
					}
				}
					
				ModelAndView mav = new ModelAndView("login/login");
				return mav;
				
			}
			ModelAndView mav = new ModelAndView("login/login");
			return mav;
			
		}
		catch(NotMatchedLoginException e) {
			bindingResult.rejectValue("pw", "notMatch", "���̵� �Ǵ� ��й�ȣ�� ��ġ���� �ʽ��ϴ�.");
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
	
	
	
	
	
	
}
