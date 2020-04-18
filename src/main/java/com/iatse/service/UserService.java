package com.iatse.service;
import com.iatse.dto.UserRegistrationDto;
import com.iatse.model.Member;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    Member save(UserRegistrationDto registration);
}
