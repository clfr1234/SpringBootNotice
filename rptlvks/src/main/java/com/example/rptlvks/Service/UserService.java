package com.example.rptlvks.Service;

import com.example.rptlvks.Entity.Role;
import com.example.rptlvks.Entity.User;
import com.example.rptlvks.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws  UsernameNotFoundException{
        Optional<User> user = userRepository.findById(username);

        if(user.isPresent()) {
            User roleUser = user.get();

            User authUser = User.builder()
                    .idx(roleUser.getIdx())
                    .name(roleUser.getName())
                    .id(roleUser.getId())
                    .email(roleUser.getEmail())
                    .password(roleUser.getPassword())
                    .jdate(roleUser.getJdate())
                    .role(roleUser.getRole())
                    .build();
            return authUser;
        }
        return null;
    }

    public User registerSetting(User user) {

        LocalDateTime localDateTime = LocalDateTime.now();
        Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        user.setJdate(date);

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        user.setRole(Role.USER.getValue());
        return userRepository.save(user);
    }

    public String id_search(String id) {
        Optional<User> userOptional = userRepository.findById(id);

       if(userOptional.isPresent()) {
           return "DUPLICATION";
       }else{
           return "NOT DUPLICATION";
       }
    }
}