package istu.bacs.web.controller;

import istu.bacs.domain.User;
import istu.bacs.service.UserService;
import istu.bacs.service.UsernameAlreadyInUseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	public String register(@ModelAttribute User user) {
		try {
			userService.register(user);
		} catch (UsernameAlreadyInUseException ignored) {
			return "redirect:/register";
		}
		return "redirect:/login";
	}
}