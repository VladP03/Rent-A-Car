package com.rentacar.service;

import com.rentacar.model.UserDTO;
import com.rentacar.model.adapters.UserAdapter;
import com.rentacar.model.validations.OnCreate;
import com.rentacar.repository.user.User;
import com.rentacar.repository.user.UserRepository;
import com.rentacar.security.BCryptPasswordEncoder;
import com.rentacar.security.UserPrincipal;
import com.rentacar.service.exceptions.dataIntegrity.EmailUniqueConstraintException;
import com.rentacar.service.exceptions.dataIntegrity.UsernameUniqueException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.*;

@Service
@Validated
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;



    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userFounded = userRepository.findByUsername(username);

        if (!userFounded.isPresent()) {
            throw new RuntimeException();
        }

        return new UserPrincipal(userFounded.get());
    }



    public List<UserDTO> getUser() {
        return UserAdapter.toListDTO(userRepository.findAll());
    }

    @Validated(OnCreate.class)
    public UserDTO createUser(@Valid UserDTO userDTO) {
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        try {
            return UserAdapter.toDTO(userRepository.save(UserAdapter.fromDTO(userDTO)));
        } catch (DataIntegrityViolationException exception) {
            if (!isUniqueUsername(userDTO.getUsername())) {
                throw new UsernameUniqueException(userDTO);
            }

            if (!isUniqueEmail(userDTO.getEmail())) {
                throw new EmailUniqueConstraintException(userDTO);
            }

            throw new DataIntegrityViolationException(Objects.requireNonNull(exception.getMessage()));
        }
    }



    private boolean isUniqueUsername(String username) {
        Optional<User> userFounded = userRepository.findByUsername(username);

        return !userFounded.isPresent();
    }

    private boolean isUniqueEmail(String email) {
        Optional<User> userFounded = userRepository.findByEmail(email);

        return !userFounded.isPresent();
    }
}
