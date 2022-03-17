package com.exercise.sa2files.application;

import com.exercise.sa2files.domain.Fichero;

import java.util.List;

public interface IFichero {
    Fichero add(Fichero f);
    List<Fichero> findAll(String path);
    Fichero findById(Integer id);
}
