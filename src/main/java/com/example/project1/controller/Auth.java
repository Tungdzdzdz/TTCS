package com.example.project1.controller;

import com.example.project1.DTO.UserDTO;
import com.example.project1.DTO.UserLogin;
import com.example.project1.Model.User;
import com.example.project1.Response.ErrorResponse;
import com.example.project1.Response.Token;
import com.example.project1.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("http://localhost:5173")
@RequiredArgsConstructor
public class Auth {
    private final UserService userService;
    @PostMapping("api/v1/auth/register")
    public ResponseEntity<?> register(
            @Valid @RequestBody UserDTO userDTO,
            BindingResult bindingResult
    )
    {
        try {
            if(bindingResult.hasErrors()) 
            {
                List<ErrorResponse> errorList = bindingResult
                        .getFieldErrors()
                        .stream()
                        .map(fieldError -> new ErrorResponse(fieldError.getField(), fieldError.getDefaultMessage()))
                        .toList();
                return ResponseEntity.badRequest().body(errorList);
            }
            userService.createUser(userDTO);
            return ResponseEntity.ok().body(null);
        }
        catch (Exception e)
        {
            List<ErrorResponse> errorList = new ArrayList<>();
            errorList.add(new ErrorResponse(e.getLocalizedMessage(), e.getMessage()));
            return ResponseEntity.badRequest().body(errorList);
        }
    }
    @PostMapping("api/v1/auth/login")
    public ResponseEntity<?> login(
            @Valid @RequestBody UserLogin userLogin,
            BindingResult bindingResult
    )
    {
        try{
            if(bindingResult.hasErrors())
            {
                List<ErrorResponse> errorList = bindingResult
                        .getFieldErrors()
                        .stream()
                        .map(fieldError -> new ErrorResponse(fieldError.getField(), fieldError.getDefaultMessage()))
                        .toList();
                return ResponseEntity.badRequest().body(errorList);
            }
            User user = userService.getUserByUsername(userLogin.getUsername());
            Token token = new Token(userService.login(userLogin), user.getRole().getName());
            return ResponseEntity.ok(token);
        }
        catch (Exception e)
        {
            List<ErrorResponse> errorList = new ArrayList<>();
            errorList.add(new ErrorResponse(e.getLocalizedMessage(), e.getMessage()));
            return ResponseEntity.badRequest().body(errorList);
        }
    }
    
    @PostMapping("api/v1/auth/login/admin")
    public ResponseEntity<?> loginAdmin(
            @Valid @RequestBody UserLogin userLogin,
            BindingResult bindingResult
    )
    {
        try{
            if(bindingResult.hasErrors())
            {
                List<ErrorResponse> errorList = bindingResult
                        .getFieldErrors()
                        .stream()
                        .map(fieldError -> new ErrorResponse(fieldError.getField(), fieldError.getDefaultMessage()))
                        .toList();
                return ResponseEntity.badRequest().body(errorList);
            }
            User user = userService.getUserByUsername(userLogin.getUsername());
            Token token = new Token(userService.loginAdmin(userLogin), user.getRole().getName());
            return ResponseEntity.ok(token);
        }
        catch (Exception e)
        {
            List<ErrorResponse> errorList = new ArrayList<>();
            errorList.add(new ErrorResponse(e.getLocalizedMessage(), e.getMessage()));
            return ResponseEntity.badRequest().body(errorList);
        }
    }

    @GetMapping("api/v1/auth/me")
    public ResponseEntity<?> me(Principal principal)
    {
        try {
            return ResponseEntity.ok(userService.getUserByUsername(principal.getName()));
        } catch (Exception e) {
            List<ErrorResponse> errorList = new ArrayList<>();
            errorList.add(new ErrorResponse(e.getLocalizedMessage(), e.getMessage()));
            return ResponseEntity.badRequest().body(errorList);
        }
    }
}