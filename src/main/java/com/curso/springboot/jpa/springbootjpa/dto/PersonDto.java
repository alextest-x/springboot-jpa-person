package com.curso.springboot.jpa.springbootjpa.dto;


public class PersonDto {

    
    private String name;
    private String lastname;


    //no es necesario crear unconstructor vacio porque
    //no es una clase entity no lo maneja jpa
    //nosotros creamos la instancia

    public PersonDto(String name, String lastname) {
        this.name = name;
        this.lastname = lastname;
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


    //ponemos el toString para que imprima los datos sino imprime los objetos
    @Override
    public String toString() {
        return "PersonDto{" +
                "name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                '}';
    }
}
