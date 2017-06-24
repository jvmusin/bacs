package istu.bacs.web;

import istu.bacs.model.User;
import istu.bacs.service.UserService;
import istu.bacs.service.UsernameAlreadyInUseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {
	
	private static final String VIEWS_REGISTER_FORM = "register";
	private static final String VIEWS_LOGIN_FORM = "login";
	
	private final UserService userService;
	
	public AuthController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping("/login")
	public String loadLoginForm(Model model) {
		model.addAttribute("user", new User());
		return VIEWS_LOGIN_FORM;
	}
	
	@GetMapping("/register")
	public String loadRegisterForm(Model model) {
		model.addAttribute("user", new User());
		return VIEWS_REGISTER_FORM;
	}
	
	@PostMapping("/register")
	public String register(@ModelAttribute User user, Errors errors) {
		int usernameLen = user.getUsername().length();
		int passwordLen = user.getPassword().length();
		if (!(6 <= usernameLen && usernameLen <= 20))
			errors.rejectValue("username", "wronglen", "Username length should be inside [6, 20]");
		if (!(6 <= passwordLen && passwordLen <= 20))
			errors.rejectValue("password", "wronglen", "Password length should be inside [6, 20]");
		if (errors.hasErrors())
			return VIEWS_REGISTER_FORM;
		
		try {
			userService.register(user);
		} catch (UsernameAlreadyInUseException ignored) {
			errors.rejectValue("username", "taken", "This username is already taken");
			return VIEWS_REGISTER_FORM;
		}
		return "redirect:/login";
	}
}