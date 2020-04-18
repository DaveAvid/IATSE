package com.iatse.controller;
import javax.validation.Valid;

import com.iatse.dto.UserRegistrationDto;
import com.iatse.model.Member;
import com.iatse.repository.MemberRepository;
import com.iatse.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@Slf4j
@Controller
@RequestMapping("/registration")
public class UserRegistrationController {
    @Autowired
    UserService userService;
    @Autowired
    MemberRepository memberRepository;
    @Value("${iatse.accessCode}")
    String accessCode;
    @GetMapping
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserRegistrationDto());
        return "registration";
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute("user") @Valid UserRegistrationDto userDto,
                                      BindingResult result){
        Member existingMember = memberRepository.findByEmail(userDto.getEmail());
        if (existingMember != null) {
            log.warn("Duplicate email registration attempted. Email:{}",existingMember.getEmail());
            result.rejectValue("email",null, "There is already an account registered with that email");
        }
        if (!userDto.getAccessCode().equals(accessCode)){
            log.warn("Invalid code entered. AccessCode:{}",userDto.getAccessCode());
            result.rejectValue("accessCode",null, "Invalid access code, are you sure you are a member?");
        }
        if (!userDto.getPassword().equals(userDto.getConfirmPassword())){
            log.warn("Passwords do not match. User:{}",userDto.getEmail());
            result.rejectValue("passwordMismatch",null, "Passwords do not match, please re-enter");
        }
        if (!userDto.getEmail().equals(userDto.getConfirmEmail())){
            log.warn("Email addresses do not match. Email:{}",userDto.getEmail());
            result.rejectValue("emailMismatch",null, "Email addresses do not match, please re-enter");
        }
        if (result.hasErrors()){
            return "registration";
        }
        Member createdMember = userService.save(userDto);
        log.info("New member created: {}", createdMember);
        return "redirect:/registration?success";
    }

}
