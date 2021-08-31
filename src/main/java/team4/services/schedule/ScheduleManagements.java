package team4.services.schedule;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import oracle.net.aso.s;
import team4.database.mapper.ScheduleInterface;
import team4.services.auth.ProjectUtils;
import team4.user.beans.ScheduleBean;
import team4.user.beans.UserBean;

@Service
public class ScheduleManagements implements ScheduleInterface{

	@Autowired
	SqlSessionTemplate sqlSession;
	@Autowired
	ProjectUtils pu;

	public ModelAndView ctl(ScheduleBean sb,UserBean ub) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("schedule");
		//UserBean에 있는 Mpfile은 배열이며, schedule.jsp, js단에서 form으로 전송. 이때 name값이 bean이름인 mpfile과 동일.
		//즉 파일첨부 전송하는순간 form을통해 전송되며 bean으로 저장.(set됨.)
		for(int i=0; i<ub.getMpfile().length; i++) {
			sb.setTeCode("21080052");
			sb.setNum(0);
			sb.setPath(pu.savingFile(ub.getMpfile()[i]).toString());
			if(this.insAlbum(sb)) {
				System.out.println("ALB UM UPLOAD SUCCESSED");
			}else {System.out.println("ALBUM UPLOAD FAILED");
			}		
		}
		return mav;
	}

	public boolean insAlbum(ScheduleBean sb) {

		return this.converToBoolean(sqlSession.insert("insAlbum",sb));
	}

	public boolean converToBoolean (int data) {

		return data>0? true: false;
	}


	public void insertSchedule (ScheduleBean sb) {

		//시퀀스 써라? #{num},	#{open}=open을가져오고 나중애ㅔ 스케줄확일떄 open을가져와서 o면 오픈 n이면 비공개

		sb.setNum(this.getMaxNum(sb)+1);
		sb.setProcess("B");
		this.insSD(sb);	
		if(sb.getMpfile()!=null) {

			for(int i=0; i<sb.getMpfile().length; i++) {

				sb.setPath("resources/image/"+pu.savingFile(sb.getMpfile()[i]));
				this.insAL(sb);
			}
		}
	}

	public void schedule(ScheduleBean sb) {

		this.monthSchedule(sb);
	}
	public boolean insSD(ScheduleBean sb) {

		return this.converToBoolean(sqlSession.insert("insSD",sb));	


	}

	public int getMaxNum(ScheduleBean sb) {


		return sqlSession.selectOne("getMaxNum",sb);
	}


	public boolean insAL(ScheduleBean sb) {


		return this.converToBoolean(sqlSession.insert("insAL",sb));
	}


	public List<ScheduleBean> monthSchedule (ScheduleBean sb) {

		return sqlSession.selectList("monthSchedule", sb);
	}


}
