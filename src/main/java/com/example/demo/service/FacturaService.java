package com.example.demo.service;

import com.example.demo.entity.Factura;
import com.example.demo.repository.FacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FacturaService {

    @Autowired
    FacturaRepository facturaRepository;

    public List<Factura> obtenerTodos(){
        List<Factura> lista = facturaRepository.findAll();
        return lista;
    }

    public Optional<Factura> optenerPorId(Long id){
        return facturaRepository.findById(id);
    }

    public void guardar(Factura factura){
        facturaRepository.save(factura);
    }

    public void borrar(Long id){
        facturaRepository.deleteById(id);
    }

    public boolean existePorId(Long id){
        return facturaRepository.existsById(id);
    }
}
