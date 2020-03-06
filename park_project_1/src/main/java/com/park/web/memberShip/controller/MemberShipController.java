package com.park.web.memberShip.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.park.web.interceptor.SessionName;
import com.park.web.login.db.LoginDTO;
import com.park.web.memberShip.db.MemberShipDetailVO;
import com.park.web.memberShip.db.MemberShipSnsVO;
import com.park.web.memberShip.db.MemberShipVO;
import com.park.web.memberShip.service.MemberShipService;
import com.park.web.memberShip.util.FindPasswordValidator;
import com.park.web.user.db.UserVO;
import com.park.web.user.service.UserService;

@Controller
@RequestMapping(value="/memberShip")
public class MemberShipController {
	 
	@Inject
	private MemberShipService msservice;
	
	@Inject
	private UserService userservice;
	
	
	//ȸ������
	@RequestMapping(value="/memberShipJoin", method=RequestMethod.GET)
	public String memberShipJoin(MemberShipVO memberShipVO, Model model) throws Exception {
		
		model.addAttribute("member_section", "member");
		return "/memberShip/memberShipJoin";
	}
	
	//ȸ������(SNS)
	@RequestMapping(value="/memberShipJoinSns", method=RequestMethod.GET)
	public String memberShipJoinSns(MemberShipSnsVO memberShipSnsVO, Model model) throws Exception {
		
		return "/memberShip/memberShipJoinSns";
	}
	
	@RequestMapping(value="/memberRegister", method=RequestMethod.POST)
	public String memberRegister(@ModelAttribute("memberShipVO") @Valid MemberShipVO memberShipVO, BindingResult bindingResult, 
			MemberShipDetailVO memberShipDetailVO, Model model, HttpServletResponse response) throws Exception {
		
		if(bindingResult.hasErrors()) {
			return "/memberShip/memberShipJoin";
		}
		
		//id �ߺ� üũ
		if(msservice.checkId(memberShipVO.getId())) {
			//0���� Ŭ ��� �ߺ�(true)
			model.addAttribute("idCheck", "�ߺ��� ���̵� �Դϴ�");
			
			return "/memberShip/memberShipJoin";
		}
		
		//email �ߺ� üũ
		if(msservice.checkEmail(memberShipVO.getEmail())) {
			//0 ���� Ŭ ��� �ߺ�(true)
			model.addAttribute("emailCheck", "�ߺ��� �̸��� �Դϴ�");
			return "/memberShip/memberShipJoin";
		}
		
		//nickname �ߺ� üũ
		if(msservice.checkNickName(memberShipVO.getNickname())) {
			//0 ���� Ŭ ��� �ߺ�(true)
			model.addAttribute("nickNameCheck", "�ߺ��� �г��� �Դϴ�");
			return "/memberShip/memberShipJoin";
		}
		
		//��й�ȣ üũ
		if(!memberShipVO.checkPassword()) {
			model.addAttribute("pwCheck", "��й�ȣ�� ��ġ���� �ʽ��ϴ�");
			return "/memberShip/memberShipJoin";
		}
		
		//��й�ȣ ��ȣȭ
		String bcryptPw = BCrypt.hashpw(memberShipVO.getPassword(), BCrypt.gensalt()); 
		memberShipVO.setPassword(bcryptPw);
		
		msservice.register(memberShipVO, response);
		msservice.registerDetail(memberShipDetailVO);
		
		return "redirect:/login/login";
		
	}
	
