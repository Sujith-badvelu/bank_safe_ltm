package com.edutech.progressive.controller;

import com.edutech.progressive.entity.Accounts;
import com.edutech.progressive.service.AccountService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping(value = "/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountController {

    private final AccountService accountService; // JPA-backed via interface

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    // GET /accounts
    @GetMapping
    public ResponseEntity<List<Accounts>> getAllAccounts() {
        try {
            List<Accounts> list = accountService.getAllAccounts();
            return ResponseEntity.ok(list);
        } catch (SQLException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // GET /accounts/{accountId}
    @GetMapping("/{accountId}")
    public ResponseEntity<Accounts> getAccountById(@PathVariable int accountId) {
        try {
            Accounts acc = accountService.getAccountById(accountId);
            return ResponseEntity.ok(acc);
        } catch (SQLException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // GET /accounts/user/{userId}
    // Day 7 tests typically expect userId as an int and a JSON list in the response.
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Accounts>> getAccountsByUser(@PathVariable("userId") int userId) {
        try {
            List<Accounts> list = accountService.getAccountsByUser(userId);
            return ResponseEntity.ok(list);
        } catch (SQLException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // POST /accounts
    // Day 7: must return 201 Created + Location header + integer id in body.
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> addAccount(@RequestBody Accounts accounts) {
        try {
            int id = accountService.addAccount(accounts);

            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create("/accounts/" + id));

            return new ResponseEntity<>(id, headers, HttpStatus.CREATED);
        } catch (SQLException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // PUT /accounts/{accountId}
    @PutMapping(value = "/{accountId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateAccount(@PathVariable int accountId, @RequestBody Accounts accounts) {
        try {
            accounts.setAccountId(accountId);
            accountService.updateAccount(accounts);
            return ResponseEntity.ok().build();
        } catch (SQLException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // DELETE /accounts/{accountId}
    @DeleteMapping("/{accountId}")
    public ResponseEntity<Void> deleteAccount(@PathVariable int accountId) {
        try {
            accountService.deleteAccount(accountId);
            return ResponseEntity.ok().build();
        } catch (SQLException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}