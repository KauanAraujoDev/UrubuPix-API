package com.example.demo;
import com.example.demo.utils.DBBalanceConnection;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
		var balance = new DBBalanceConnection();
		balance.multipleBalance();
	}
}