package com.park.web.memberShip.service;

import java.io.PrintWriter;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.park.web.file.db.FileDAO;
import com.park.web.login.db.LoginDTO;
import com.park.web.memberShip.db.MemberShipDAO;
import com.park.web.memberShip.db.MemberShipDetailVO;
import com.park.web.memberShip.db.MemberShipSnsVO;
import com.park.web.memberShip.db.MemberShipVO;
import com.park.web.memberShip.util.MailUtils;
import com.park.web.memberShip.util.TempKey;
import com.park.web.user.db.UserVO;


@Service
public class MemberShipServiceImpl implements MemberShipService {

	@Inject
	private MemberShipDAO msmanager; 
	
	@Inject
	private FileDAO filemanager;
	
	@Inject
	private JavaMailSender mailSender;
	
	//id 중복 체크
	@Override
	public boolean checkId(String id) throws Exception {
		boolean isCheck = false;
		int check = msmanager.checkId(id);
		
		if(check > 0) { // 0 보다 클 경우 중복.(isCheck = true)
			isCheck = true;
		}
		else {
			isCheck = false;
		}
		return isCheck;
	}
	
	//email 중복 체크
	@Override
	public boolean checkEmail(String email) throws Exception {
		boolean isCheck = false;
		int check = msmanager.checkEmail(email);
		
		if(check > 0) { // 0보다 클 경우 중복. (isCheck = true)
			isCheck = true;			
		}
		else {
			isCheck = false;
		}
		return isCheck;
	}
	
	//nickname 중복 체크
	@Override
	public boolean checkNickName(String nickname) throws Exception {
		boolean isCheck = false;
		int check = msmanager.checkNickName(nickname);
		
		if(check > 0) { // 0보다 클 경우 중복. (isCheck = true)
			isCheck = true;
		}
		else {
			isCheck = false;
		}
		return isCheck;
	}
	
	
	@Override
	public UserVO getUserInfo(String id) throws Exception {
		return msmanager.getUserInfo(id);
	}
	
	//회원 등록
	@Transactional
	@Override
	public void register(MemberShipVO memberShipVO, HttpServletResponse response) throws Exception {
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();	
	
		//인증키 생성
		String approvalKey = new TempKey().createKey();
		
		memberShipVO.setApproval_key(approvalKey);
		
		msmanager.memberApproval(memberShipVO); //회원 인증
		msmanager.memberRegister(memberShipVO); //회원 가입
		
		//인증메일 전송
		sendMail(memberShipVO, "join");
		
		out.print("<script> alert('이메일 주소로 인증 메일을 보냈습니다. 인증 후 이용 해주세요'); location.href='http://chparkland.com/park_project_1/login/login'; </script>");
		out.close();
	}
	
	//회원 등록(SNS)
	@Transactional
	@Override
	public void registerSNS(MemberShipSnsVO memberShipSnsVO) throws Exception {
		//인증키 생성
		String approvalKey = new TempKey().createKey();
		memberShipSnsVO.setApproval_key(approvalKey);
		
		msmanager.approvalStatusSns(memberShipSnsVO);
		msmanager.memberRegisterSns(memberShipSnsVO);
	}
	
	//회원 추가 정보
	@Override
	public void registerDetail(MemberShipDetailVO memberShipDetailVO) throws Exception {
		msmanager.insertMemberShipDetail(memberShipDetailVO);
		
	}
	
