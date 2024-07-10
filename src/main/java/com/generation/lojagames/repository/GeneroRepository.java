package com.generation.lojagames.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.generation.lojagames.model.Genero;

public interface GeneroRepository extends JpaRepository<Genero, Long> {
	
	public List<Genero> findAllByDescricaoContainingIgnoreCase(@Param("descricao") String descricao);
}
