package team4.services.auth;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.mobile.device.DeviceUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ProjectUtils {

	/* RequestContetHolder Class --> Spring 3.0 이상
	 *  : ThreadLocal를 사용해서 현재 쓰레드에 RequestAttributes 인스턴스를 바인딩 해두었다가 요청을 하면 이 인스턴스를 돌려주는 역할을 합니다.
	 *  : ThreadLocal을 사용하는 경우 ThreadLocal 변수에 보관된 데이터의 사용이 끝나면 반드시 삭제 해야 재사용되는 쓰레드의 잘못된 참조를 방지
	 *    그렇지 않으면, 재사용되는 쓰레드가 올바르지 않은 데이터를 참조할 수 있다.
	 *    
	 * org.springframework.mobile   spring-mobile-device
	 */

	public int screenType(int serviceCode){
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		return serviceCode * (isMobile(request)? -1 : 1);
	}

	private boolean isMobile(HttpServletRequest request){
		return DeviceUtils.getCurrentDevice(request).isMobile();
	}

	/* Session영역으로부터 attribute 값을 가져 오기 위한 method  */
	public Object getAttribute(String name) throws Exception {
		return (Object) RequestContextHolder.getRequestAttributes().getAttribute(name, RequestAttributes.SCOPE_SESSION);
	}

	/* Session영역에 attribute 설정 method */
	public void setAttribute(String name, Object object) throws Exception {
		RequestContextHolder.getRequestAttributes().setAttribute(name, object, RequestAttributes.SCOPE_SESSION);
	}

	/* Session영역에 설정된 attribute 삭제 */
	public void removeAttribute(String name) throws Exception {
		RequestContextHolder.getRequestAttributes().removeAttribute(name, RequestAttributes.SCOPE_SESSION);
	}

	/* Session영역의 SessionId값을 가져오기 위한 Method */
	public String getSessionId() throws Exception  {
		return RequestContextHolder.getRequestAttributes().getSessionId();
	}

	/*Upload된 파일을 Local Repository에 저장 : savingFile(MultipartFile), savingFile(MultipartFile[]) */
	public String savingFile(MultipartFile file) {
		//  /Users/tagdaeyeong/eclipse_workspace/Spring/team4/src/main/webapp/resources/images/cat.png
		String uploadFileLocation=File.separator+"Users"+File.separator+"tagdaeyeong"+File.separator+"eclipse_workspace"+File.separator+"Spring"+File.separator+"team4"+File.separator+"src"+File.separator+"main"+File.separator+"webapp"+File.separator+"resources"+File.separator+"image"+File.separator;
		String fileInfo = null;
		//중복을 회피할 파일이름 지정
		UUID uuid = UUID.randomUUID();
		String savingFileName= uuid + file.getOriginalFilename();
		//업로드 된 파일의 Content Type 
		String contentType= file.getContentType();
		//업로드된 파일의 사이즈
		long fileSize = file.getSize();		
		//Storage에 실제 파일을 저장(전달)하기위해
		File sfile = new File(uploadFileLocation, savingFileName);
		try {
			file.transferTo(sfile);
			fileInfo = savingFileName;
		} catch (IllegalStateException e) {e.printStackTrace();
		} catch (IOException e) {e.printStackTrace();
		}		
		return fileInfo;
	}
	public String[] savingFile(MultipartFile[] file) {
		//  /Users/tagdaeyeong/eclipse_workspace/Spring/team4/src/main/webapp/resources/images/cat.png
		String uploadFileLocation=File.separator+"Users"+File.separator+"tagdaeyeong"+File.separator+"eclipse_workspace"+File.separator+"Spring"+File.separator+"team4"+File.separator+"src"+File.separator+"main"+File.separator+"webapp"+File.separator+"resources"+File.separator+"image"+File.separator;
		//사이즈니까 인덱스가 아님! 착각하며 안됨 -인성조한테 두드려마증ㅁ-
		String[] fileInfo = new String[file.length];

		//중복을 회피할 파일이름 지정
		UUID uuid = UUID.randomUUID();

		for(int i=0; i<file.length; i++) {
			String savingFileName= uuid + file[i].getOriginalFilename();
			//업로드 된 파일의 Content Type 
			String contentType= file[i].getContentType();
			//업로드된 파일의 사이즈
			long fileSize = file[i].getSize();		
			//Storage에 실제 파일을 저장(전달)하기위해
			File sfile = new File(uploadFileLocation, savingFileName);
			try {
				file[i].transferTo(sfile);
				fileInfo[i] = savingFileName;
			} catch (IllegalStateException e) {e.printStackTrace();
			} catch (IOException e) {e.printStackTrace();
			}
		}
		return fileInfo;
	}
}