	//인증메일 전송 
	@Override
	public void sendMail(MemberShipVO memberShipVO, String div) throws Exception {
		
		//인증메일 내용
		MailUtils sendMail = new MailUtils(mailSender);
		
		if(div.equals("join")) {
			sendMail.setSubject("CHPark 회원가입 인증 메일 입니다.");
			
			sendMail.setMsg(new StringBuffer().append("<h1>회원 가입 인증 메일</h1>")
				.append("<div align='center' style='border:1px solid black; font-family:verdana'>")
				.append("<h3 style='color:powderblue'>") 
				.append(memberShipVO.getId() + "님 회원가입을 환영합니다.</h3>") 
				.append("<div style='font-size: 120%'>") 
				.append("인증 버튼을 클릭 하시면 회원가입이 완료됩니다.</div><br/>") 
				.append("<form action='http://chparkland.com/park_project_1/memberShip/approvalStatus'>") 
				.append("<input type='hidden' name='email' value='" + memberShipVO.getEmail() + "'>") 
				.append("<input type='hidden' name='approval_key' value='" + memberShipVO.getApproval_key() + "'>") 
				.append("<input type='hidden' name='id' value='" + memberShipVO.getId() + "'>") 				
				.append("<input type='submit' value='회원인증'></form><br/></div>") 
				.toString());
		}else if(div.equals("findPw")) {
			sendMail.setSubject("CHPark 임시 비밀번호 메일 입니다.");
			
			sendMail.setMsg(new StringBuffer().append("<h1>임시 비밀번호</h1>")
					.append("<div align='center' style='border:1px solid black; font-family:verdana'>")
					.append("<h3 style='color:powderblue'>") 
					.append(msmanager.findByEmail(memberShipVO).getId() + "님의 임시 비밀번호 입니다.</h3>") 
					.append("<div style='font-size: 120%'>") 
					.append("<p>임시 비밀번호 : ")
					.append(memberShipVO.getPassword() + "</p>")
					.append("<form action='http://chparkland.com/park_project_1'>") 
					.append("<input type='submit' value='로그인'></form><br/></div>") 
					.toString());
		}
		
		sendMail.setFrom("qkrckdgml1993@gmail.com", "ChPark");
		sendMail.setTo(memberShipVO.getEmail());
		sendMail.send();
	}
		
	
	//approval_status 인증
	@Override
	public void approvalStatus(MemberShipVO memberShipVO) throws Exception {
		if(msmanager.approvalStatus(memberShipVO) == 0) { //인증 실패
			System.out.println("인증 실패");
		}else { //인증 성공
			msmanager.approvalStatus(memberShipVO);
			System.out.println("인증 성공");	
		}
		
	}
	
	
	@Override
	public MemberShipVO findByEmail(MemberShipVO memberShipVO) throws Exception {
		return msmanager.findByEmail(memberShipVO);		
	}
	//비밀번호 찾기
	@Transactional
	@Override
	public void findPw(MemberShipVO memberShipVO, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();	
		msmanager.findByEmail(memberShipVO);
		
		//임시 비밀번호 생성
		String tempPw = new TempKey().tempPw();
		memberShipVO.setPassword(tempPw);
			
		//비밀번호 변경 메일 발송
		sendMail(memberShipVO, "findPw");
				
		//비밀번호 암호화
		String bcryptPw = BCrypt.hashpw(memberShipVO.getPassword(), BCrypt.gensalt()); 
		memberShipVO.setPassword(bcryptPw);
	
		//임시 비밀번호로 변경
		msmanager.findPw(memberShipVO);
		
		out.print("<script> alert('임시 비밀번호를 메일로 발송하였습니다. 비밀번호 변경 후 사용 바랍니다.'); location.href='http://chparkland.com/park_project_1/login/login'; </script>");
		out.close();
	}
	
	//비밀번호 찾기 검증
	@Override
	public MemberShipVO execute(MemberShipVO memberShipVO) throws Exception {
		MemberShipVO result = msmanager.findByEmail(memberShipVO);
		
		if(result == null) {
			throw new Exception();
		}
		
		return result;
	}
	
	//회원 정보 변경
	@Transactional
	@Override
	public void updateUserInfo(UserVO userVO) throws Exception {
		msmanager.updateUserInfo(userVO);
		msmanager.updateMemberShipDetail(userVO);
	}
	//비밀번호 변경
	@Override
	public void updateUserPw(MemberShipVO memberShipVO) throws Exception {
		msmanager.updateUserPw(memberShipVO);
	}
	
	//회원 탈퇴
	@Override
	public void deleteMemberShip(UserVO userVO) throws Exception {
		msmanager.deleteMemberShip(userVO);
	}
	
	//패스워드 체크
	@Override
	public boolean passwordCheck(LoginDTO loginDTO) throws Exception {
		return msmanager.passwordCheck(loginDTO);
	}
	
	
	/* MemberShip Detail */
	//회원 정보 수정(마이페이지)
	@Override 
	public void updateMemberShipDetail(UserVO userVO) throws Exception {
		msmanager.updateMemberShipDetail(userVO); 
	}
	
	@Transactional
	@Override
	public void updateUserProfile(UserVO userVO) throws Exception {
		
		//유저 프로필 이미지
		String[] files = userVO.getFiles();
		
		//기존 유저 프로필 이미지 삭제
		filemanager.deleteUserProfile(userVO.getId());
		msmanager.deleteUserDetailProfile(userVO.getId());
		
		if(files == null) {
			return;
		}
		
		if(files.length <= 1) {
			
			for(String fileName : files) {
				filemanager.insertUserProfile(userVO, fileName);
			}
		}
		
		
		msmanager.updateUserProfile(userVO);
	}
	
	@Override
	public String getUserProfile(String id) throws Exception {
		
		return msmanager.getUserProfile(id);
	}

	@Override
	public String getUserProfileNickName(String nickname) throws Exception {
		
		return msmanager.getUserProfileNickName(nickname);
	}
	

	
	
	

}
