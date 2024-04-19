package com.example.project1.service;

import com.example.project1.DTO.UserDTO;
import com.example.project1.DTO.UserLogin;
import com.example.project1.Exception.DataNotFoundException;
import com.example.project1.Model.User;

public interface IUserService {
    String createUser(UserDTO userDTO) throws Exception;
    String login(UserLogin userLogin) throws Exception;
    String loginAdmin(UserLogin userLogin) throws Exception;
}
