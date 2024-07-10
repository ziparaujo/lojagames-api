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

import com.generation.lojagames.model.Game;
import com.generation.lojagames.repository.GameRepository;
import com.generation.lojagames.repository.GeneroRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class GameController {
	
	@Autowired
	private GameRepository gameRepository;
	
	@Autowired
	private GeneroRepository generoRepository;
	
	@GetMapping
	public ResponseEntity<List<Game>> getAll() {
		return ResponseEntity.ok(gameRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Game> getById(@Valid @PathVariable Long id) {
		return gameRepository.findById(id)
				.map(response -> ResponseEntity.ok(response))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
	@GetMapping("/nome/{nome}")
	public ResponseEntity<List<Game>> getByNome(@PathVariable String nome) {
		return ResponseEntity.ok(gameRepository.findAllByNomeContainingIgnoreCase(nome));
	}
	
	@PostMapping
	public ResponseEntity<Game> post(@Valid @RequestBody Game game) {
		if(generoRepository.existsById(game.getGenero().getId()))
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(gameRepository.save(game));
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Genero inexistente.", null);
	}
	
	@PutMapping
	public ResponseEntity<Game> put(@Valid @RequestBody Game game) {
		if(generoRepository.existsById(game.getId())) {
			if(generoRepository.existsById(game.getGenero().getId()))
				return ResponseEntity.status(HttpStatus.OK)
						.body(gameRepository.save(game));
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Genero inexistente.", null);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		Optional<Game> game = gameRepository.findById(id);
		
		if(game.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		
		gameRepository.deleteById(id);
	}
}