	//ȸ�� ����(SNS)
	@RequestMapping(value="/memberRegisterSns", method=RequestMethod.POST)
	public String memberRegisterSns(@ModelAttribute("memberShipSnsVO") @Valid MemberShipSnsVO memberShipSnsVO, BindingResult bindingResult,
			MemberShipDetailVO memberShipDetailVO, Model model) throws Exception {
		
		if(bindingResult.hasErrors()) {
			return "/memberShip/memberShipJoinSns";
		}
		
		//id �ߺ� üũ
		if(msservice.checkId(memberShipSnsVO.getId())) {
			//0���� Ŭ ��� �ߺ�(true)
			model.addAttribute("idCheck", "�ߺ��� ���̵� �Դϴ�");
			
			return "/memberShip/memberShipJoinSns";
		}
		
		//email �ߺ� üũ
		if(msservice.checkEmail(memberShipSnsVO.getEmail())) {
			//0 ���� Ŭ ��� �ߺ�(true)
			model.addAttribute("emailCheck", "�ߺ��� �̸��� �Դϴ�");
			return "/memberShip/memberShipJoinSns";
		}
		
		//nickname �ߺ� üũ
		if(msservice.checkNickName(memberShipSnsVO.getNickname())) {
			//0 ���� Ŭ ��� �ߺ�(true)
			model.addAttribute("nickNameCheck", "�ߺ��� �г��� �Դϴ�");
			return "/memberShip/memberShipJoinSns";
		}
		
		msservice.registerSNS(memberShipSnsVO);
		msservice.registerDetail(memberShipDetailVO);
		
		return "redirect:/login/login";
		
	}
	
	//id �ߺ�üũ(AJAX)    
	@RequestMapping(value="/checkId", method=RequestMethod.POST)
	public boolean checkId(@RequestParam("id") String id) throws Exception {
		return msservice.checkId(id);
	}
	//email �ߺ�üũ(AJAX)
	@RequestMapping(value="/checkEmail", method=RequestMethod.POST)
	public boolean checkEmail(@RequestParam("email") String email) throws Exception {
		return msservice.checkEmail(email);
	}
	
	
	//ȸ������ ����
	@RequestMapping(value="/approvalStatus", method= {RequestMethod.GET, RequestMethod.POST})
	public String approvalStatus(@ModelAttribute MemberShipVO memberShipVO) throws Exception {
		msservice.approvalStatus(memberShipVO);
	
		return "memberShip/memberShipSuccess";
	}
	
	
	//���̵� ã��(member)
	@RequestMapping(value="/findIdForm" ,method=RequestMethod.GET)
	public String findIdForm(MemberShipVO memberShipVO) throws Exception {
		return "/memberShip/findIdForm";
	}
	@RequestMapping(value="/findId", method=RequestMethod.POST)
	public String findId(@ModelAttribute MemberShipVO memberShipVO, Model model) throws Exception {
			model.addAttribute("memberShipVO", msservice.findByEmail(memberShipVO));
			
			return "/memberShip/findIdResult";
	}
	
	//��й�ȣ ã��(member)
	@RequestMapping(value="/findPwForm", method=RequestMethod.GET)
	public String findPwForm(MemberShipVO memberShipVO) throws Exception {
		return "/memberShip/findPwForm";
	}
	@RequestMapping(value="/findPw", method= RequestMethod.POST)
	public String findPw(@ModelAttribute MemberShipVO memberShipVO, Errors errors, HttpServletResponse response, 
			RedirectAttributes rttr) throws Exception {
		
		new FindPasswordValidator().validate(memberShipVO, errors);
		
		if(errors.hasErrors()) {
			return "memberShip/findPwForm";
		}

		
		if(msservice.findByEmail(memberShipVO) != null) {
			
			if(msservice.findByEmail(memberShipVO).getPassword() == null) {
				
				errors.rejectValue("email","SnsLogin", "SNS�� ������ ȸ�� �Դϴ�.");
				return "memberShip/findPwForm";
			}
			else {
				
				MemberShipVO result = msservice.execute(memberShipVO);
				rttr.addFlashAttribute("result", result);
				
				msservice.findPw(memberShipVO, response);
				
				return "redirect:/login/login";
			}
		}
		else {
			errors.rejectValue("email","emailNotExist", "�������� �ʴ� �̸��� �Դϴ�.");
			
			return "memberShip/findPwForm";
		}
		
		
	}
	
	
	
