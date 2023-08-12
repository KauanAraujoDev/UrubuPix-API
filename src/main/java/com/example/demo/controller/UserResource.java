package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.utils.DBUserConnection;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserResource {
    DBUserConnection db = new DBUserConnection();

    @GetMapping
    public ResponseEntity<List<User>> getUsers() throws SQLException {
        return ResponseEntity.ok().body(db.Users());
    }
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable int id) {
        return ResponseEntity.ok().body(db.deleteUser(id));
    }
    @PutMapping(value = "/create/{username}&{age}&{plan}")
    public ResponseEntity<Boolean> createUser(@PathVariable String username, @PathVariable int age, @PathVariable String plan) {
        return ResponseEntity.ok().body(db.createUser(username, age, plan));
    }
}