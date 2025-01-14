package com.example.project1.service;

import com.example.project1.DTO.UserDTO;
import com.example.project1.DTO.UserLogin;
import com.example.project1.Exception.DataNotFoundException;
import com.example.project1.Exception.NotIdenticalPasswordException;
import com.example.project1.Model.Role;
import com.example.project1.Model.User;
import com.example.project1.repository.RoleRepository;
import com.example.project1.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
                .findById(userDTO.getRoleId() == null ? 1 : userDTO.getRoleId())
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
        User user = userRepository.findByUsername(userLogin.getUsername()).orElseThrow(() -> new DataNotFoundException("User is not found!"));
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    userLogin.getUsername(),
                    userLogin.getPassword()
            )
        );
        return jwtService.generateToken(user);
    }
    @Override
    public String loginAdmin(UserLogin userLogin) throws Exception {
        User user = userRepository.findByUsername(userLogin.getUsername()).orElseThrow(() -> new DataNotFoundException("User is not found!"));
        if(user.getRole().getId() != 2)
        {
            throw new UsernameNotFoundException("User is not admin");
        }
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    userLogin.getUsername(),
                    userLogin.getPassword()
            )
        );
        return jwtService.generateToken(user);
    }
    @Override
    public List<User> getAllUsers() throws DataNotFoundException {
        return userRepository.findAll();
    }
    @Override
    public void updateUser(UserDTO userDTO) throws Exception {
        User user = userRepository.findById(userDTO.getId()).orElseThrow(() -> new DataNotFoundException("User is not found!"));
        user.setEmail(userDTO.getEmail());
        if(userDTO.getPassword().length() > 8)
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        else if(userDTO.getPassword().length() > 0 && userDTO.getPassword().length() < 8)
            throw new Exception("Password length must be greater than 7");
        user.setUsername(userDTO.getUsername());
        user.setRole(roleRepository.findById(userDTO.getRoleId()).orElseThrow(() -> new DataNotFoundException("Role is not found!")));
        userRepository.save(user);
    }
    @Override
    public void deleteUser(Integer id) throws DataNotFoundException {
        userRepository.deleteById(id);
    }
    @Override
    public User getUserByUsername(String username) throws DataNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new DataNotFoundException("User is not found!"));
    }
}
