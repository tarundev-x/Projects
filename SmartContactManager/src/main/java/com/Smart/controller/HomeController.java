package com.Smart.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.Smart.dao.UserRepository;
import com.Smart.entities.User;
import com.Smart.helper.Message;

@Controller
public class HomeController {
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private UserRepository userrepository;

	// home page
	@RequestMapping("/")
	public String home(Model model) {
		model.addAttribute("title", "Home - Smart Contact Manager");
		return "home";
	}

	// about page

	@RequestMapping("/about")
	public String about(Model model) {
		model.addAttribute("title", "About - Smart Contact Manager");
		return "about";
	}

	// Registration page

	@RequestMapping("/signup")
	public String signup(Model model, HttpSession session)

	{
		model.addAttribute("title", "Register - Smart Contact Manager");
		model.addAttribute("user", new User());
		session.removeAttribute("message");
		// newly added
		return "signup";
	}

//	handler for registering user

	@RequestMapping(value = "/do_register", method = RequestMethod.POST)
	public String registeruser(@Valid @ModelAttribute("user") User user, BindingResult result1,
			@RequestParam(value = "agreement", defaultValue = "false") boolean agreement, Model model,
			HttpSession session) {

		try {

			if (!agreement) {
				System.out.println("You have not agreed to the terms and conditions!");

				throw new Exception("You have not agreed to the terms and conditions!");

			} else// newly added
			{
				session.removeAttribute("message");
			}

			if (result1.hasErrors()) {
				System.out.println("Validation errors: " + result1.toString());
				model.addAttribute("user", user);
				return "signup"; // Return to the registration form with validation errors

			}

			user.setRole("ROLE_USER");
			user.setEnabled(true);
			user.setImageUrl("default.png");
			user.setAgreed(agreement);
			user.setPassword(passwordEncoder.encode(user.getPassword()));

			// Remove the session attribute after it has been displayed
//			session.removeAttribute("message");

			System.out.println("agreement " + agreement);
			System.out.println("user " + user);

			User result = this.userrepository.save(user);

			model.addAttribute("user", new User());// reference-form

			session.setAttribute("message", new Message("Successfully Resgistered !", "alert-success"));

			return "signup";

		} catch (Exception e) {

			e.printStackTrace();

			model.addAttribute("user", user);

			session.setAttribute("message", new Message("Something went wrong !!" + e.getMessage(), "alert-danger"));

			return "signup";
		}

	}

	// hander for custom login

	@GetMapping("/signin")
	public String custumLogin(Model model) {

		model.addAttribute("title", "Login page");
		return "login";
	}

}
