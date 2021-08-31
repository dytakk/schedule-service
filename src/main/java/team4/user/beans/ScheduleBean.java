package team4.user.beans;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class ScheduleBean {
	
	//yymmdd+번호//
	private String teCode;	
	private String teName;	
	private int num;
	private String mbId;
	private String mbName;
	private String title;
	private String date;
	private String location;
	private String contents;
	private String process;
	private String prName;
	private String open;
	private String opName;
	private String loop;
	private String loname;
	private String path;
	private MultipartFile[] mpfile;
}
