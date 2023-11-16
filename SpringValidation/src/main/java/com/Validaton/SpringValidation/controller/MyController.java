package com.Validaton.SpringValidation.controller;




import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


import com.Validaton.SpringValidation.entities.LoginData;

import jakarta.validation.Valid;

@Controller

public class MyController {
    
    @GetMapping("/form")
    public String openForm(Model model) {
        System.out.println("opening form");
        
        model.addAttribute("LoginData", new LoginData()); // Use "LoginData" as the attribute name
        return "form";
    }

    @PostMapping("/process")
    public String processForm(@Valid @ModelAttribute("LoginData") LoginData loginData, BindingResult result) {
        if (result.hasErrors()) {
            System.out.println(result);
            return "form";
        }
        System.out.println(loginData);
        return "success";
    }
}
