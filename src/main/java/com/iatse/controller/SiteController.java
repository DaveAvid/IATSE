package com.iatse.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
@Slf4j
@Controller
public class SiteController {


    @GetMapping("/")
    public String showIndex() {return "index";}

    @GetMapping("/calendar")
    public String showEvents(Principal principal) {
            log.info("principal");
    return "calendar";}

    @GetMapping("/about")
    public String showAbout() {return "about";}

    @GetMapping("/education")
    public String showNews() {return "education";}

    @GetMapping("/login")
    public String showLogin() {return "login";}

    @GetMapping("/resources")
    public String showResources() {return "resources";}

    @GetMapping("/admin")
    public String showAdmin() {return "admin";}

}
