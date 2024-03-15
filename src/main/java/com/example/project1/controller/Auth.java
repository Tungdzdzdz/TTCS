package com.example.project1.controller;

import com.example.project1.DTO.UserDTO;
import com.example.project1.DTO.UserLogin;
import com.example.project1.Response.ErrorResponse;
import com.example.project1.Response.Token;
import com.example.project1.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1/auth")
@CrossOrigin("http://localhost:5173")
@RequiredArgsConstructor
public class Auth {
    private final UserService userService;
    @PostMapping("register")
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
    @PostMapping("login")
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
            Token token = new Token(userService.login(userLogin));
            return ResponseEntity.ok(token);
        }
        catch (Exception e)
        {
            List<ErrorResponse> errorList = new ArrayList<>();
            errorList.add(new ErrorResponse(e.getLocalizedMessage(), e.getMessage()));
            return ResponseEntity.badRequest().body(errorList);
        }
    }
}
