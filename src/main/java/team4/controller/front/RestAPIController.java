package team4.controller.front;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import team4.services.auth.Encryption;
import team4.services.auth.ProjectUtils;
import team4.services.friends.FriendsRelation;
import team4.services.schedule.ScheduleManagements;
import team4.user.beans.MailForm;
import team4.user.beans.ScheduleBean;
import team4.user.beans.TDetailsBean;
import team4.user.beans.TeamBean;
import team4.user.beans.UserBean;

@RestController
@RequestMapping("/schedule")
public class RestAPIController {
	@Autowired
	FriendsRelation fr;
	@Autowired
	Encryption enc;
	@Autowired
	ProjectUtils pu;
	@Autowired
	ScheduleManagements sm;


	@PostMapping("/teamList")
	@ResponseBody
	public List<TeamBean> getTeamList() {	
		TeamBean tb = new TeamBean();
		try {
			tb.setMbId((String)pu.getAttribute("uCode"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fr.getTeamList(tb);
	}
	@PostMapping("/memberList")
	//reqeust=받아오는거,  response= text로 씀!, 
	//RequestBody=>HTTP의 요청 내용을 자바객체로 맵핑. 	ResponseBody=>자바객체를 HTTP 요청의 body 내용으로 맵핑하는 역할.
	public List<TDetailsBean> getMemberList(@RequestBody TDetailsBean tb) {	
		List<TDetailsBean> teamList = fr.getMemberList(tb);

		for(int i=0; i<teamList.size(); i++) {
			try {
				teamList.get(i).setMbName(enc.aesDecode(teamList.get(i).getMbName(), teamList.get(i).getMbId()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return teamList;		
	}
	
	//addTeam을 실행하고 getTeamList를 Return하는 이유? addTeam을 통해 추가 된 Team과 전체 TeamList를 불러오기 위함임.
	@PostMapping("/addTeam")
	public List<TeamBean> addTeam(@RequestBody TeamBean tb){
		fr.addTeam(tb);
		return this.getTeamList();
	}
	@PostMapping("/friends")
	public List<TDetailsBean> frinedsList(TDetailsBean tb){
		try {
			tb.setMbId((String)pu.getAttribute("uCode"));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fr.friendsList(tb);
	}
	@PostMapping("/addFriends")
	public List<TDetailsBean> addFriends(@RequestBody List<TeamBean> tb) {


		return fr.addMember(tb);
	}
	@PostMapping("/EmailAuthConfirm")
	public List<TDetailsBean> mailAuth(@RequestBody TDetailsBean tb) {
		return null;
	}

	@PostMapping("/searchList")
	public List<UserBean> searchList(@RequestBody UserBean ub) {	
	
		return fr.searchList(ub);	
		
	}
	
	//친구검색에서 안나오는 사람들에게 회원가입 초대장보내기
	@PostMapping("/inviteMail")
	public void inviteMail(@RequestBody MailForm mb) {	
	
		fr.inviteMember(mb);
	}

}


