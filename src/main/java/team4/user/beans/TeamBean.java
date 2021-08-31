package team4.user.beans;


import java.util.List;

import lombok.Data;


@Data
//@NoargsConstructor   생성자가 없습니다.
public class TeamBean {
	
	private String teCode;
	private String teName;
	private String mbId;
	
	private List<TDetailsBean> tdetails;

}
//모든 데이터를 다넘겨야하기때문에 서버방식에서는 비효율적 list부분..각 빈이 리스트로있