package com.curso.springboot.jpa.springbootjpa.repositories;

import com.curso.springboot.jpa.springbootjpa.dto.PersonDto;
import com.curso.springboot.jpa.springbootjpa.entities.Person;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/*
<T, ID> son genericos
<Person, Long>
Person es nombre de la clase Entity
Long tipo d dato de la llave primaria
y ya se puede inyectar en SpringbootJpaApplication.java
CrudRepository: tiene un iterable, optional, un list
JpaRepository: tiene para ordenar y paginar
*/

public interface PersonRepository extends CrudRepository<Person, Long> {


 /*********************       subQuery    **************************/

 //@Query("select p from Person p where p.id in (1, 2, 3) ")
 //  public List<Person> getPersonByIds();

 //de otra forma
 @Query("select p from Person p where p.id in ?1 ")
 public List<Person> getPersonByIds(List<Long> ids);


 @Query("select p from Person p where p.id not in ?1 ")
 public List<Person> getPersonNotById(List<Long> ids);

 @Query("select p.name, length(p.name) from Person p where length(p.name) = (select min(length(p.name)) from Person p)")
 public List<Object[]> getShortName();

//obtener el ultimo registro

 @Query("select p from Person p where p.id=( select max(p.id) from Person p)")
 public Optional<Person> getLastRegistration();


 /*****************************************************************/

 //regresa un arreglo de objetos y hacemos un cast para que regrese un arreglo de valores de la consulta
 //min[0], max[1], etc
 @Query("select min(p.id), max(p.id), sum(p.id), avg(length(p.name)), count(p.id) from Person p")
 public Object getResumeAggregationFunction();




 /*****************************************************************/

  //consulta basado en el nombre del metodo
  List<Person> findAllByOrderByNameAscLastnameDesc();

 @Query("select p from Person p order by p.name asc")
 List<Person> getAll();

 /*****************************************************************/

  @Query("select min(length(p.name)) from Person p")
  public  Integer getMinLengthName();

 @Query("select max(length(p.name)) from Person p")
 public  Integer getmaxLengthName();


 @Query("select p.name, length(p.name) from Person p")
 public List<Object[]> getPersonNamelength();
 /*****************************************************************/

     //obtener el total de registros en la tabla
     @Query("select count(p) from Person p")
     Long totalPerson();


      //obtener el minimo de al tabla
      @Query("select min(p.id) from Person p")
      Long minId();


      //obtener el ultimo id en la tabla
      @Query("select max(p.id) from Person p")
      Long maxId();

 /*****************************************************************/


    //consulta con el nombre del metodo
   List<Person> findAllByOrderByNameDesc();


     @Query("select p from Person p order by p.name, p.lastname desc")
     List<Person> getAllOrder();


    //campos personalizados basados en el nombre del metodo
    List<Person> findByIdBetween (Long id1, Long id2);

    //campos personalizados basados en el nombre del metodo
    List<Person> findByNameBetween (String name1, String name2);


    //List<Person> findByNameBetweenOrderByNameDescLastnameDesc (String name1, String name2);
    //List<Person> findByIdBetweenOrderByNameAsc(Long id1, Long id2);

    @Query("select p from Person p where p.id between ?1 and ?2  order by p.name desc")
    List<Person> findAllBetweenId(Integer id1, Integer id2);

    @Query("select p from Person p where p.name between ?1 and ?2 order by p.name, p.lastname asc")
    List<Person> findAllBetweenIdName(String c1, String c2);




    @Query("select p from Person p where p.name between  'A' and 'P' ")
    List<Person> findAllBetweenIdName();


   @Query("select p from Person p where p.id between 2 and 3 ")
   List<Person> findAllBetweenId();



    @Query("select p.id, upper(p.name), lower(p.lastname), upper(p.programmingLanguage) from Person p")
    List<Object[]> findAllPersonDataUpperAndLower();


    @Query("select upper(p.name || ' ' || p.lastname) from Person p")
    List<String> findAllFullNameConcatUpper();

    @Query("select lower( concat( p.name, ' ',  p.lastname)) from Person p")
    List<String> findAllFullNameConcatLower();


    // lista completa con name and lastname conatenado
    //@Query("select CONCAT(p.name, ' ', p.lastname) as fullname from Person p")
    //de otra forma de concatenar con ||
    @Query("select p.name || ' ' || p.lastname from Person p")
    List<String> findAllFullNameConcat();