	//ȸ�� ���� ����
	@RequestMapping(value="/modifyPage", method=RequestMethod.GET)
	public String modifyPage(UserVO userVO, String id, Model model, HttpSession session) throws Exception {
		UserVO loginUser = (UserVO) session.getAttribute(SessionName.LOGIN);
		
		if(loginUser != null) {
			model.addAttribute("user", msservice.getUserInfo(loginUser.getId()));
			
			//�������
			String birth = msservice.getUserInfo(loginUser.getId()).getBirth();
			model.addAttribute("birth", birth);
							
		}
		return "/memberShip/memberShipDetail";
	}
	@RequestMapping(value="/updateUserInfo", method=RequestMethod.POST)
	public String updateUserInfo(@ModelAttribute("userVO") UserVO userVO, Model model, HttpSession session, 
			RedirectAttributes rttr, BindingResult bindingResult) throws Exception {
		
		if(bindingResult.hasErrors()) {
			return "/memberShip/memberShipDetail";
		}
		
		UserVO loginUser = (UserVO) session.getAttribute(SessionName.LOGIN);
		
		//nickname �ߺ� üũ
		if(msservice.checkNickName(userVO.getNickname())) {
			//0 ���� Ŭ ��� �ߺ�(true)
			rttr.addFlashAttribute("nickNameCheck", "�ߺ��� �г��� �Դϴ�");
		}
		else {			
			msservice.updateUserInfo(userVO);
			
			loginUser.setNickname(userVO.getNickname());
		}
		
		return "redirect:/main/mainPage";
	}
	
	
	//���� ������ �̹��� ����
	@RequestMapping(value="/updateUserProfilePage", method=RequestMethod.GET)
	public String updateUserProfilePage(UserVO userVO, String id, Model model, HttpSession session) throws Exception {
		
		UserVO loginUser = (UserVO) session.getAttribute(SessionName.LOGIN);
		if(loginUser != null) {
			
			model.addAttribute("user", msservice.getUserInfo(loginUser.getId()));
			model.addAttribute("profile", msservice.getUserProfile(loginUser.getId()));
		}
		
		return "/user/userProfile";
	}
	@RequestMapping(value="/updateUserProfile", method=RequestMethod.POST)
	public String updateUserProfile(@ModelAttribute("userVO") UserVO userVO, Model model) throws Exception {
		
		msservice.updateUserProfile(userVO);
		
		userVO.setProfileImg(msservice.getUserProfile(userVO.getId()));
		System.out.println("profile : " + userVO.getProfileImg());
		
		return "redirect:/mypage/getBoardListPagingFromId";
	}
	
	
	//��й�ȣ ����(�⺻ ȸ����)
	@RequestMapping(value="/modifyPwPage", method=RequestMethod.GET)
	public String modifyPwPage(MemberShipVO memberShipVO, Model model, HttpSession session) throws Exception {
		UserVO loginUser = (UserVO) session.getAttribute(SessionName.LOGIN);
		
		if(loginUser != null) {
			model.addAttribute("user", msservice.getUserInfo(loginUser.getId()));
		}
		
		return "/memberShip/modifyPwPage";
	}
	@RequestMapping(value="/updateUserPw", method=RequestMethod.POST)
	public String updateUserPw(@ModelAttribute MemberShipVO memberShipVO, Errors errors, HttpSession session,RedirectAttributes rttr) throws Exception {
		
		UserVO loginUser = (UserVO) session.getAttribute(SessionName.LOGIN);
		
		if(!BCrypt.checkpw(memberShipVO.getOldPassword(), loginUser.getPassword())) {
			errors.rejectValue("oldPassword", "pwEqualsNot", "���� ��й�ȣ�� Ʋ���̽��ϴ�.");
			return "/memberShip/modifyPwPage";
		}
		
		if(memberShipVO.getPassword().equals("") || memberShipVO.getPasswordChk().equals("")) {
			errors.rejectValue("password", "pwEmpty", "��й�ȣ�� �Է����ּ���.");
			return "/memberShip/modifyPwPage";
		}
		
		if(!memberShipVO.checkPassword()) {
			errors.rejectValue("password", "pwEqualsNot", "��й�ȣ�� ��ġ���� �ʽ��ϴ�.");
			return "/memberShip/modifyPwPage";
		}
		
		//��й�ȣ ��ȣȭ
		String bcryptPw = BCrypt.hashpw(memberShipVO.getPassword(), BCrypt.gensalt()); 
		memberShipVO.setPassword(bcryptPw);
		
		msservice.updateUserPw(memberShipVO);
		
		return "redirect:/main/mainPage";
	}
	
