package com.example.leakage.controller;

import com.example.leakage.dto.ActionRequestDto;
import com.example.leakage.dto.CreateRequestDto;
import com.example.leakage.dto.LeakageRequestDto;
import com.example.leakage.model.Request;
import com.example.leakage.service.RequestService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/requests")
public class RequestController {

	private final RequestService service;

	public RequestController(RequestService service) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<Request> create(
			@RequestHeader("x-user-id") String userId,
			@RequestHeader("x-user-role") String role,
			@Valid @RequestBody CreateRequestDto dto) {
		if (!"maker".equals(role)) return ResponseEntity.status(403).build();
		return ResponseEntity.ok(service.create(dto, userId));
	}

	@GetMapping("/pending")
	public ResponseEntity<List<Request>> pending(
			@RequestHeader("x-user-role") String role) {
		if (!"approver".equals(role) && !"super_approver".equals(role)) return ResponseEntity.status(403).build();
		return ResponseEntity.ok(service.listPendingForRole(role));
	}

	@GetMapping("/history")
	public ResponseEntity<List<Request>> history(
			@RequestHeader("x-user-id") String userId,
			@RequestHeader("x-user-role") String role) {
		return ResponseEntity.ok(service.historyForUser(userId, role));
	}

	@PostMapping("/{id}/approve")
	public ResponseEntity<Request> approve(
			@RequestHeader("x-user-id") String userId,
			@RequestHeader("x-user-role") String role,
			@PathVariable long id,
			@RequestBody(required = false) ActionRequestDto body) {
		try {
			return ResponseEntity.ok(service.approve(id, userId, role));
		} catch (IllegalStateException e) {
			return ResponseEntity.status(403).build();
		}
	}

	@PostMapping("/{id}/reject")
	public ResponseEntity<Request> reject(
			@RequestHeader("x-user-id") String userId,
			@RequestHeader("x-user-role") String role,
			@PathVariable long id,
			@RequestBody(required = false) ActionRequestDto body) {
		try {
			return ResponseEntity.ok(service.reject(id, userId, role));
		} catch (IllegalStateException e) {
			return ResponseEntity.status(403).build();
		}
	}

	@PostMapping("/{id}/escalate")
	public ResponseEntity<Request> escalate(
			@RequestHeader("x-user-id") String userId,
			@RequestHeader("x-user-role") String role,
			@PathVariable long id,
			@RequestBody(required = false) ActionRequestDto body) {
		try {
			return ResponseEntity.ok(service.escalate(id, userId, role));
		} catch (IllegalStateException e) {
			return ResponseEntity.status(403).build();
		}
	}

	@PostMapping("/leakage")
	public ResponseEntity<Request> createLeakageRequest(
			@RequestHeader("x-user-id") String userId,
			@RequestHeader("x-user-role") String role,
			@Valid @RequestBody LeakageRequestDto dto) {
		if (!"maker".equals(role)) return ResponseEntity.status(403).build();
		return ResponseEntity.ok(service.createLeakageRequest(dto, userId));
	}

	@PostMapping("/{id}/send-email")
	public ResponseEntity<Request> sendEmail(
			@RequestHeader("x-user-id") String userId,
			@RequestHeader("x-user-role") String role,
			@PathVariable long id) {
		try {
			return ResponseEntity.ok(service.sendEmailToOperation(id, userId, role));
		} catch (IllegalStateException e) {
			return ResponseEntity.status(400).body(null);
		}
	}
} 