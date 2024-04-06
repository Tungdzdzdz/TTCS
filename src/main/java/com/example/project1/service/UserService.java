package com.example.project1.service;

import com.example.project1.DTO.UserDTO;
import com.example.project1.DTO.UserLogin;
import com.example.project1.Exception.DataNotFoundException;
import com.example.project1.Exception.NotIdenticalPasswordException;
import com.example.project1.Model.Role;
import com.example.project1.Model.User;
import com.example.project1.repository.RoleRepository;
import com.example.project1.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    @Override
    public String createUser(UserDTO userDTO) throws Exception {
        if(!userDTO.getPassword().equals(userDTO.getRetypePassword()))
        {
            throw new NotIdenticalPasswordException("The password is not identical");
        }
        Role role = roleRepository
                .findById(1)
                .orElseThrow(() -> new DataNotFoundException("The role is not found!"));
        User user = User
                .builder()
                .username(userDTO.getUsername())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .role(role)
                .email(userDTO.getEmail())
                .build();
        userRepository.save(user);
        return jwtService.generateToken(user);
    }
    @Override
    public String login(UserLogin userLogin) throws Exception {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userLogin.getUsername(),
                        userLogin.getPassword()
                )
        );
        User user = userRepository.findByUsername(userLogin.getUsername()).orElseThrow(() -> new DataNotFoundException("User is not found!"));
        return jwtService.generateToken(user);
    }
}
