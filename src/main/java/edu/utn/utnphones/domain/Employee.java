package edu.utn.utnphones.domain;

import javax.persistence.*;

//Sin annotations porque las tiene la clase padre
//@Entity
//@Table(name = "employees")
public class Employee extends Person {


    private City city;
}
