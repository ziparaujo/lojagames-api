package com.generation.lojagames.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.generation.lojagames.model.Game;

public interface GameRepository extends JpaRepository<Game, Long> {

	public List <Game> findAllByNomeContainingIgnoreCase(@Param("nome") String nome);
}
