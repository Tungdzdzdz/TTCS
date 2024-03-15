package com.example.project1.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/demo")
public class Demo {
    @GetMapping
    public ResponseEntity<?> demo()
    {
        return ResponseEntity.ok("Hello user!");
    }
}
