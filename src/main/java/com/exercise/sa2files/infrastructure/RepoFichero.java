package com.exercise.sa2files.infrastructure;

import com.exercise.sa2files.domain.Fichero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepoFichero extends JpaRepository<Fichero, Integer> {
    List<Fichero> findAllByNombre(String nombre);
}
