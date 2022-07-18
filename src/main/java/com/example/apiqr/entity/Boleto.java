package com.example.apiqr.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "boleto")
public class Boleto implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idboleto", nullable = false)
    private Integer id;

    @Column(name = "estado", nullable = false)
    private Integer estado;

    @Column(name = "codigoaleatorio")
    private String codigoaleatorio;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public String getCodigoaleatorio() {
        return codigoaleatorio;
    }

    public void setCodigoaleatorio(String codigoaleatorio) {
        this.codigoaleatorio = codigoaleatorio;
    }

}