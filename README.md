# ChParkLand
-----


# 개요

* 프로젝트 명 : ChParkLand

* 일정 : 19.10 ~ 20.02

* 프로젝트 소개 : 회원 중심으로 소통할 수 있는 커뮤니티 웹 사이트를 구현

* 사용 기술 및 개발 환경 

  - O/S : Windows 10 (개발 ), Linux ubuntu 18.04(운영)
  - JAVA8, JavaScript, JQuery, HTML5, CSS
  - 운영 환경 : Tomcat8.5, AWS EC2(ubuntu), AWS RDS
  - 프레임 워크 : Spring Framework, MyBatis
  - DB : MariaDB
  - Tools : STS, HeidiSql, Putty, GitHub
  - etc : AWS S3




# 내용

* [PPT(SlideShare)](https://www.slideshare.net/secret/JGKnJYTfjDZ9LA)


* 구현 기능 

  - 회원가입, 수정, 탈퇴
  - 로그인 & 로그아웃 ()
  - 소셜 로그인 & 회원가입 (구글, 네이버, 카카오)
  - 회원 정보 찾기(아이디, 비밀번호 : 임시 비밀번호 발송)
  - 게시판 (다중 게시판, 검색, 페이징, 대댓글, 파일 첨부, 공지)
  - 관리자 기능(회원 관리, 게시판 관리)
  - 마이 페이지 (작성글, 작성 댓글, 알림, 프로필 관리)
  - 유저 페이지 (작성글, 작성 댓글, 쪽지 보내기)
  - 검색 (키워드, 페이징)
  - 회원 프로필(등록, 수정, 삭제)
  - 메인 화면 (주간 추천 게시판, 주간 인기 게시판, 기타 게시판)
  - 쪽지 (수신, 발신 페이지)
  - 문의 이메일
  - 파일 업로드 & 다운로드 (AWS S3 bucket)
  - 알림 (웹소켓을 이용한 실시간 알림)
  - 보안 : SSL, XSS 필터, 비밀번호 암호화(Bcrypt)



 
 # 마치며


프로젝트를 모두 마치며 스프링 시큐리티를 사용하여 회원 로그인부터의 구현을 하였을 경우 
좀 더 간편하고 보안적으로 강화가 될 것이라는걸 체감했고 시큐리티 공부의 필요성을 느꼈습니다.
그리고 추가로 웹사이트의 지향점은 구글 등에서 사이트의 게시글 검색이 될 수 있도록 하고 
검색 엔진 최적화(SEO)로 양질의 게시글 외부에 노출 될 수 있을때 완성도가 높은 웹사이트가 된다고 생각합니다.
이 부분에서도 더 많은 공부의 필요성을 느꼈습니다.

