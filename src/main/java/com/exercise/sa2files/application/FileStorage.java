package com.exercise.sa2files.application;

import com.exercise.sa2files.shared.NotFoundException;
import com.exercise.sa2files.shared.UnprocesableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Service
public class FileStorage implements IFileStorage {

    private Path root = Paths.get("uploads");

    @Autowired @Qualifier("directorio") private String directorio;

    @Override
    public void init() {
        this.root = Paths.get(this.directorio);
        try {
            Files.createDirectory(root);
        } catch (IOException e) {
            throw new UnprocesableException("Error al crear directorio: "+root.toString());
        }
    }

    @Override
    public void save(MultipartFile file) {
        try{
            Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
        } catch (Exception e) {
            throw new UnprocesableException("Error al copiar el fichero: " + e.getMessage());
        }
    }

    @Override
    public Resource load(String filename) {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource((file.toUri()));
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new NotFoundException("Fichero no encontrado: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(root.toFile());
    }

    @Override
    public Stream<Path> loadAll(Path pt) {
        if (!pt.equals(this.root)) throw new UnprocesableException("El directorio no existe: "+ pt);
        try {
            return Files.walk(this.root,1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
        } catch (IOException e) {
            throw new UnprocesableException("Error al descargar ficheros" + pt);
        }
    }
}
