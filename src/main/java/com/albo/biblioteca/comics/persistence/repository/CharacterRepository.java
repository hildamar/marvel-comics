package com.albo.biblioteca.comics.persistence.repository;

import com.albo.biblioteca.comics.persistence.model.Character;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CharacterRepository extends JpaRepository<Character,Long> {
  public List<Character> findByCharacterRelated(String characterRelated);
}
