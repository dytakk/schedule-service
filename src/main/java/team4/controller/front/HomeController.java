package team4.controller.front;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.graalvm.compiler.replacements.nodes.ArrayCompareToNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import team4.services.auth.Authentication;
import team4.services.auth.Encryption;
import team4.services.auth.ProjectUtils;
import team4.services.friends.FriendsRelation;
import team4.services.schedule.ScheduleManagements;
import team4.user.beans.AccessInfo;
import team4.user.beans.ScheduleBean;
import team4.user.beans.TDetailsBean;
import team4.user.beans.TeamBean;
import team4.user.beans.UserBean;


@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);


	@Autowired
	private Authentication auth;

	@Autowired
	private ScheduleManagements sm;

	@Autowired
	FriendsRelation fr;

	@Autowired
	private ProjectUtils pu;

	@Autowired
	private Encryption enc;

	private ModelAndView mav;

	@RequestMapping(value = "/", method = {RequestMethod.GET , RequestMethod.POST})
	public ModelAndView sendAccessForm(@ModelAttribute AccessInfo ai)  {
		return auth.rootCtl(ai);
	}

	//스케줄관리 => (임시) 파일업로드 메뉴.
	@PostMapping(value = "/Schedule" )
	public String ScheduleForm() {

		return "schedule";
	}

	//파일(사진)전송 메소드
	@PostMapping(value = "/sendFiles" )
	public ModelAndView sendFiles(@ModelAttribute UserBean ub) {
		ScheduleBean sb = new ScheduleBean();
		mav = sm.ctl(sb,ub);
		return mav;
	}

	//팀관리 페이지
	@PostMapping(value = "/team" )
	public String Team() {
		return "team";
	}

	//친구관리 페이지
	@PostMapping(value = "/FriendList" )
	public String Friend() {
		return "friendlist";
	}

	//일정추가페이지(대쉬보드에서 일정추가)
	@PostMapping(value = "/addSchedule" )
	public String AddSchedule(@ModelAttribute ScheduleBean sb) {
		System.out.println(sb.getDate());

		return "addSchedule";
	}

	@PostMapping( "/Access" )
	public ModelAndView Access(@ModelAttribute AccessInfo ai,ScheduleBean sb) throws Exception {
		mav = auth.accessCtl(ai, sb);
		return mav;
	}

	@PostMapping( "/Logout" )
	public ModelAndView LogOut(@ModelAttribute AccessInfo ai) throws Exception {
		mav = auth.accessOutCtl(ai);
		return mav;
	}
	
	//회원가입 기본화면에서 회원가입 누르면 이 servlet으로이동. 이거 자체는 join.jsp page를 return해줍니다.
	@RequestMapping(value = "/JoinForm" )
	public String sendJoinForm() {
		return "join";
	}
	
	@PostMapping( "/Join" )
	public ModelAndView memberJoin(@ModelAttribute UserBean ub) throws Exception {
		System.out.println("파일을 조인으로 GO");
		//		System.out.println(ub.getMpfile().getOriginalFilename());
		//		System.out.println(ub.getMpfile().getContentType());

		mav =auth.joinCtl(ub);
		return mav;
	}

	@PostMapping( "/IsDup")
	@ResponseBody	//responseBody Ajax로 return할 떄 page가 아닌 body의 일부분 데이터만 응답해야할 때. 프론트와 서버간의 업무를 철저히 분류
	public String isDuplicateCheck(@ModelAttribute AccessInfo ai) {

		return auth.isDulicateIdCtl(ai);
	}

	@GetMapping( "/mailAuth" )
	public String mailAuth() {

		return "emailauth";
	}

	@RequestMapping( "/EmailAuthConfirm" )
	public ModelAndView mailAuth(@ModelAttribute TDetailsBean tb) throws Exception {

		return fr.authMail(tb);
	}



	@PostMapping("/sendConfirm")
	public ModelAndView sendConfirm(@ModelAttribute AccessInfo ai, ScheduleBean sb) {
		try {
			sb.setMbId((String)pu.getAttribute("uCode"));
			sm.insertSchedule(sb);	
			mav.setViewName("dashboard");
		} catch (Exception e) {

			e.printStackTrace();
		}
		return mav;


	}

	/*@PostMapping( "/LogIn" )
	public String Login(@RequestParam("uCode") String uCode, @RequestParam("aCode") String aCode) {


		System.out.print(uCode + aCode);
		return "login";
	}*/


	/*@PostMapping( "/LogIn2" )
	public String Login2(@RequestParam("Code") ArrayList<String> list) {


		System.out.print(list.get(0) + list.get(1));
		return "login";
	}*/


	/*@PostMapping( "/LogIn3" )
	public String Login3(@ModelAttribute UserBean ub) {


		System.out.print(ub.getUCode() + ":" + ub.getACode());
		return "login";
	}
	 */

}