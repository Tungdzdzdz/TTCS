package com.example.project1.controller;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.project1.service.NotifycationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/notifycation")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class NotifycationController {
    private final NotifycationService notifycationService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getNotifycationByUser(
        @PathVariable("userId") Integer userId,
        @RequestParam("from") Integer from
    )
    {
        return ResponseEntity.ok(notifycationService.getNotifycationByUser(userId, from));
    }

    @PutMapping("/seen")
    public ResponseEntity<?> seen(
        Principal principal
    )
    {
        notifycationService.seen(principal.getName());
        return ResponseEntity.ok("Seen all notifycation");
    }
}
