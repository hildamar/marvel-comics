package com.albo.biblioteca.comics.persistence.repository;

import com.albo.biblioteca.comics.persistence.model.Character;
import com.albo.biblioteca.comics.persistence.model.ComicCharacter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComicCharacterRepository extends JpaRepository<ComicCharacter,Long> {
  public List<ComicCharacter> findByCharacter(Character character);
}
