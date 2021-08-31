package team4.database.mapper;


import java.util.List;

import team4.user.beans.AccessInfo;
import team4.user.beans.UserBean;

public interface MybatisInterface {

	int isUserId(AccessInfo ai);
	int isAccess(AccessInfo ai);
	void insAccessHistory(AccessInfo ai);
	List<UserBean> selMemberInfo(AccessInfo ai);
	void insMembers(UserBean ub);
	boolean insLogOut(AccessInfo ai);
	int forceLogOut(AccessInfo ai);
	int isCurrentAccess(AccessInfo ai);
	
}
