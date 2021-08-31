package team4.database.mapper;

import java.util.List;

import team4.user.beans.TDetailsBean;
import team4.user.beans.TeamBean;

public interface FriendsInterface {
	
	public List<TeamBean> getTeamList(TeamBean tb);
	public List<TDetailsBean> getMemberList(TDetailsBean tb);
	public int getNowNumber(TDetailsBean tb);
	public boolean insTeam(TeamBean tb);
	public List<TDetailsBean> friendsList(TDetailsBean tb);
	
	

}
