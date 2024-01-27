package com.curso.springboot.jpa.springbootjpa.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.LocalDateTime;

@Embeddable
public class Audit {

    /*

    ponemos lo que queremos rutilizar
    que vamos a utilizar en otras clases
    por ejemplo las fechas de la clase entity
    los setters y getters los eventos de que definimos
    del @PrePersist y el @PreUpdate


     */

    @Column(name="create_at")
    private LocalDateTime createAt;

    @Column(name="updated_at")
    private LocalDateTime updatedAt;


    //se ejecuta cuando se llama se crea un usuario nuevo
    @PrePersist
    public void prePersist(){
        System.out.println("evento del ciclo de vida del entity pre-persist");
        this.createAt = LocalDateTime.now();
    }

    //se ejecuta cuando actualiza el lenguage de programacion
    @PreUpdate
    public void preUpdate(){
        System.out.println("evento del ciclo de vida del objeto entity pre-update");
        this.updatedAt = LocalDateTime.now();
    }





    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }



}
