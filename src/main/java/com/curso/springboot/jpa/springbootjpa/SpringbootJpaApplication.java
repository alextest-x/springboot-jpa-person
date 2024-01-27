package com.curso.springboot.jpa.springbootjpa;

import ch.qos.logback.core.encoder.JsonEscapeUtil;
import com.curso.springboot.jpa.springbootjpa.dto.PersonDto;
import com.curso.springboot.jpa.springbootjpa.entities.Person;
import com.curso.springboot.jpa.springbootjpa.repositories.PersonRepository;
import org.apache.logging.log4j.spi.ObjectThreadContextMap;
import org.hibernate.id.IntegralDataTypeHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;


import javax.swing.text.html.Option;
import java.util.*;


//CommandLineRunner
// se implementa una interface porque vamos a usar la consola


@SpringBootApplication
public class SpringbootJpaApplication implements CommandLineRunner {

    @Autowired
    private PersonRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(SpringbootJpaApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {

        //list();
        //findOne();
        //create();
        //update();
        //delete();
        //delete2();

        //personalizedQueries();
        //personalizedQueries2();
        //personalizedQueriesPorNombre();
        //personalizedQueriesNoDuplicados();
        //personalizedQueriesNoDuplicados2();
        //personalizedQueriesNoDuplicadosCount();
        //personalizedQueriesCount();

        //personalizedQueriesConcatUpperAndLowerCase();

        //personalizedQueriesBetween();

        //queriesFuntionAgregation();

        //consultas que estan anidada dentro de una consulta
        //  subConsultas();

        //create();
          update();
    }

    @Transactional(readOnly = true)
    public void subConsultas(){

        System.out.println("=== Consulta por nombre mas corto y su largo subQuery ===================");
        List<Object[]> registers= repository.getShortName();
        registers.forEach(reg-> {
            //System.out.println(reg);  //aqui me da el objeto
            //obteniendo la posicion [0];  hay que hacer un cast

            String name = (String) reg[0];
            Integer length = (Integer) reg[1];
            System.out.println("name = " + name + ", length = " + length);

        });

        System.out.println("=== Consulta para obtener el ultimo registro de tabla subQuery ===================");
        Optional<Person> optionalPerson = repository.getLastRegistration();
        optionalPerson.ifPresent(persona -> {
            System.out.println(persona);
        });

/*
        System.out.println("=== Consulta para obtener los ids 1, 2, 3 subQuery ===================");
        List<Person> listIds = repository.getPersonByIds();
        listIds.forEach (listaIds -> {
            System.out.println(listaIds);
        });
*/
        //de otra forma
        System.out.println("=== Consulta para obtener los ids 1, 2, 3 subQuery ===================");
        //ponemos una lista una instancia
        List<Person> listIds = repository.getPersonByIds(Arrays.asList(1L, 2L, 3L));
        listIds.forEach (listaIds -> {
            System.out.println(listaIds);
        });

        System.out.println("=== Consulta para obtener los ids que no contengan 1, 2, 3 con not in <> subQuery ===================");
        //ponemos una lista una instancia
        List<Person> list = repository.getPersonNotById(Arrays.asList(1L, 2L, 3L));
        list.forEach (listaIds -> {
            System.out.println(listaIds);
        });




    }

    @Transactional(readOnly = true)
    public void queriesFuntionAgregation(){

        System.out.println("======== Consulta a la tabla con ordey by name toString() ===================");
        String pe = repository.getAll().toString();
        System.out.println(pe);

        System.out.println("============= Consulta a la tabla con order by name ==========================");
        List<Person> persons = repository.getAll();
        persons.forEach( persona -> {
            System.out.println(persona);
        });


        System.out.println("============= Consulta a la tabla con order by por metodo  ===================");
        List<Person> p1 = repository.findAllByOrderByNameAscLastnameDesc();
        p1.forEach( per -> {
            System.out.println(per);
        });


        System.out.println("============= Consulta con el nombre mas corto  ===================");
        Integer minLengthName = repository.getMinLengthName();
        System.out.println(minLengthName);

        System.out.println("============= Consulta con el nombre mas Largo  =====================");
        Integer maxLengthName = repository.getmaxLengthName();
        System.out.println(maxLengthName);


        System.out.println("============= Consulta total de registros de la tabla ================");
        Long count = repository.totalPerson();
        System.out.println(count);

        System.out.println("============= Consulta con el valor minimo del id ====================");
        Long min = repository.minId();
        System.out.println(min);

        System.out.println("============= Consulta con el valor maximo del id ====================");
        Long max = repository.maxId();
        System.out.println(max);

        //lista de objetos
        System.out.println("============= Consulta la longitud del campo nombre ====================");
        List<Object[]>  longitudCampo = repository.getPersonNamelength();
        longitudCampo.forEach( reg -> {
            //hacemos el cast a String porque es de tipo object
            String  name = (String) reg[0];
            Integer length = (Integer) reg[1];

            System.out.println("name= "+ name + ", longitud= "+ length);
        });

        System.out.println("=== Consultas resumen de funciones de agregaciones min, max, sum, avg, count ===");
         Object[] resumenReg = (Object[]) repository.getResumeAggregationFunction();

        System.out.println("min = " + resumenReg[0] +
                ", max = " + resumenReg[1] +
                ", sum = " + resumenReg[2] +
                ", avg = " + resumenReg[3] +
                ", count = " + resumenReg[4]
                );

    }



    @Transactional(readOnly = true)
    public void personalizedQueriesBetween(){
        System.out.println("==============  consulta por rangos ============");
               List<Person> persons = repository.findAllBetweenId();
        persons.forEach( persona -> {
            System.out.println(persona);
        });


        System.out.println("==============  consulta por rangos 2 ============");
        List<Person> persons1 = repository.findAllBetweenIdName();
        persons1.forEach( persona -> {
            System.out.println(persona);
        });


        System.out.println("==============  consulta por rangos pasando parametros(2,6)  ==========");
        List<Person> persons2 = repository.findAllBetweenId(2,6);
        persons2.forEach( persona -> {
            System.out.println(persona);
        });


        System.out.println("==============  consulta por rangos pasando parametros (P,X) ============");
        List<Person> persona3 = repository.findAllBetweenIdName("P", "X");
        persona3.forEach( persona -> {
            System.out.println(persona);
        });


        System.out.println("==============  consulta por rangos pasando parametros(2,3) por metodo ==========");
        List<Person> per1 = repository.findByIdBetween(2L,3L);
        per1.forEach( persona -> {
            System.out.println(persona);
        });


        System.out.println("==============  consulta por rangos pasando parametros (A,P) por metodo =========");
        List<Person> per2 = repository.findByNameBetween("A", "Q");
        per2.forEach( persona -> {
            System.out.println(persona);
        });


        System.out.println("==============  consulta ========================");
        List<Person> persons4 = repository.getAllOrder();
        persons4.forEach(System.out::println);



        System.out.println("==============  consulta por el metodo================");
        List<Person> persona = repository.findAllByOrderByNameDesc();
        persona.forEach(System.out::println);

    }

    @Transactional(readOnly = true)
    public void personalizedQueriesPorNombre() {
        System.out.println("================== consulta con los nombres de personas asc ============");
        List<String> names = repository.findAllNames();
        names.forEach(nombre -> {
            System.out.println(nombre);
        });
    }

        @Transactional(readOnly = true)
        public void personalizedQueriesNoDuplicados() {
        System.out.println("=== consulta con los nombres de personas no duplicados ===========");
        List<String> namesDistinct= repository.personalizedQueriesNoDuplicados();
        namesDistinct.forEach( nombres -> {
                System.out.println(nombres);
        });
    }

    @Transactional(readOnly = true)
    public void personalizedQueriesNoDuplicados2() {
        System.out.println("=== consulta con los nombres de personas no duplicados con lenguaje de programacion ===");
        List<String> namesDistinct= repository.personalizedQueriesNoDuplicados2();
        namesDistinct.forEach( nombres -> {
            System.out.println(nombres);
        });
    }


    @Transactional(readOnly = true)
    public void personalizedQueriesNoDuplicadosCount() {
        System.out.println("=== consulta  el total de los lenguajes de programacion no duplicados ===");
        Long total= repository.peronalizedQueriesDistinctCount();
        System.out.println("Total : " + total);

    }

    @Transactional(readOnly = true)
    public void personalizedQueriesCount() {
        System.out.println("=== consulta el total de los lenguajes de programacion  ===");
        Long total= repository.personalizedQueriesCount();
        System.out.println("Total : " + total);

    }
    @Transactional(readOnly = true)
    public void personalizedQueriesConcatUpperAndLowerCase(){
        System.out.println("=== Consulta de nombres y apellido de personas");
        List<String> names = repository.findAllFullNameConcat();
        names.forEach(System.out::println);

        System.out.println("=== Consulta de nombres y apellido mayusculas");
        names= repository.findAllFullNameConcatUpper();
        names.forEach(System.out::println);


        System.out.println("=== Consulta de nombres y apellido minusculas");
        names= repository.findAllFullNameConcatLower();
        names.forEach(System.out::println);


        System.out.println("=== Consulta de nombres y apellido de mayusculas y minusculas");
        List<Object[]> regUpperLower = repository.findAllPersonDataUpperAndLower();
        regUpperLower.forEach(reg -> System.out.println("id= " + reg[0] + ", nombre = " + reg[1] +
                ", apellido= " + reg[2] + ", lenguage=" +reg[3]));

    }




   @Transactional(readOnly = true)
    public void personalizedQueries2() {

        System.out.println("=====Consulta por objeto persona y lenguaje de programacion  ==========");
        List<Object[]> personsRegs = repository.findAllMixPerson();
        personsRegs.forEach(registro -> {
            System.out.println("programmingLanguage: " + registro[1] + ", person: " +registro[0]);
        });

        System.out.println("=======================================================================");

        System.out.println("=== Consulta por name y lastname que regresa un objeto entity de una instancia personalizada ====");
         List<Person> persons = repository.findAllObjectPersonPersonalizada();
         //mostramos el objeto por cada uno
         persons.forEach(persona -> {
             System.out.println(persona);
         });
          //optimizado
         //persons.forEach(System.out::println);
//

       System.out.println("====  Consulta por name y lastname que regresa un objeto DTO de una clase personalizada ====================");
       List<PersonDto> personDto = repository.findAllPersonDto();
       personDto.forEach( personaDto -> System.out.println(personaDto) );

    }


    @Transactional(readOnly = true)
    public void personalizedQueries(){

        Scanner scanner = new Scanner(System.in);
        System.out.println("========== Consulta personalizada por nombre y busca por Id ==========");
        System.out.println("Ingrese el id para el nombre: ");
        Long id = scanner.nextLong();
        System.out.println("=======================================================================");


        System.out.println("============  Mostrando solo el Id  ===================================");
        String name = repository.getNameById(id);
        System.out.println("name: " + name);
        System.out.println("=======================================================================");


        System.out.println("============  Mostrando solo el nombre completo con Concat() ==========");
        String fullName = repository.getFullNameById(id);
        System.out.println("lastname: " + fullName);
        System.out.println("=======================================================================");


        System.out.println("============Consulta por campos personalizados por el Id  ==============");
        System.out.println("Consulta personalizada por el id");
        //hacemos un cast para convertir a un arreglo de objetos porque solo trae uno
        Object[] personReg = (Object[]) repository.obtenerUnCampoById(id);
        System.out.println(personReg);
        System.out.println("id: " + personReg[0] + " " +
                ", name: " + personReg[1] + ", lastname: " + personReg[2] + ", Lenguaje de Progamacion : " +
                personReg[3]);
        System.out.println("=======================================================================");

        //lista de objetos
        System.out.println("========= consulta por campos personalizados lista =====================");
        List<Object[]> regs = repository.obtenerPersonDataList();
        regs.forEach(reg -> {
            System.out.println("id: " + reg[0] + " " +
                    ", name: " + reg[1] + ", lastname: " + reg[2] + ", Lenguaje de Progamacion : " +
                    reg[3]);
        });


        //lista de objetos con un optional
        //hacemos un cast a object

        System.out.println("========= consulta por campos personalizados lista con optional =======");
        Optional<Object> optionalReg = repository.obtenerPersonDataById(id);
        if(optionalReg.isPresent()){
            Object[] reg = (Object[]) optionalReg.orElseThrow();
            System.out.println("id: " + reg[0] + " " +
                    ", name: " + reg[1] + ", lastname: " + reg[2] + ", Lenguaje de Progamacion : " +
                    reg[3]);
            System.out.println("=======================================================================");


        }
    }



    @Transactional(readOnly = true)
    public void list() {
                /*
           un list de objetos Person con findAll() regresa un iterable entonces  hay que convertir
           el iterable a un list hay que hacer un cast

       // List<Person> persons = (List<Person>) repository.findAll();
       // List<Person> persons = (List<Person>) repository.findByProgrammingLanguage("Java");

       // persons.stream().forEach(person -> {
       //     System.out.println(person);
       // });

       //se itera cada persona de personas de la lista la obtenemos y la mandamos imprimir
       //persons.stream().forEach(person -> System.out.println(person));
       */

        // List<Person> persons = (List<Person>) repository.buscarByProgrammingLanguage("Java", "Andres");
        // List<Person> persons = (List<Person>) repository.buscarByProgrammingLanguage("Python", "Pepe");
        List<Person> persons = (List<Person>) repository.findByProgrammingLanguageAndName("Python", "Pepe");
        persons.stream().forEach(person -> {
            System.out.println(person);
        });


        //obtiene una lista
        List<Object[]> personsValues = repository.obtenerPersonData();
        personsValues.stream().forEach(person -> {
            // System.out.println(person);
            System.out.println("Registros : " + person[0] + "  " + "estudia:  " + person[1]);
        });


        //obtiene por lenguaje de programacion y nombre le pasa parametros
        List<Object[]> personsValues1 = repository.obtenerPersonData1("Java", "Andres");
        personsValues1.stream().forEach(person -> {

            System.out.println("Registro por Lenguaje de Programacion: " +person[0] + "  " + "estudia:  " + person[1]);
        });


        //obtiene por nombre
        List<Object[]> personsValues2 = repository.obtenerPersonData1("Andres");
        personsValues2.stream().forEach(person -> {
            System.out.println("nombre:  " + person[0]);
        });


    }
/*   //obtiene por usuario por id 1
        @Transactional(readOnly = true)
        public void findOne(){
        Person person = repository.findById(3L).orElseThrow();
        System.out.println(person);
    }
*/

/*
    //obtiene por usuario por id 1
    //con un optional regresa un optional de persona
    //1 se busca el id se obtiene el optional
    // valida si esta presente y se lo pasa al objeto persona
    // y lo imprimimos sino hay id imprime nulo
    @Transactional(readOnly = true)
    public void findOne() {
        Person person = null;
        Optional<Person> optionalPerson = repository.findById(1L);

        //if (!optionalPerson.isEmpty()){
        if (optionalPerson.isPresent()) {
            person = optionalPerson.get();
            System.out.println("Metodo con un optional:  " + person);
        } else {
            System.out.println("No encuentra el id:  " + person);

        }

    }
*/

    //de otra forma

    /*
    @Transactional(readOnly = true)
    public void findOne(){

        //si esta presente pasa una expresion lambda (person -> ) se imprime el callback
        //repository.findById(1L).ifPresent(person -> System.out.println(person));

        //de otra forma
        //se pone el metodo de referencia ::
        //se pasa un argumento en el callbak y se recibe se implementa el metodo de referencia
        repository.findById(1L).ifPresent(System.out::println);
     }

  */

   /*
        @Transactional(readOnly = true)
        public void findOne(){
        repository.findOneName("Pepe").ifPresent(System.out::println);
    }
    */

    @Transactional(readOnly = true)
    public void findOne() {
        // obtiene por el el prefijo Nombre
        // repository.findOneLikeName("Pe").ifPresent(System.out::println);

        //obtiene por el nombre completo
        // repository.findByName("Pe").ifPresent(System.out::println);

        //obtiene por el el prefijo Nombre sin el like de la consulta de @Query
        //findByNameContaining personalizado hay que ponerlo el PersonRepository
        //el nombre del metodo

          repository.findByNameContaining("Pe").ifPresent(System.out::println);
    }


    //la clase create() deben estar en las clases de service y ponemos
    // @Transactional de spring Framework anotations
    //en donde son save o delete lo ponemos
    //y en las consultas ponemos @Transactional(readOnly = true)
    //@Transactional envuelve al metodo con un bean transaction
    // envuelve al metodo con una transation con un bean transaction antes de ejecutar el metodo
    //y despues hace un commit y finaliza y si hay error hace rollback.

    @Transactional
    public void create(){

        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el nombre: ");
        String name = scanner.next();
        System.out.println("Ingrese el apellido: ");
        String lastname= scanner.next();
        System.out.println("Ingrese el lenguaje de programacion: ");
        String programmingLanguage = scanner.next();
        scanner.close();
        Person person = new Person(null, name, lastname, programmingLanguage);

        //el id es null porque es incremental enla base de datos
        //Person person = new Person(null, "Javier", "Perez", "Python");
        //si el id es nulo lo inserta si existe lo actualiza
        //repository.save(person);

        //el save regresa un entity con el id

        //fue creada en la base de datos
        Person personNew = repository.save(person);
        System.out.println(personNew);

        //asegurar que se guarda en Bd
        //lo buscamos con el id

        //repository.findById(personNew.getId()).ifPresent(p -> System.out.println(p));

        //lo optimizamos con el metodo de referencia ::
        repository.findById(personNew.getId()).ifPresent(System.out::println);

    }

    /*
     //update con optional
     @Transactional
     public void update(){
         Scanner scanner = new Scanner(System.in);
         System.out.println("Ingrese el id de la persona: ");
         Long id = scanner.nextLong();

         Optional <Person> optionalPerson = repository.findById(id);

         optionalPerson.ifPresent(person -> {
             System.out.println("datos actuales antes de actualizar :  " + person);
             System.out.println("ingrese el lenguaje de programacion");
             String programmingLanguage = scanner.next();
             person.setProgrammingLanguage(programmingLanguage);
             //pasamos el objeto person y el save regresa el objeto actualizado de la BD
             Person personDb = repository.save(person);
             System.out.println("datos actualizados: " + personDb);

         });

         scanner.close();


     }
     */

    //update con if
    @Transactional
    public void update(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el id de la persona: ");
        Long id = scanner.nextLong();

        Optional <Person> optionalPerson = repository.findById(id);
         Person person = optionalPerson.orElseThrow();

        if(optionalPerson.isPresent()){
           Person personaDB = optionalPerson.orElseThrow();

            System.out.println("datos actuales antes de actualizar :  " + personaDB);
            System.out.println("ingrese el lenguaje de programacion: ");

            String programmingLanguage = scanner.next();
            personaDB.setProgrammingLanguage(programmingLanguage);
            //pasamos el objeto personaDB y el save regresa el objeto actualizado de la BD de personUpdate
            Person personUpdate = repository.save(personaDB);
            System.out.println("datos actualizados: " + personUpdate);
        } else {
            System.out.println("El usuario con el id : " + id  + "no esta registrado: " );
        }

        scanner.close();
    }

    @Transactional
    public void delete(){
        System.out.println(" antes de eliminar ");
        repository.findAll().forEach(System.out::println);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el id de la persona a eliminar : ");
        Long id = scanner.nextLong();
        //deleteById busca por id y lo elimina
        repository.deleteById(id);
        System.out.println(" Id eliminado: " + id);
        repository.findAll().forEach(System.out::println);
        scanner.close();

    }

    //delete con expresion (->) lambda
    @Transactional
    public void delete2(){
        System.out.println(" antes de eliminar ");
        repository.findAll().forEach(System.out::println);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el id de la persona a eliminar : ");
        Long id = scanner.nextLong();

        Optional<Person> optionalPerson = repository.findById(id);
       //optionalPerson.ifPresent(persona -> repository.delete(persona));

        //valida si existe el id sino muestra el mensaje
        optionalPerson.ifPresentOrElse(
                persona -> repository.delete(persona),
                ()-> System.out.println("No existe el Id:  " +id));


        repository.findAll().forEach(System.out::println);
        scanner.close();

    }


}

