package team4.user.beans;


import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class UserBean {
	
	private String uCode;
	private String aCode;
	private String uName;	
	private String uMail;
	private String search;
	private String stickerPath;
	private MultipartFile[] mpfile;
	
}
