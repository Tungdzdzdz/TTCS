package com.example.project1.service;

import com.example.project1.DTO.UserDTO;
import com.example.project1.DTO.UserLogin;
import com.example.project1.Exception.DataNotFoundException;

public interface IUserService {
    String createUser(UserDTO userDTO) throws Exception;
    String login(UserLogin userLogin) throws Exception;
}
