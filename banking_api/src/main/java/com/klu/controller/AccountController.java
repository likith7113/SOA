package com.klu.controller;

import com.klu.model.AccountDetails;
import com.klu.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/details")
    public ResponseEntity<AccountDetails> getAccountDetails(
            @RequestHeader(value = "Authorization", required = false) String authHeader) {
        
        String accountHolder = "likith"; 
        
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            // Token validation can be added here
        }
        
        AccountDetails details = accountService.getAccountDetails(accountHolder);
        return ResponseEntity.ok(details);
    }
}

