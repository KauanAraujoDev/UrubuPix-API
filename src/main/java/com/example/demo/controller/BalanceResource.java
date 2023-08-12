package com.example.demo.controller;

import com.example.demo.model.Balance;
import com.example.demo.utils.DBBalanceConnection;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping(value = "/balance")
public class BalanceResource {
    DBBalanceConnection db = new DBBalanceConnection();
    @GetMapping(value = "/{id}&{username}")
    public ResponseEntity<List<Balance>> getBalance(@PathVariable int id, @PathVariable String username) throws SQLException {
        return ResponseEntity.ok().body(db.getBalance(id, username));
    }
    @PostMapping(value = "/add/{id}&{value}")
    public ResponseEntity<Boolean> addBalance(@PathVariable int id, @PathVariable int value) {
        return ResponseEntity.ok().body(db.addBalance(id, value));
    }
    @PostMapping(value = "/withdraw/{id}&{value}")
    public ResponseEntity<Boolean> withdraw(@PathVariable int id, @PathVariable int value) throws SQLException {
        return ResponseEntity.ok().body(db.moneyWithdraw(id, value));
    }
}