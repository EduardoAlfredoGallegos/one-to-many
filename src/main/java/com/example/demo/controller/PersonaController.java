package com.example.demo.controller;

import com.example.demo.DTO.Mensaje;
import com.example.demo.entity.Persona;
import com.example.demo.service.PersonaService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/personas")
public class PersonaController {

    @Autowired
    PersonaService personaService;

    @GetMapping("/lista")
    public ResponseEntity<List<Persona>> getLista(){
        List<Persona> lista = personaService.obtenerTodos();
        return new ResponseEntity<List<Persona>>(lista, HttpStatus.OK);
    }

    @GetMapping("/detalle/{id}")
    public ResponseEntity<Persona> getOne(@PathVariable Long id){
        if(!personaService.existePorId(id))
            return new ResponseEntity(new Mensaje("no existe esa persona"), HttpStatus.NOT_FOUND);
        Persona persona = personaService.obtenerPorId(id).get();
        return new ResponseEntity<Persona>(persona, HttpStatus.OK);
    }

    @PostMapping("/nuevo")
    public ResponseEntity<?> create(@RequestBody Persona persona){
        if(StringUtils.isBlank(persona.getNombre()))
            return new ResponseEntity(new Mensaje("el nombre es obligatorio"), HttpStatus.BAD_REQUEST);
        if(personaService.existePorNombre(persona.getNombre()))
            return new ResponseEntity(new Mensaje("ese nombre ya existe"), HttpStatus.BAD_REQUEST);
        personaService.guardar(persona);
        return new ResponseEntity(new Mensaje("persona guardado"), HttpStatus.CREATED);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> update(@RequestBody Persona persona, @PathVariable("id") Long id){
        if(!personaService.existePorId(id))
            return new ResponseEntity(new Mensaje("no existe esa persona"), HttpStatus.NOT_FOUND);
        if(StringUtils.isBlank(persona.getNombre()))
            return new ResponseEntity(new Mensaje("el nombre es obligatorio"), HttpStatus.BAD_REQUEST);
        if(personaService.existePorNombre(persona.getNombre()) &&
                personaService.obtenerPorNombre(persona.getNombre()).get().getId() != id)
            return new ResponseEntity(new Mensaje("ese nombre ya existe"), HttpStatus.BAD_REQUEST);
        Persona prodUpdate = personaService.obtenerPorId(id).get();
        prodUpdate.setNombre(persona.getNombre());
        prodUpdate.setEdad(persona.getEdad());
        personaService.guardar(prodUpdate);
        return new ResponseEntity(new Mensaje("perfil actualizado"), HttpStatus.CREATED);
    }

    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        if(!personaService.existePorId(id))
            return new ResponseEntity(new Mensaje("no existe esa persona"), HttpStatus.NOT_FOUND);
        personaService.borrar(id);
        return new ResponseEntity(new Mensaje("perfil eliminado"), HttpStatus.OK);
    }
}
