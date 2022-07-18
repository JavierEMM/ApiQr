package com.example.apiqr.controller;

import com.example.apiqr.entity.Boleto;
import com.example.apiqr.repository.BoletoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
public class QrController {
    @Autowired
    BoletoRepository boletoRepository;

    @GetMapping("/boleto")
    public List<Boleto> listaTarjetas(){
        return  boletoRepository.findAll();
    }

    @GetMapping("/boleto/{codigoaleatorio}")
    public ResponseEntity<HashMap<String,Object>> obtenerBoleto(@PathVariable("codigoaleatorio") String codigo){
        HashMap<String,Object> responseJson = new HashMap<>();
        try {
            Optional<Boleto> optionalBoleto = boletoRepository.findByCodigoaleatorio(codigo);
            if(optionalBoleto.isPresent()){
                responseJson.put("result","success");
                responseJson.put("boleto",optionalBoleto.get());
                responseJson.put("validez",optionalBoleto.get().getEstado() == 0 ? "valido" : "invalido");
                return ResponseEntity.ok(responseJson);
            }else{
                responseJson.put("msg","Boleto no encontrado");
            }
        }catch (NumberFormatException e){
            responseJson.put("msg","El codigo es incorrecto");
        }
        responseJson.put("result","failure");
        return ResponseEntity.badRequest().body(responseJson);
    }
    @PutMapping(value = "/boleto/confirmar")
    public ResponseEntity<HashMap<String,Object>> actualizarBoleto(@RequestBody Boleto boleto){
        HashMap<String, Object> responseMap = new HashMap<>();
        System.out.println(boleto.getId());
        if (boleto.getId() != null && boleto.getId()>0){
            Optional<Boleto> opt = boletoRepository.findById(boleto.getId());
            if (opt.isPresent()){
                opt.get().setEstado(1);
                opt.get().setCodigoaleatorio(null);
                boletoRepository.save(opt.get());
                responseMap.put("estado","actualizado");
                responseMap.put("boleto",opt.get());
                return ResponseEntity.ok(responseMap);
            }else {
                responseMap.put("estado","error");
                responseMap.put("msg","El boleto a actualizar no existe");
                return ResponseEntity.badRequest().body(responseMap);
            }
        }else{
            responseMap.put("estado","error");
            responseMap.put("msg","Debe enviar un ID");
            return ResponseEntity.badRequest().body(responseMap);
        }

    }
    @PutMapping(value = "/boleto/confirmar/codigo")
    public ResponseEntity<HashMap<String,Object>> actualizarCodigo(@RequestBody Boleto boleto){
        HashMap<String, Object> responseMap = new HashMap<>();
        if (boleto.getCodigoaleatorio() != null && boleto.getCodigoaleatorio().length()>0){
            Optional<Boleto> opt = boletoRepository.findByCodigoaleatorio(boleto.getCodigoaleatorio());
            if (opt.isPresent()){
                opt.get().setEstado(1);
                opt.get().setCodigoaleatorio(null);
                boletoRepository.save(opt.get());
                responseMap.put("estado","actualizado");
                responseMap.put("boleto",opt.get());
                return ResponseEntity.ok(responseMap);
            }else {
                responseMap.put("estado","error");
                responseMap.put("msg","El boleto a actualizar no existe");
                return ResponseEntity.badRequest().body(responseMap);
            }
        }else{
            responseMap.put("estado","error");
            responseMap.put("msg","Debe enviar un ID");
            return ResponseEntity.badRequest().body(responseMap);
        }

    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<HashMap<String,String>> gestionExcepcion(HttpServletRequest request) {

        HashMap<String, String> responseMap = new HashMap<>();
        if (request.getMethod().equals("POST") || request.getMethod().equals("PUT")) {
            responseMap.put("estado", "error");
            responseMap.put("msg", "Formato incorrecto");
        }
        return ResponseEntity.badRequest().body(responseMap);
    }
}
