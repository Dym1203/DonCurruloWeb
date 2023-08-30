package com.idat.currulo.web.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
//import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
//import javax.persistence.Table;
import javax.validation.constraints.Size;

//@Entity
//@Table(name = "medidas")
public class Medida implements Serializable {
	
	public static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idMedida;

    @Size(max = 20)
    @Column(nullable = false, unique = true)
    private String nomMedida;

    @Column(nullable = false, unique = false)
    private Boolean estado;

    @OneToMany(mappedBy = "medida")
    private Collection<Plato> itemsPlatos = new ArrayList<>();


    public Medida(Integer idMedida, String nomMedida, Boolean estado) {
        this.idMedida = idMedida;
        this.nomMedida = nomMedida;
        this.estado = estado;
    }

    public Medida(String nomMedida) {
        this.nomMedida = nomMedida;
    }

    public Medida() {
    	
    }
	
    
    @PrePersist
    public void estadoMedida() {
        estado = true;
    }

    public Integer getIdMedida() {
        return idMedida;
    }

    public void setIdMedida(Integer idMedida) {
        this.idMedida = idMedida;
    }

    public String getNomMedida() {
        return nomMedida;
    }

    public void setNomMedida(String nomMedida) {
        this.nomMedida = nomMedida;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Collection<Plato> getItemsPlatos() {
        return itemsPlatos;
    }

    public void setItemsPlatos(Collection<Plato> itemsPlatos) {
        this.itemsPlatos = itemsPlatos;
    }
    
}