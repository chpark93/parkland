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
	
	//id �ߺ� üũ
	@Override
	public boolean checkId(String id) throws Exception {
		boolean isCheck = false;
		int check = msmanager.checkId(id);
		
		if(check > 0) { // 0 ���� Ŭ ��� �ߺ�.(isCheck = true)
			isCheck = true;
		}
		else {
			isCheck = false;
		}
		return isCheck;
	}
	
	//email �ߺ� üũ
	@Override
	public boolean checkEmail(String email) throws Exception {
		boolean isCheck = false;
		int check = msmanager.checkEmail(email);
		
		if(check > 0) { // 0���� Ŭ ��� �ߺ�. (isCheck = true)
			isCheck = true;			
		}
		else {
			isCheck = false;
		}
		return isCheck;
	}
	
	//nickname �ߺ� üũ
	@Override
	public boolean checkNickName(String nickname) throws Exception {
		boolean isCheck = false;
		int check = msmanager.checkNickName(nickname);
		
		if(check > 0) { // 0���� Ŭ ��� �ߺ�. (isCheck = true)
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
	
	//ȸ�� ���
	@Transactional
	@Override
	public void register(MemberShipVO memberShipVO, HttpServletResponse response) throws Exception {
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();	
	
		//����Ű ����
		String approvalKey = new TempKey().createKey();
		
		memberShipVO.setApproval_key(approvalKey);
		
		msmanager.memberApproval(memberShipVO); //ȸ�� ����
		msmanager.memberRegister(memberShipVO); //ȸ�� ����
		
		//�������� ����
		sendMail(memberShipVO, "join");
		
		out.print("<script> alert('�̸��� �ּҷ� ���� ������ ���½��ϴ�. ���� �� �̿� ���ּ���'); location.href='http://chparkland.com/park_project_1/login/login'; </script>");
		out.close();
	}
	
	//ȸ�� ���(SNS)
	@Transactional
	@Override
	public void registerSNS(MemberShipSnsVO memberShipSnsVO) throws Exception {
		//����Ű ����
		String approvalKey = new TempKey().createKey();
		memberShipSnsVO.setApproval_key(approvalKey);
		
		msmanager.approvalStatusSns(memberShipSnsVO);
		msmanager.memberRegisterSns(memberShipSnsVO);
	}
	
	//ȸ�� �߰� ����
	@Override
	public void registerDetail(MemberShipDetailVO memberShipDetailVO) throws Exception {
		msmanager.insertMemberShipDetail(memberShipDetailVO);
		
	}
	
	//�������� ���� 
	@Override
	public void sendMail(MemberShipVO memberShipVO, String div) throws Exception {
		
		//�������� ����
		MailUtils sendMail = new MailUtils(mailSender);
		
		if(div.equals("join")) {
			sendMail.setSubject("CHPark ȸ������ ���� ���� �Դϴ�.");
			
			sendMail.setMsg(new StringBuffer().append("<h1>ȸ�� ���� ���� ����</h1>")
				.append("<div align='center' style='border:1px solid black; font-family:verdana'>")
				.append("<h3 style='color:powderblue'>") 
				.append(memberShipVO.getId() + "�� ȸ�������� ȯ���մϴ�.</h3>") 
				.append("<div style='font-size: 120%'>") 
				.append("���� ��ư�� Ŭ�� �Ͻø� ȸ�������� �Ϸ�˴ϴ�.</div><br/>") 
				.append("<form action='http://chparkland.com/park_project_1/memberShip/approvalStatus'>") 
				.append("<input type='hidden' name='email' value='" + memberShipVO.getEmail() + "'>") 
				.append("<input type='hidden' name='approval_key' value='" + memberShipVO.getApproval_key() + "'>") 
				.append("<input type='hidden' name='id' value='" + memberShipVO.getId() + "'>") 				
				.append("<input type='submit' value='ȸ������'></form><br/></div>") 
				.toString());
		}else if(div.equals("findPw")) {
			sendMail.setSubject("CHPark �ӽ� ��й�ȣ ���� �Դϴ�.");
			
			sendMail.setMsg(new StringBuffer().append("<h1>�ӽ� ��й�ȣ</h1>")
					.append("<div align='center' style='border:1px solid black; font-family:verdana'>")
					.append("<h3 style='color:powderblue'>") 
					.append(msmanager.findByEmail(memberShipVO).getId() + "���� �ӽ� ��й�ȣ �Դϴ�.</h3>") 
					.append("<div style='font-size: 120%'>") 
					.append("<p>�ӽ� ��й�ȣ : ")
					.append(memberShipVO.getPassword() + "</p>")
					.append("<form action='http://chparkland.com/park_project_1'>") 
					.append("<input type='submit' value='�α���'></form><br/></div>") 
					.toString());
		}
		
		sendMail.setFrom("qkrckdgml1993@gmail.com", "ChPark");
		sendMail.setTo(memberShipVO.getEmail());
		sendMail.send();
	}
		
	
	//approval_status ����
	@Override
	public void approvalStatus(MemberShipVO memberShipVO) throws Exception {
		if(msmanager.approvalStatus(memberShipVO) == 0) { //���� ����
			System.out.println("���� ����");
		}else { //���� ����
			msmanager.approvalStatus(memberShipVO);
			System.out.println("���� ����");	
		}
		
	}
	
	
	@Override
	public MemberShipVO findByEmail(MemberShipVO memberShipVO) throws Exception {
		return msmanager.findByEmail(memberShipVO);		
	}
	//��й�ȣ ã��
	@Transactional
	@Override
	public void findPw(MemberShipVO memberShipVO, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();	
		msmanager.findByEmail(memberShipVO);
		
		//�ӽ� ��й�ȣ ����
		String tempPw = new TempKey().tempPw();
		memberShipVO.setPassword(tempPw);
			
		//��й�ȣ ���� ���� �߼�
		sendMail(memberShipVO, "findPw");
				
		//��й�ȣ ��ȣȭ
		String bcryptPw = BCrypt.hashpw(memberShipVO.getPassword(), BCrypt.gensalt()); 
		memberShipVO.setPassword(bcryptPw);
	
		//�ӽ� ��й�ȣ�� ����
		msmanager.findPw(memberShipVO);
		
		out.print("<script> alert('�ӽ� ��й�ȣ�� ���Ϸ� �߼��Ͽ����ϴ�. ��й�ȣ ���� �� ��� �ٶ��ϴ�.'); location.href='http://chparkland.com/park_project_1/login/login'; </script>");
		out.close();
	}
	
	//��й�ȣ ã�� ����
	@Override
	public MemberShipVO execute(MemberShipVO memberShipVO) throws Exception {
		MemberShipVO result = msmanager.findByEmail(memberShipVO);
		
		if(result == null) {
			throw new Exception();
		}
		
		return result;
	}
	
	//ȸ�� ���� ����
	@Transactional
	@Override
	public void updateUserInfo(UserVO userVO) throws Exception {
		msmanager.updateUserInfo(userVO);
		msmanager.updateMemberShipDetail(userVO);
	}
	//��й�ȣ ����
	@Override
	public void updateUserPw(MemberShipVO memberShipVO) throws Exception {
		msmanager.updateUserPw(memberShipVO);
	}
	
	//ȸ�� Ż��
	@Override
	public void deleteMemberShip(UserVO userVO) throws Exception {
		msmanager.deleteMemberShip(userVO);
	}
	
	//�н����� üũ
	@Override
	public boolean passwordCheck(LoginDTO loginDTO) throws Exception {
		return msmanager.passwordCheck(loginDTO);
	}
	
	
	/* MemberShip Detail */
	//ȸ�� ���� ����(����������)
	@Override 
	public void updateMemberShipDetail(UserVO userVO) throws Exception {
		msmanager.updateMemberShipDetail(userVO); 
	}
	
	@Transactional
	@Override
	public void updateUserProfile(UserVO userVO) throws Exception {
		
		//���� ������ �̹���
		String[] files = userVO.getFiles();
		
		//���� ���� ������ �̹��� ����
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
