#  ---------------------------------------
#  -------  Create DB and Inserts  --------
#  ---------------------------------------


drop database if exists utn_phones
create database utn_phones

use utn_phones

-- drop table if exists provinces
create table provinces(
	province_id int not null auto_increment,
	province_name varchar(50) not null,
	constraint pk_province_id primary key (province_id)
)

-- drop table if exists cities
create table cities(
	city_id int not null auto_increment,
	city_name varchar(50) not null,
	city_prefix varchar(5) not null,
	city_province_id int not null,
	constraint pk_city_id primary key (city_id),
	constraint unq_city_prefix unique (city_prefix),
	constraint fk_city_province_id foreign key (city_province_id) references provinces(province_id)
)

-- drop table if exists line_types
create table line_types(
	line_type_id int not null auto_increment,
	line_type varchar(50) not null,
	constraint pk_line_type_id primary key (line_type_id)
)

-- drop table if exists rates
create table rates(
	city_from_id int not null,
	city_to_id int not null,
	rate_value float(4,2),
	rate_cost float(4,2),
	constraint pk_city_from_id primary key (city_from_id,city_to_id),
	constraint fk_city_from_id foreign key (city_from_id) references cities(city_id),
	constraint fk_city_to_id foreign key (city_to_id) references cities(city_id)
)

-- drop table if exists user_types
create table user_types(
	type_id int not null auto_increment,
	type_name varchar(15) not null,
	constraint pk_type_id primary key(type_id)
)

-- drop table if exists users
create table users(
	user_id int not null auto_increment,
	user_name varchar(50) not null,
	user_lastname varchar(50) not null,
	user_dni int(8) not null,
	username varchar(50) not null,
	userpass varchar(50) not null,
	user_city_id int not null,
	user_type_id int not null,
	user_active boolean not null,
	constraint pk_user_id primary key (user_id),
	constraint unq_user_dni unique (user_dni),
	constraint unq_username unique (username),
	constraint fk_user_city_id foreign key (user_city_id) references cities(city_id),
	constraint fk_user_type_id foreign key (user_type_id) references user_types(type_id)
)


-- drop table if exists phone_lines
create table phone_lines(
	line_id int not null auto_increment,
	line_number varchar(15) not null,
	line_status ENUM('Active','Inactive','Suspended') not null,
	line_type_id int not null,
	line_user_id int not null,
	constraint pk_line_id primary key (line_id),
	constraint unq_line_number unique (line_number),
	constraint fk_line_type_id foreign key (line_type_id) references line_types(line_type_id),
	constraint fk_line_user_id foreign key (line_user_id) references users(user_id)
)

-- drop table if exists invoices
create table invoices(
	invoice_id int not null auto_increment,
	invoice_line_id int not null,
	invoice_quantity_calls int not null,
	invoice_cost_price float(4,2) not null,
	invoice_total_price float(6,2) not null,
	invoice_date datetime not null,
	invoice_due_date datetime not null,
	constraint pk_invoice_id primary key (invoice_id),
	constraint fk_invoice_line_id foreign key (invoice_line_id) references phone_lines(line_id)
)

-- drop table if exists calls
create table calls(
	call_id int not null auto_increment,
	call_line_id_from int not null,
	call_line_id_to int not null,
	call_minute_price float(4,2) not null,
	call_minute_cost float(4,2) not null,
	call_duration_seg int not null,
	call_date datetime not null,
	call_invoice_id int,
	constraint pk_call_id primary key (call_id),
	constraint fk_call_line_id_from foreign key (call_line_id_from) references phone_lines (line_id),
	constraint fk_call_line_id_to foreign key (call_line_id_to) references phone_lines (line_id),
	constraint fk_call_invoice_id foreign key (call_invoice_id) references invoices(invoice_id)
)

#  ---------------------------------------
#  ------------  Inserts data  -----------
#  ---------------------------------------

# ---- provinces ----
insert into provinces (province_name) values 
('Buenos Aires'),
('Cordoba'),
('Entre Rios'),
('Jujuy'),
('Mendoza'),
('Misiones'),
('Neuquen'),
('Salta'),
('San Luis'),
('Santa Fe'),
('Tucuman');


# ---- cities ----
insert into cities (city_name, city_prefix, city_province_id) values
('Buenos Aires','11',1),
('Mar del Plata','223',1),
('Necochea','2262',1),
('La Plata','221',1),
('Cordoba','351',2),
('Parana','343',3),
('San Salvador de Jujuy','388',4),
('Mendoza','261',5),
('Posadas','3752',6),
('Neuquen','299',7),
('Salta','387',8),
('San Luis','2652',9),
('Santa Fe','342',10),
('Rosario','341',10),
('San Miguel de Tucuman','381',11);


# ---- user_types ----
insert into user_types (type_name) values 
('Client'),
('Employee');


# users
insert into users (user_name,user_lastname,user_dni,username,userpass,user_city_id,user_type_id, user_active) values
('Nicolas','Pettinato',33189203,'nico','Kjju3aBhB24=',2,1,1),
('Angel','Lattanzio',38123456,'angel','Kjju3aBhB24=',3,1,1),
('Axel','Rosas',20123456,'axel','Kjju3aBhB24=',2,1,1), 
('Rogelio','Aguas',17902387,'rogelio','Kjju3aBhB24=',6,1,1), 
('Miguel','Jagger',14936759,'miguel','Kjju3aBhB24=',10,1,1), 
('Juancito','Dinero',10226812,'juancito','Kjju3aBhB24=',4,1,1),
('Empleado1','Empleado1',13245008,'emple1','Kjju3aBhB24=',1,2,1),
('Empleado2','Empleado2',15125689,'emple2','Kjju3aBhB24=',1,2,1);

-- "Kjju3aBhB24=" - "1234"

# line_types
insert into line_types (line_type) values 
('Mobile'),
('Residential');


# rates
insert into rates (city_from_id, city_to_id, rate_value, rate_cost) values
(1,2,6.25,1.25),
(1,3,5.75,1.15),
(1,6,8.3,3.75),
(2,1,4.15,1.5),
(2,3,2.5, 0.5),
(3,2,4,1.5),
(5,1,7.40,3.15);


# phone_lines
insert into phone_lines (line_number, line_status, line_type_id, line_user_id) values
('2234548725','Active',1,1),
('2234744250','Active',2,1),
('2262506826','Active',1,2),
('115234567','Active',2,2),
('37521234567','Active',1,2),
('3431234567','Active',1,3),
('2991234567','Active',1,4),
('2211234567','Active',1,5);