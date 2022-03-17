package com.exercise.sa2files.infrastructure;

import com.exercise.sa2files.application.IFichero;
import com.exercise.sa2files.application.IFileStorage;
import com.exercise.sa2files.domain.Fichero;
import com.exercise.sa2files.shared.UnprocesableException;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@RestController
public class FicheroController {

    @Autowired
    IFichero ficheroService;

    @Autowired
    IFileStorage fileStorage;

    @PostMapping("upload/{tipo}")
    public ResponseEntity<Fichero> add(@PathVariable String tipo, @RequestParam("file") MultipartFile file)
    {
        // Comprobamos que tipo no está vacío.
        if (Objects.equals(tipo, "")) throw new UnprocesableException("tipo está vacío");
        // Comprobamos que file es del tipo especificado por tipo.
        String filename = file.getOriginalFilename();
        assert filename != null;
        String ext = (filename.contains(".")) ? filename.substring(filename.lastIndexOf(".") + 1) : "";
        if (!ext.equals(tipo)) throw new UnprocesableException("El fichero es de tipo incorrecto");
        // Cargamos el fichero.
        fileStorage.save(file);
        // Guardamos sus datos en la base de datos.
        Fichero f = new Fichero(null,file.getOriginalFilename(),tipo,new Date());
        ficheroService.add(f);
        return new ResponseEntity<>(f, HttpStatus.OK);
    }

    @GetMapping("getpath")
    public ResponseEntity<List<Fichero>> findAll(@RequestParam("path") String path)
    {
        List<Fichero> lista = ficheroService.findAll(path);
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("download/{id}")
    public ResponseEntity<Resource> findById(@PathVariable Integer id)
    {
        Fichero f = ficheroService.findById(id);
        Resource res = fileStorage.load(f.getNombre());
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("download")
    public ResponseEntity<Resource> findByNombre(@RequestParam("nombre") String nombre)
    {
        Resource res = fileStorage.load(nombre);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
