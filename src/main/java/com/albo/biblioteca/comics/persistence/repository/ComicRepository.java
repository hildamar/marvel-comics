package com.albo.biblioteca.comics.persistence.repository;

import com.albo.biblioteca.comics.persistence.model.Comic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComicRepository extends JpaRepository<Comic, Long> {
  public List<Comic> findByHero(String hero);
}
