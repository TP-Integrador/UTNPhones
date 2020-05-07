package edu.utn.utnphones.domain;

import javax.persistence.Table;

//Sin annotations porque las tiene la clase padre
@Table(name = "employees")
public class Employee extends Person {

}
