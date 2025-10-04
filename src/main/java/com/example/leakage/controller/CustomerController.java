package com.example.leakage.controller;

import com.example.leakage.dto.CAItemDto;
import com.example.leakage.dto.UserInfoDto;
import com.example.leakage.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

	private final CustomerService customerService;

	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}

	@GetMapping("/search")
	public ResponseEntity<List<CAItemDto>> searchByIdCard(
			@RequestHeader("x-user-id") String userId,
			@RequestHeader("x-user-role") String role,
			@RequestParam String idCard) {
		if (!"maker".equals(role)) return ResponseEntity.status(403).build();
		return ResponseEntity.ok(customerService.searchByIdCard(idCard));
	}

	@GetMapping("/{caId}/info")
	public ResponseEntity<UserInfoDto> getUserInfo(
			@RequestHeader("x-user-id") String userId,
			@RequestHeader("x-user-role") String role,
			@PathVariable String caId) {
		if (!"maker".equals(role)) return ResponseEntity.status(403).build();
		return ResponseEntity.ok(customerService.getUserInfo(caId));
	}
}
