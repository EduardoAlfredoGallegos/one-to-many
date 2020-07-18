package com.example.demo.service;

import com.example.demo.entity.Persona;
import com.example.demo.repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PersonaService {

    @Autowired
    PersonaRepository personaRepository;

    public List<Persona> obtenerTodos(){
        List<Persona> lista = personaRepository.findAll();
        return lista;
    }

    public Optional<Persona> obtenerPorId(Long id){
        return personaRepository.findById(id);
    }

    public Optional<Persona> obtenerPorNombre(String n){
        return personaRepository.findByNombre(n);
    }

    public void guardar(Persona persona){
        personaRepository.save(persona);
    }

    public void borrar(Long id){
        personaRepository.deleteById(id);
    }

    public boolean existePorNombre(String n){
        return personaRepository.existsByNombre(n);
    }

    public boolean existePorId(Long id){
        return personaRepository.existsById(id);
    }

}
