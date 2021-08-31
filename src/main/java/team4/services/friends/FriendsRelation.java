package team4.services.friends;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.util.TextBuffer;

import team4.database.mapper.FriendsInterface;
import team4.services.auth.Encryption;
import team4.services.auth.ProjectUtils;
import team4.user.beans.AccessInfo;
import team4.user.beans.MailForm;
import team4.user.beans.TDetailsBean;
import team4.user.beans.TeamBean;
import team4.user.beans.UserBean;


@Service
public class FriendsRelation implements FriendsInterface {
	@Autowired
	Encryption enc;
	@Autowired
	ProjectUtils pu;
	@Autowired
	SqlSessionTemplate sqlSession;
	@Autowired
	DataSourceTransactionManager tx;
	@Autowired
	JavaMailSenderImpl javaMail;

	private DefaultTransactionDefinition def;
	private TransactionStatus status;

	public FriendsRelation() {}

	public List<TeamBean> getTeamList(TeamBean tb){
		List<TeamBean> teamList;
		teamList = sqlSession.selectList("getTeamList",tb);
		System.out.println(teamList);
		return teamList;
	}
	public List<TDetailsBean> getMemberList(TDetailsBean tb){
		List<TDetailsBean> memberList = sqlSession.selectList("getMemberList",tb);
		return memberList;
	}

