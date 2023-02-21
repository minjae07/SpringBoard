package kr.board.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.board.entity.Member;
import kr.board.mapper.MemberMapper;

@Controller
public class MemberController {

	@Autowired
	MemberMapper memberMapper;
	
	@RequestMapping("/memJoin.do")
	public String memJoin() {
		return "member/memjoin";
	}
	
	@ResponseBody
	@RequestMapping("/memRegisterCheck.do")
	public int memRegisterCheck(@RequestParam("memID") String memID) {
		Member member = memberMapper.registerCheck(memID);
		if(member!=null || memID.equals("")) {
			return 0; //이미 존재하는 회원, 입력 불가 
		}
		return 1;	//아이디 사용가능
	}
	
	//회원가입
	@RequestMapping("/memRegister.do")
	public String memRegister(Member member, String memPassword1, String memPassword2,
			RedirectAttributes rttr, HttpSession session) {	
		if(member.getMemID()==null || member.getMemID().equals("") ||
				memPassword1==null || memPassword1.equals("") ||
				memPassword2==null || memPassword2.equals("") ||
		  // member.getMemPassword()==null || member.getMemPassword().equals("") || 아래에서 체크
		   member.getMemName() == null || member.getMemName().equals("") ||
		   member.getMemAge() == 0 || String.valueOf(member.getMemAge()).equals("") ||
		   member.getMemGender() == null || member.getMemGender().equals("") ||
		   member.getMemEmail() == null || member.getMemEmail().equals("")) {
			//누락 메시지 가지고 가기? => 객체바인딩(Model, HttpServletRequest, HttpSession)
			//RedirectAttributes : model로 jsp에 객체 바인딩을 하는게 아닐때, 리다이렉트에 한번만 뿌려주는 것
			rttr.addFlashAttribute("msgType", "실패 메세지");
			rttr.addFlashAttribute("msg", "모든 내용을 입력하세요");
			return "redirect:/memJoin.do";	//${msgType}, ${msg}로 값을 가져오자
		}
		if(!memPassword1.equals(memPassword2)) {
			rttr.addFlashAttribute("msgType", "실패 메세지");
			rttr.addFlashAttribute("msg", "비밀번호가 서로 다릅니다");
			return "redirect:/memJoin.do"; //${msgType}, ${msg}로 값을 가져오자
		}
		member.setMemProfile(""); //사진 이미지는 존재하지 않음
		//회원을 테이블에 저장
		int result = memberMapper.register(member);
		if(result==1) {	//회원가입 성공
			rttr.addFlashAttribute("msgType", "성공 메세지");
			rttr.addFlashAttribute("msg", "회원가입에 성공하셨습니다");
			// 회원가입이 성공하면 => 로그인 처리하기
			session.setAttribute("mvo", member);	//${!empty m} 체크 jsp에서
			return "redirect:/";
		}else {	//회원가입 실패
			rttr.addFlashAttribute("msgType", "실패 메세지");
			rttr.addFlashAttribute("msg", "이미 존재하는 회원입니다");
			// 회원가입이 성공하면 => 로그인 처리하기
			session.setAttribute("mvo", member);	//${!empty m} 체크 jsp에서
			return "redirect:/memJoin.do";
		}
	}
	// 로그아웃 처리
	@RequestMapping("/memLogout.do")
	public String memLogout(HttpSession session) {
		session.invalidate(); //세션 무효화
		return "redirect:/";
	}
	//로그인 화면
	@RequestMapping("/memLoginForm.do")
	public String memLoginForm() {
		return "member/memLoginForm";
	}
	//로그인 기능 구현
	@RequestMapping("/memLogin.do")
	public String memLogin(Member member, RedirectAttributes rttr, HttpSession session) {
		if(member.getMemID()==null || member.getMemID().equals("")||
				member.getMemPassword()==null || member.getMemPassword().equals("")) {
			rttr.addFlashAttribute("msgType", "실패메시지");
			rttr.addFlashAttribute("msg", "모든내용을 입력해주세요");
			return "redirect:/memLoginForm.do";
		}
		Member mvo = memberMapper.memLogin(member);
		if(mvo!=null) {	//로그인에 성공
			rttr.addFlashAttribute("msgType", "성공메시지");
			rttr.addFlashAttribute("msg", "로그인에 성공했습니다");
			session.setAttribute("mvo", mvo);	//${!empty mvo}를 헤더 페이지에서 체크
			return "redirect:/";	//메인
		}else {	//로그인에 실패
			rttr.addFlashAttribute("msgType", "실패메시지");
			rttr.addFlashAttribute("msg", "다시 입력해 주세요");
			return "redirect:/memLoginForm.do";
		}
	}
	//회원정보 수정화면
	@RequestMapping("/memUpdateForm.do")
	public String memUpdateForm() {
		return "member/memUpdateForm";
	}
	//회원 정보 수정
	@RequestMapping("/memUpdate.do")
	public String memUpdate(Member member, RedirectAttributes rttr,
			String memPassword1, String memPassword2, HttpSession session) {
		
			 if(member.getMemID()==null || member.getMemID().equals("") ||
				memPassword1==null || memPassword1.equals("") ||
				memPassword2==null || memPassword2.equals("") ||
				member.getMemName() == null || member.getMemName().equals("") ||
				member.getMemAge() == 0 || String.valueOf(member.getMemAge()).equals("") ||
				member.getMemGender() == null || member.getMemGender().equals("") ||
				member.getMemEmail() == null || member.getMemEmail().equals("")) {
				rttr.addFlashAttribute("msgType", "실패 메세지");
				rttr.addFlashAttribute("msg", "모든 내용을 입력하세요");
				return "redirect:/memUpdateForm.do";	//${msgType}, ${msg}로 값을 가져오자
		}
			 if(!memPassword1.equals(memPassword2)) {
				 rttr.addFlashAttribute("msgType", "실패 메세지");
				 rttr.addFlashAttribute("msg", "비밀번호가 서로 다릅니다");
				 return "redirect:/memUpdateForm.do"; //${msgType}, ${msg}로 값을 가져오자
		}
		//회원 정보 수정
		int result = memberMapper.memUpdate(member);
		if(result==1) {	//수정 성공 메세지
			rttr.addFlashAttribute("msgType", "성공 메세지");
			rttr.addFlashAttribute("msg", "회원정보 수정에 성공하셨습니다");
			// 회원가입이 성공하면 => 로그인 처리하기
			session.setAttribute("mvo", member);	//${!empty m} 체크 jsp에서
			return "redirect:/";
		}else {	//회원가입 실패
			rttr.addFlashAttribute("msgType", "실패 메세지");
			rttr.addFlashAttribute("msg", "회원정보 수정에 실패하셨습니다");
			// 회원가입이 성공하면 => 로그인 처리하기
			session.setAttribute("mvo", member);	//${!empty m} 체크 jsp에서
			return "redirect:/memUpdateForm.do";
		}
	}
	//회원 사진 등록
	@RequestMapping("/memImageForm.do")
	public String memImageForm() {
		return "member/memImageForm";
	}
}
