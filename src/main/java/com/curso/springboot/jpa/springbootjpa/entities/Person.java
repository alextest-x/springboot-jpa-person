package com.curso.springboot.jpa.springbootjpa.entities;

import jakarta.persistence.*;

//import java.time.LocalDateTime;



@Entity
@Table(name="persons")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String lastname;

    @Column(name="programming_language")
    private String programmingLanguage;

    /*
    se agrega un atributo porque se creo la clase Audit.java
    creamos la instancia para que el valor no vaya nulo
    donde se pusieron las fechas para deacoplar y poder utilizarlo
    en otras clases y ponemos la variable audit en el string().to
    */
    @Embedded
    private Audit audit = new Audit();

    //simepre hay un cosntructor vacio
    public Person() {
    }

    //para hibernate jpa hay que tener un constructor con paramaretros y uno vacio
    public Person(Long id, String name, String lastname, String programmingLanguage) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.programmingLanguage = programmingLanguage;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getProgrammingLanguage() {
        return programmingLanguage;
    }




    public void setProgrammingLanguage(String programmingLanguage) {
        this.programmingLanguage = programmingLanguage;
    }


    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", programmingLanguage='" + programmingLanguage + '\'' +
                ", createAt=" + audit.getCreateAt() +
                ", updatedAt=" + audit.getUpdatedAt() +
                '}';
    }

    public Person(String name, String lastname) {
        this.name = name;
        this.lastname = lastname;
    }
}
