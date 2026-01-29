package com.aegisunderwrite.application.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aegisunderwrite.application.entity.Rule;
import com.aegisunderwrite.application.repository.RuleRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/rules")
@RequiredArgsConstructor
public class RuleController {

	private final RuleRepository ruleRepository;

	@GetMapping
	public ResponseEntity<List<Rule>> getAllRules() {
		return ResponseEntity.ok(ruleRepository.findAll());
	}

	@GetMapping("/active")
	public ResponseEntity<List<Rule>> getActiveRules() {
		return ResponseEntity.ok(ruleRepository.findByActiveTrueOrderByPriorityAsc());
	}

	@PostMapping
	public ResponseEntity<Rule> createRule(@Valid @RequestBody Rule rule) {
		return ResponseEntity.status(HttpStatus.CREATED).body(ruleRepository.save(rule));
	}

	@PatchMapping("/{id}/toggle")
	public ResponseEntity<Rule> toggleRule(@PathVariable UUID id) {
		Rule rule = ruleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Rule not found: " + id));
		rule.setActive(!rule.getActive());
		return ResponseEntity.ok(ruleRepository.save(rule));
	}
}