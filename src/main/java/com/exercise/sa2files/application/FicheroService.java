package com.exercise.sa2files.application;

import com.exercise.sa2files.infrastructure.RepoFichero;
import com.exercise.sa2files.domain.Fichero;
import com.exercise.sa2files.shared.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FicheroService implements IFichero {

    @Autowired
    RepoFichero repoFichero;

    @Autowired IFileStorage storageService;

    @Override
    public Fichero add(Fichero f) {
        repoFichero.save(f);
        return f;
    }

    @Override
    public List<Fichero> findAll(String path) {
        Stream<Path> lista = storageService.loadAll(Paths.get(path));
        // Construir la lista de ficheros buscando por nombre cada uno.
        List<Fichero> listaFichero = lista
                .map(e -> repoFichero.findAllByNombre(e.getFileName().toString()))
                .map(e -> (e.size()>0) ? e.get(0) : null)
                .collect(Collectors.toList());
        return repoFichero.findAll();
    }

    @Override
    public Fichero findById(Integer id) {
        Optional<Fichero> optFichero = repoFichero.findById(id);
        return optFichero.orElseThrow(()-> new NotFoundException("No existe ningún fichero con id: "+id));
    }
}
