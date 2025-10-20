package com.belval.crudrest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UploadController {
	@GetMapping("/upload")
	public String getUploadPage() {
		return "upload";
	}

}
