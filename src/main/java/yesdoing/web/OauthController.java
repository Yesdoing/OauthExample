package yesdoing.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import httpSocial.FacebookLogin;

@Controller
public class OauthController {
	FacebookLogin login = new FacebookLogin();
	
	@GetMapping("receiveCode")
	public String show(String code, Model model) {
		login.useAPI(login.connect(code));
		return "receivedForm";
	}
	
	@GetMapping("refreshToken")
	public String refresh() {
		return "refreshtoken";
	}
}


