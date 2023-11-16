package com.Smart.controller;

import java.util.Random;

import javax.mail.Session;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Smart.EmailService;
import com.Smart.dao.UserRepository;
import com.Smart.entities.User;

@Controller
public class forgotController {

	// generating otp of 4 digit
	Random random = new Random(System.currentTimeMillis());
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private EmailService emailservice;
	@Autowired
	private UserRepository userRepository;

	// email id form open handler..............................

	@RequestMapping("/forgot")
	public String openEmailFrom() {
		return "forgot_email_form";
	}

	@PostMapping("/send-otp")
	public String SendOTP(@RequestParam("email") String email, HttpSession session) {
		System.out.println("Email - " + email);

		int otp = random.nextInt(90000) + 10000;
		System.out.println("OTP " + otp);

		String subject = "OTP from SCM.";

		String message = "<div style='border:1px solid #e2e2e2;padding:20px'>" + "<h1>OTP is <b>" + otp + "</b></h1>"
				+ "</div>";

		String to = email;

		boolean flag = this.emailservice.sendEmail(message, subject, to);

		if (flag) {
			session.setAttribute("myotp", otp);
			session.setAttribute("email", email);
			return "Verify_OTP";
		} else {
			session.setAttribute("message", "Check your email id !!");
			return "forgot_email_form";
		}
	}

	// verify otp
	@PostMapping("/verify-otp")
	public String verifyotp(@RequestParam("otp") int otp, HttpSession session) {
		int myOtp = (int) session.getAttribute("myotp");
		String Email= (String) session.getAttribute("email");
		
		if(myOtp==otp)
		{
			User user = this.userRepository.getUserByByUserName(Email);
			if(user==null)
			{
				//send error message
				session.setAttribute("message", "User does not exist with this email !!");
				return "forgot_email_form";
			}
			else
			{
				//send change password
				
				
			}
			return "password_change_form";
		}
		else
		{
			session.setAttribute("message", "You have entered wrong OTP ");
			return "Verify_OTP";
		}
	}
	//change password
	@PostMapping("/change-password")
	public String changePassword(@RequestParam("newpassword") String newpassword,HttpSession session)
	{
		String email = (String) session.getAttribute("email");
		User user = this.userRepository.getUserByByUserName(email);
		user.setPassword(this.bCryptPasswordEncoder.encode(newpassword));
		this.userRepository.save(user);
		return "redirect:/signin?change=password changed successfully";
	}
	
	
	

}