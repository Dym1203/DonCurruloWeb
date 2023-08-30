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
//@Table(name = "marcas")
public class Marca implements Serializable {
	
	public static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idMarca;

    @Size(max = 20)
    @Column(nullable = false, unique = true)
    private String nomMarca;

    @Column(nullable = false, unique = false)
    private Boolean estado;

    @OneToMany(mappedBy = "marca")
    private Collection<Plato> itemsPlatos = new ArrayList<>();

    
    public Marca(Integer idMarca, String nomMarca, Boolean estado) {
        this.idMarca = idMarca;
        this.nomMarca = nomMarca;
        this.estado = estado;
    }

    public Marca(String nomMarca) {
        this.nomMarca = nomMarca;
    }

    public Marca() {

    }
    
    
    @PrePersist
    public void estadoMarca() {
        estado = true;
    }

    public Integer getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(Integer idMarca) {
        this.idMarca = idMarca;
    }
    
    public String getNomMarca() {
        return nomMarca;
    }

    public void setNomMarca(String nomMarca) {
        this.nomMarca = nomMarca;
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