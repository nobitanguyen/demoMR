package com.mr.springmvc.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller

public class IndexController {

//	 @RequestMapping(value = "/")
//	 public String getIndexPage() {
//	 return "Restaurant";
//	 }

	private static final Logger logger = Logger.getLogger(IndexController.class);
	
	@RequestMapping(value = "/login")
	public String loginPage() {
		logger.info("This is Login Page");
		return "Login";
	}

	@RequestMapping(value = { "/" })
	public String homePage(ModelMap model) {
		model.addAttribute("user", getPrincipal());
		return "Restaurant";
	}

    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){    
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";
    }

	@RequestMapping(value = "/Access_Denied", method = RequestMethod.GET)
	public String accessDeniedPage(ModelMap model) {
		model.addAttribute("user", getPrincipal());
		return "accessDenied";
	}

	private String getPrincipal() {
		String userName = null;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			Object principal = auth.getPrincipal();

			if (principal instanceof UserDetails) {
				userName = ((UserDetails) principal).getUsername();
			} else {
				userName = principal.toString();
			}
		}
		return userName;
	}

}