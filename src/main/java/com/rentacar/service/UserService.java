package com.rentacar.service;

import com.rentacar.model.UserDTO;
import com.rentacar.model.adapters.UserAdapter;
import com.rentacar.repository.user.User;
import com.rentacar.repository.user.UserRepository;
import com.rentacar.security.UserPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
// implements UserDetailsService
public class UserService {

    private final UserRepository userRepository;
//    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
//        this.passwordEncoder = passwordEncoder;
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Optional<User> userFounded = userRepository.findByUsername(username);
//
//        if (!userFounded.isPresent()) {
//            throw new RuntimeException();
//        }
//
//        return new UserPrincipal(userFounded.get());
//    }

    public List<UserDTO> getUser() {
        return UserAdapter.toListDTO(userRepository.findAll());
    }

    public UserDTO createUser(UserDTO userDTO) {
        return null;
    }
}
