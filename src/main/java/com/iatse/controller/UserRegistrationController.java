package com.iatse.controller;
import javax.validation.Valid;

import com.iatse.dto.UserRegistrationDto;
import com.iatse.model.Member;
import com.iatse.repository.MemberRepository;
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
        if (result.hasErrors()){
            return "registration";
        }
        Member newMember = new Member();
        newMember.setEmail(userDto.getEmail());
        newMember.setFirstName(userDto.getFirstName());
        newMember.setLastName(userDto.getLastName());
        newMember.setPassword(userDto.getPassword());
        Member createdMember = memberRepository.save(newMember);
        log.info("New member created: {}", createdMember);
        return "redirect:/registration?success";
    }

}
