package com.albo.biblioteca.comics.persistence.repository;

import com.albo.biblioteca.comics.persistence.model.Creator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Hildamar Parra
 */
public interface CreatorRepository extends JpaRepository<Creator, Long> {
   List<Creator> findByRole(String role);
}

