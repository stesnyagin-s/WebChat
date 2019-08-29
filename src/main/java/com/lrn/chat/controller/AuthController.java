package com.lrn.chat.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/")
public class AuthController {
    private static final Logger log = LoggerFactory.getLogger(ChatController.class);

    private final InMemoryUserDetailsManager inMemoryUserDetailsManager;

    @Autowired
    public AuthController(InMemoryUserDetailsManager inMemoryUserDetailsManager) {
        this.inMemoryUserDetailsManager = inMemoryUserDetailsManager;
    }

    @RequestMapping("/")
    public String getIndexPage() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication auth = securityContext.getAuthentication();
        if (auth != null && !(auth instanceof AnonymousAuthenticationToken) && auth.isAuthenticated()) {
            return "index";
        } else return "redirect:/login";
    }

    @RequestMapping("/login")
    public String getLoginPage() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication auth = securityContext.getAuthentication();
        if (auth != null && !(auth instanceof AnonymousAuthenticationToken) && auth.isAuthenticated()) {
            return "redirect:/";
        } else return "login";
    }

    @RequestMapping("/registration")
    public String getRegistrationPage() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication auth = securityContext.getAuthentication();
        if (auth != null && !(auth instanceof AnonymousAuthenticationToken) && auth.isAuthenticated()) {
            return "redirect:/";
        } else return "registration";
    }

    @RequestMapping(
            path = "/registration",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity say(@RequestParam("username") String username, @RequestParam("password1") String password1,
                              @RequestParam("password2") String password2) {
        if (!password1.equals(password2)) {
            return ResponseEntity.badRequest().body("Passwords are different");
        }
        if (username.equals("") || password1.equals("")) {
            return ResponseEntity.badRequest().body("Please fill all fields");
        }
        if (inMemoryUserDetailsManager.userExists(username)) {
            return ResponseEntity.badRequest().body("User with such username already exist");
        }
        UserDetails user =
                User.withDefaultPasswordEncoder()
                        .username(username)
                        .password(password1)
                        .roles("USER")
                        .build();
        inMemoryUserDetailsManager.createUser(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
