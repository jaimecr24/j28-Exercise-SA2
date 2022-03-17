package com.exercise.sa2files.application;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface IFileStorage {
    void init();
    void save(MultipartFile file);
    Resource load(String filename);
    void deleteAll();
    Stream<Path> loadAll(Path pt);
}
