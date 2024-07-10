package com.generation.lojagames.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.generation.lojagames.model.Genero;
import com.generation.lojagames.repository.GeneroRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/generos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class GeneroController {
	
	@Autowired
	private GeneroRepository generoRepository;
	
	@GetMapping
	public ResponseEntity<List<Genero>> getAll() {
		return ResponseEntity.ok(generoRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Genero> getById(@PathVariable Long id) {
		return generoRepository.findById(id)
				.map(response -> ResponseEntity.ok(response))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
	@GetMapping("/descricao/{descricao}")
	public ResponseEntity<List<Genero>> getByTitle(@PathVariable String descricao) {
		return ResponseEntity.ok(generoRepository.findAllByDescricaoContainingIgnoreCase(descricao));
	}
	
	@PostMapping
	public ResponseEntity<Genero> post(@Valid @RequestBody Genero genero) {
		return ResponseEntity.status(HttpStatus.CREATED).body(generoRepository.save(genero));
	}
	
	@PutMapping
	public ResponseEntity<Genero> put(@Valid @RequestBody Genero genero) {
		return generoRepository.findById(genero.getId())
				.map(response -> ResponseEntity.status(HttpStatus.CREATED)
				.body(generoRepository.save(genero)))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		Optional<Genero> genero = generoRepository.findById(id);
		
		if(genero.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		
		generoRepository.deleteById(id);
	}
}