    //cuenta todos los lenguajes
    @Query("select count (p.programmingLanguage) from Person p")
    Long personalizedQueriesCount();

    //cuneta los lenguajes no repitidos
    @Query("select count (distinct (p.programmingLanguage)) from Person p")
    Long peronalizedQueriesDistinctCount();


    @Query("select distinct (p.programmingLanguage) from Person p")
    List<String> personalizedQueriesNoDuplicados2();

    //lista de nombre con distinct
    @Query("select distinct (p.name) from Person p")
    List<String> personalizedQueriesNoDuplicados();


    //lista de todos los nombre
    @Query("select p.name from Person p")
    List<String> findAllNames();



    //DTO data transfer Object hay que poner el package de la clase PersonDto
    @Query("select new com.curso.springboot.jpa.springbootjpa.dto.PersonDto(p.name, p.lastname) from Person p")
    List<PersonDto> findAllPersonDto();

     //obteniendo campos selecionados por name y lastname
     //se instancia la clase aqui y no JPA
     //hay que tener un constructor con name y lastname
     //en las clases entity no es necesario poner el package sola las encuentra
     @Query("select new Person(p.name, p.lastname) from Person p")
     List<Person> findAllObjectPersonPersonalizada();


     @Query("select p, p.programmingLanguage from Person p")
     List<Object[]> findAllMixPerson();


    //obtiene un campo con un optional con un id
    @Query("select p.id, p.name, p.lastname, p.programmingLanguage from Person p where p.id=?1")
    Optional<Object> obtenerPersonDataById(Long id);


   //obtiene todos los campos
    @Query("select p.id, p.name, p.lastname, p.programmingLanguage from Person p")
    List<Object[]> obtenerPersonDataList();


    //obtiene un solo objeto por id
    //entonces le quitamos los [] porque es uno solo
    @Query("select p.id, p.name, p.lastname, p.programmingLanguage from Person p where p.id=?1")
    Object obtenerUnCampoById(Long id);


    //name and lastname conatenado
    @Query("select concat(p.name, ' ', p.lastname) as fullname from Person p where p.id=?1")
    String getFullNameById(Long id);



    //consulta personalizada obteniendo el nombre
    //con el parametro id
    @Query("select p.name from Person p where p.id=?1")
    String getNameById(Long id);



    //hace un where y busca un lenguaje java
    List <Person> findByProgrammingLanguage(String programmingLanguage);

    /*
     *  se pone una consulta jpa a la tabla que mapea objetos a la tabla
     *  p regresa un objeto o varios Person es la clase entity y el alias p
     *  enlista todos los objetos entiry persona que estan mapeados en la tabla persona
     *  se pone ?1 porque es el parametro numero 1 p.name=?2 es el parametro 2
     */

    @Query("select p from Person p where p.programmingLanguage=?1 and p.name=?2")
    List <Person> buscarByProgrammingLanguage(String programmingLanguage, String name);



    //busca y find es lo mismo pero la diferencia es el @Query
    //aqui lo hace por defabo y con la anotacion @Query hay que poner la consulta
    List <Person> findByProgrammingLanguageAndName(String programmingLanguage, String name);


    //lista de valores de tipo usuario
    //pero de tipo arreglo porque va a contener el elemento 1 nombre elemento2 lenguaje de programacion
    //trae un objeto con campos seleecionado no todos
    @Query("select p.name, p.programmingLanguage from Person p")
    List<Object[]> obtenerPersonData();



    @Query("select p.name from Person p where p.name=?1")
    List<Object[]> obtenerPersonData1(String name );


    @Query("select p.name, p.programmingLanguage from Person p where p.programmingLanguage=?1")
    List<Object[]> obtenerPersonData1(String programmmingLanguage, String name );


    // se regresa un solo objeto de tipo person cuando se utiliza el CrudRepository con jpa
    // se recomienda usar el optional porque nos envuelve el resulatdo de la consulta si presente o no

    @Query("select p from Person p where p.id=?1")
    Optional<Person> findOne(Long id);

    //buscar por el nombre
    @Query("select p from Person p where p.name=?1")
    Optional<Person> findOneName(String name);

    //buscar por prefijo Pep con el operador like y %?1%
    //anotacion @Query
    @Query("select p from Person p where p.name like %?1%")
    Optional<Person> findOneLikeName(String name);





    //sin la anotacion Query y sin el like
    Optional<Person> findByNameContaining(String name);




}

