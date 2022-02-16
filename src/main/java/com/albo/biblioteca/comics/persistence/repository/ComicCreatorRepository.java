package com.albo.biblioteca.comics.persistence.repository;

import com.albo.biblioteca.comics.persistence.model.ComicCreator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComicCreatorRepository extends JpaRepository<ComicCreator,Long> {

}
