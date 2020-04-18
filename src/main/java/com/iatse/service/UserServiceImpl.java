package com.iatse.service;

import com.iatse.dto.UserRegistrationDto;
import com.iatse.model.Member;
import com.iatse.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public Member findByEmail(String email){
        return memberRepository.findByEmail(email);
    }

    public Member save(UserRegistrationDto userDto){
        Member newMember = new Member();
        newMember.setEmail(userDto.getEmail());
        newMember.setFirstName(userDto.getFirstName());
        newMember.setLastName(userDto.getLastName());
        newMember.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return memberRepository.save(newMember);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member user = memberRepository.findByEmail(email);
        if (user == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        List<GrantedAuthority> roles=new ArrayList<>();
        roles.add(new SimpleGrantedAuthority("ROLE_USER"));
        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(),
                roles);

    }
}