	//ȸ�� Ż��
	@RequestMapping(value="/deleteMemberShipPage", method=RequestMethod.GET)
	public String deleteMemberShipPage(LoginDTO loginDTO, UserVO userVO, HttpSession session, Model model) throws Exception {
		UserVO loginUser = (UserVO) session.getAttribute(SessionName.LOGIN);
		
		if(loginUser != null) {
			
			if(loginUser.getPassword() == null) {
				model.addAttribute("user", msservice.getUserInfo(loginUser.getId()));
				
				return "/memberShip/withdrawMemberShipSns";
			}
			else {
				model.addAttribute("user", msservice.getUserInfo(loginUser.getId()));
				
				return "/memberShip/withdrawMemberShip";
			}
		}
		else {
			return "redirect:/login/login";
		}
		
	}
	//member
	@RequestMapping(value="/deleteMemberShip", method=RequestMethod.POST)
	public String deleteMemberShip(@ModelAttribute("loginDTO") @Valid LoginDTO loginDTO , UserVO userVO, Model model, HttpSession session,BindingResult bindingResult,RedirectAttributes rttr, Errors errors) throws Exception {
		
		if(bindingResult.hasErrors()) {
			return "/memberShip/withdrawMemberShip";
		}
		
		UserVO user = userservice.login(loginDTO);
		if(user != null && BCrypt.checkpw(loginDTO.getPw(), user.getPassword())) {
			System.out.println("user != null");
			model.addAttribute("user", user);
			
			msservice.deleteMemberShip(userVO);
			
		}
		else if(user == null || !BCrypt.checkpw(loginDTO.getPw(), user.getPassword())){
			System.out.println("user == null");
			
			bindingResult.rejectValue("pw", "notMatch", "ȸ�� ������ ��ġ���� �ʽ��ϴ�.");
			model.addAttribute("pwCheck", "��й�ȣ�� ��ġ���� �ʽ��ϴ�");
			
			return "/memberShip/withdrawMemberShip";
		}
		
		session.invalidate();
		
		return "redirect:/main/mainPage";
	}
	
	//Sns member
	@RequestMapping(value="/deleteMemberShipSns", method=RequestMethod.POST)
	public String deleteMemberShipSns(UserVO userVO, Model model, HttpSession session, BindingResult bindingResult, RedirectAttributes rttr, Errors errors) throws Exception {
		
		UserVO loginUser = (UserVO) session.getAttribute(SessionName.LOGIN);
		
		if(bindingResult.hasErrors()) {
			return "/memberShip/withdrawMemberShip";
		}
		
		if(loginUser != null) {
			model.addAttribute("user", loginUser);
			
			msservice.deleteMemberShip(userVO);
			
		}
		
		session.invalidate();
		
		return "redirect:/main/mainPage";
	}
	
	
	//�н����� üũ
	@ResponseBody
	@RequestMapping(value="/passwordCheck", method=RequestMethod.POST)
	public boolean passwordCheck(LoginDTO loginDTO) throws Exception {
		
		UserVO user = userservice.login(loginDTO);
		boolean result = BCrypt.checkpw(loginDTO.getPw(), user.getPassword());
		 
		return result;
	}
	
	
}
