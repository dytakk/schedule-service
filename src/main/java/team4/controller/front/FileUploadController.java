package team4.controller.front;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileUploadController {

	
	@PostMapping("/upload")
	public String upload(Model model, @RequestParam("file1") MultipartFile file) {
		System.out.println("dd");
	
		return "redirect:/";
	}
	
	

}
