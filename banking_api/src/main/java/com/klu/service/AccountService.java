package com.klu.service;

import com.klu.model.AccountDetails;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    public AccountDetails getAccountDetails(String accountHolder) {
        // Return mock account details
        return new AccountDetails(5000, "INR", accountHolder, "709878798789879");
    }
}
