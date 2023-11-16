package com.JWTdemo.DEPOSYSTEM.CONTROLLER;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.JWTdemo.DEPOSYSTEM.Authentication.AuthenticationManager;
import com.JWTdemo.DEPOSYSTEM.Data.truckdata;
import com.JWTdemo.DEPOSYSTEM.SERVICE.UserService;
import com.JWTdemo.DEPOSYSTEM.Utility.JWTUtility;

@RestController
public class HomeController {
		
	@Autowired
	private truckdata service;
	 @Autowired
	 private JWTUtility jwtUtility;
	 @Autowired
	 private AuthenticationManager authenticationManager;
	 @Autowired
	 private UserService userService;
	 
	 
	@RequestMapping(value="/FASTAG/01",method=RequestMethod.GET,produces = "application/json")
	public String Retrivedata(@RequestBody Map<String, String> request)
	{
	String vehicleNumber = request.get("VehicleNumber");
	 String str = (String) vehicleNumber;
	Pattern pattern = Pattern.compile("^[A-Z0-9]{5,11}$");
	Matcher matcher = pattern.matcher(str);
	boolean flag =matcher.matches();
	String veh="GA060000";
	if(flag)
	{
	if(str.contentEquals(veh)==true)
	{
	return service.valid();
	}
	else
	{
	return service.NotExist();
	}
	}
	return service.invalid();
	}
	

}
