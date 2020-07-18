package com.example.demo.controller;

import com.example.demo.DTO.Mensaje;
import com.example.demo.entity.Factura;
import com.example.demo.service.FacturaService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/facturas")
public class FacturaController {

    @Autowired
    FacturaService facturaService;

    @GetMapping("/lista")
    public ResponseEntity<List<Factura>> getLista(){
        List<Factura> lista = facturaService.obtenerTodos();
        return new ResponseEntity<List<Factura>>(lista, HttpStatus.OK);
    }

    @GetMapping("/detalle/{id}")
    public ResponseEntity<Factura> getOne(@PathVariable Long id){
        if(!facturaService.existePorId(id))
            return new ResponseEntity(new Mensaje("no existe esa factura"), HttpStatus.NOT_FOUND);
        Factura factura = facturaService.optenerPorId(id).get();
        return new ResponseEntity<Factura>(factura, HttpStatus.OK);
    }

    @PostMapping("/nuevo")
    public ResponseEntity<?> create(@RequestBody Factura factura){
        facturaService.guardar(factura);
        return new ResponseEntity(new Mensaje("factura guardada"), HttpStatus.CREATED);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> update(@RequestBody Factura factura, @PathVariable("id") Long id){
        Factura prodUpdate = facturaService.optenerPorId(id).get();
        prodUpdate.setFecha(factura.getFecha());
        prodUpdate.setConcepto(factura.getConcepto());
        prodUpdate.setImporte(factura.getImporte());
        prodUpdate.setPersona(factura.getPersona());
        facturaService.guardar(prodUpdate);
        return new ResponseEntity(new Mensaje("factura actualizada"), HttpStatus.CREATED);
    }

    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        if(!facturaService.existePorId(id))
            return new ResponseEntity(new Mensaje("no existe esa factura"), HttpStatus.NOT_FOUND);
        facturaService.borrar(id);
        return new ResponseEntity(new Mensaje("factura eliminada"), HttpStatus.OK);
    }
}