	public List<TDetailsBean> friendsList(TDetailsBean tb){
		List<TDetailsBean> friendsList;
		friendsList = sqlSession.selectList("friendsList",tb);

		for(int i=0; i<friendsList.size(); i++) {
			try {
				friendsList.get(i).setMbName(enc.aesDecode(friendsList.get(i).getMbName(),friendsList.get(i).getMbId()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return friendsList;
	}

	@Transactional(rollbackFor = Exception.class)
	public List<TeamBean> addTeam(TeamBean tb) {
		TDetailsBean db = new TDetailsBean();
		this.setTransactionConf(TransactionDefinition.PROPAGATION_REQUIRED,TransactionDefinition.ISOLATION_READ_COMMITTED,false);

		try {
			tb.setTeCode(this.getNowNumber(db)+"");
			tb.setMbId((String)pu.getAttribute("uCode"));
			//teName은 team.jsp의 addTeam()을 참조 . jsonData를 {teName:teamName}. teName이라는 bean에(key) teamName을 저장(value)
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		//select + 숫자값 증가 001 002
		//tb를 저장해야 밑에 넘겨줄수있음. insTeam의 Query에 teCode와 teName을 넣어주고 있으므로.
		//TE 테이블에 INSERT
		//즉 bean에 저장=>sqlSession연결 query를 통해 값을 넘겨주어 그 값을 기반으로 db에 insert,select등 dml 구문을 실행.
		try {
			if(this.insTeam(tb)) {
				//db.setTeCode(this.getNowNumber(db)+"");
				db.setTeCode(tb.getTeCode());
				db.setMbId(tb.getMbId());
				db.setCgCode("L");
				//TD 테이블에 INSERT
				this.insTeam2(db);
				this.setTransactionResult(true);
			}
		}catch(Exception e) {this.setTransactionResult(false);
		e.printStackTrace();
		}
		//getTeamList 호출을 통한 갱신된 데이터 가져오기
		return this.getTeamList(tb);
	}
	//문자열만 길이가있다. 숫자는 길이 x
	public int getNowNumber(TDetailsBean tb){
		int maxNum  = sqlSession.selectOne("getNowNumber");
		maxNum = maxNum+1;
		String num= "";
		//넘어온숫자의길이를 3-길이이렇게하는거에요 나머지값은 0으로 채워요 -인성조-
		//if(maxNum!=0) 
		int length = String.valueOf(maxNum).length();
		for(int i=0; i<3-length; i++) {
			num = num +"0";
		}		
		num+=maxNum;
		Calendar cal = Calendar.getInstance();
		String format = "yyMMdd";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String date = sdf.format(cal.getTime());
		return Integer.parseInt(date+num);
	}

	public boolean insTeam(TeamBean tb){
		return this.convert(sqlSession.insert("insTeam",tb));

	}
	public boolean insTeam2(TDetailsBean db){
		return this.convert(sqlSession.insert("insTeam2",db));
	}

	public boolean convert(int data) {
		return data>0?true :false;
	}
	//Transaction Configuration
	private void setTransactionConf(int propagation,
			int isolationLevel, boolean isRead) {
		def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(propagation);
		def.setIsolationLevel(isolationLevel);
		def.setReadOnly(isRead);
		status = tx.getTransaction(def);
	}
	//Transaction Result
	private void setTransactionResult(boolean isCheck) {
		if(isCheck) {
			tx.commit(status);

		}else {
			tx.rollback(status);
		}
	}

	public List<TDetailsBean> addMember(List<TeamBean> list) {
		List<MailForm> list2 = new ArrayList<MailForm> ();
		list.get(0).getTdetails().get(0).setEMail("glassrain00@gmail.com");
		//tb.getTdetails().get(1).setEMail("glassrain00@gmail.com");
		this.authFriends(list);		
		list2.get(0).setSubject("L2Club");
		list2.get(0).setContents("<a href='http://192.168.1.169/mailAuth?teCode="+list.get(0).getTeCode()+"'> Invite you! OUR TEAM</a>");
		list2.get(0).setFrom("telecaster0@naver.com");
		String [] to = new String [list.size()];
		for(int i=0; i<list.size(); i++) {
			to[i]= list.get(i).getTdetails().get(i).getEMail();
		}
		list.get(0).getTdetails().get(0).setTeCode(list.get(0).getTeCode());
		return this.getMemberList(list.get(0).getTdetails().get(0));
	}
	
	public void inviteMember(MailForm mf) {
		System.out.println(mf.getTo());
		//list.add(mb);	
		mf.setTo(mf.getTo());	
		mf.setSubject("JOIN US!");
		mf.setContents("<a href='http://192.168.1.169/JoinForm'>컴 </a>");
		mf.setFrom("telecaster0@naver.com");
		System.out.println(mf+"여기");

		this.sendMail(mf);
	}
	public void authFriends(List<TeamBean> list) {
		String subject= "L2club";
		String contents="<a href='http://192.168.123.104/mailAuth?teCode="+list.get(0).getTeCode()+"'> !Invite you</a>"; 
		String from = "telecaster0@naver.com";
		String [] to = new String [list.size()];
		for(int i=0; i<list.size(); i++) {
			to[i]= list.get(i).getTdetails().get(i).getEMail();
		}

	}
	public void sendMail(MailForm mf) {

		MimeMessage mail = javaMail.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mail,"UTF-8");	
		try {
			helper.setFrom(mf.getFrom());
			helper.setTo(mf.getTo());
			helper.setSubject(mf.getSubject());
			helper.setText(mf.getContents(),true);
			javaMail.send(mail);
		} catch (MessagingException e) {
			e.printStackTrace();	}
	}
	
	public ModelAndView authMail(TDetailsBean tb) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("emailauth");
		tb.setCgCode("G");

		if(this.insTeam2(tb)) {
			mav.setViewName("team");
		}else{mav.setViewName("emailauth");}

		return mav;
	}
	//전체 리스트(뷰)에서  이름을 복호화한다.
	public List<UserBean> searchList(UserBean ub) {
		List<UserBean> list = sqlSession.selectList("searchList", ub);	
		List<UserBean> searchlist = list;
		boolean searchvalid = false;
		for(int i=0; i<list.size(); i++) {			
			try {
				list.get(i).setUName(enc.aesDecode(list.get(i).getUName() ,list.get(i).getUCode()));	
			} catch (Exception e) {
				e.printStackTrace();
			}			
			String amount = list.get(i).getUCode()+list.get(i).getUName();
			if(amount.contains(ub.getSearch())) {
				searchlist.get(i).setUCode(list.get(i).getUCode());	
				searchlist.get(i).setUName(list.get(i).getUName());	
			}
		}
		return searchlist;
	}
}

/*DATASOURCETRANSACTIONMANAGER
 * -선언적 트랜잭션 : aop방식
 * @Transactional 어노테이션을 이용해 클래스나 하위 메서드에 적용가능
 * --> 관련된 메서드는 반드시 public, default 접근 제한자를 사용해야함
 * - 명시적 트랜잭션 Programa Transaction
 *
 *--환경설정
 *  ->propacation : 전파방식
 *  	ins1  ->   ins2 -->   de3l  이렇게되면 3
 *  	TRAN  <-  TRAN  <-  TRAN
 *  
 *  	INS1{COMMIT || ROLLBACK
 *  		INS2{
 *  
 *  }
 *  }
 *  이게 required. 가장 많이 쓰이고 보편적임.. INS1을 통해 ins2도 종속 됨.
 * 
 *  
 *  
 *  
 *  ->isolation : 격리수준
 *  
 *   비행기표 예매 ->Read Uncommited (좌석->결제 선점)
 *
 * 
 * */


/*PROPAGATION
 * -REQUIRED : DEFAULT VALUE 이미 시작된 트랜잭션이 있으면 참여하고 없으면 새로 시작한다.
 * -SUPPORTS : 이미 시작된 트랜잭션이 있는 경우 참여하고 그렇지않으면 트랜잭션 없이 진행
 * -MANDATORY : 이미 시작된 트랜잭션이 있는 경우 참여하고  그렇지 않으면 예외를 발생.
 *-REQUIRES_NEW : 모두 새로운 트랜잭션 , 사용할 일 많지 안흥ㅁ
 *-NOT_SUPPORTED : 트랜젹션을 사용하지 않음             
 * 
 * */

/*ISOLATION
 * 동시에 여러 트랜잭션이 진행될 때 특정한 트랜잭션의 결과를 다른 트랜잭션에 노출시킬 방법
 * 
 * -DEFAULT : READ_COMMITED 다른거참조x커밋된것만 참조해라
 * -READ_UNCOMMITED : 가장 낮은수준의 isolation
 * -SERIALIZATION : 가장 강력한 격리 수준
 * 
 * */

