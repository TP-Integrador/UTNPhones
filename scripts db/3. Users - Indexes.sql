#  --------------------------------------
#  -------  Users and privileges --------
#  --------------------------------------


create user 'admin'@'localhost' identified by 'admin$2020';
grant all privileges on utn_phones.* to 'admin'@'localhost' identified by 'admin$2020' with grant option;


-- Backoffice
-- drop user 'backoffice'@'localhost';
create user 'backoffice'@'localhost' identified by 'back$2020';
grant select, insert, update on utn_phones.users to 'backoffice'@'localhost';
grant select, insert, update on utn_phones.phone_lines to 'backoffice'@'localhost';
grant select on utn_phones.rates to 'backoffice'@'localhost';
grant select on utn_phones.calls to 'backoffice'@'localhost';
grant select on utn_phones.invoices to 'backoffice'@'localhost';
-- revoke select on utn_phones.users from 'backoffice'@'localhost';

-- Client
-- drop user 'client'@'localhost';
create user 'client'@'localhost' identified by 'client$2020';
grant select on utn_phones.users to 'client'@'localhost';  
grant select on utn_phones.calls to 'client'@'localhost';
grant select on utn_phones.invoices to 'client'@'localhost';

-- Infra
-- drop user 'infra'@'localhost';
create user 'infra'@'localhost' identified by 'infra$2020';
grant insert on utn_phones.calls to 'infra'@'localhost';

-- Facturacion
-- drop user 'invoice'@'localhost';
create user 'invoice'@'localhost' identified by 'invoice$2020';
grant execute on procedure utn_phones.sp_call_billing to 'invoice'@'localhost';
GRANT EVENT ON utn_phones.* TO 'invoice'@'localhost';  -- privilege para event es global o por esquema, no se puede asisgar a una tabla



#  ---------------------------------------
#  -------  Index - Optimizations --------
#  ---------------------------------------

-- ------------------------------------ INDEXs ------------------------------------ --

-- ------------------------------------ USERS ------------------------------------ --

-- Indices por query utilizadas en el UserDao

-- getByUserName (utiliza username y password) - indice unq_username
explain SELECT * FROM users u WHERE u.username = "angel" and u.userpass = 'Kjju3aBhB24='

-- findByDni (utiliza user_dni) - indice unq_user_dni
explain SELECT * FROM users u WHERE u.user_dni = 33189203

-- findByIdAndUserType (utiliza user_type y user_id) - indice PRIMARY
explain SELECT * FROM users u WHERE u.user_type_id = 1 and u.user_id = 2

-- findByUserName (utiliza username) - indice unq_username
explain SELECT * FROM users u WHERE u.username = "nico"

-- ------------------------------------ RATE ------------------------------------ --

-- Indices por query utilizadas en el RateDao

-- getRate (utiliza city_from_id y city_to_id) - indice PRIMARY
explain SELECT * FROM rates r WHERE r.city_from_id = 1 and r.city_to_id = 2

-- getIdFrom (utiliza city_from_id) - indice PRIMARY
explain SELECT * FROM rates r WHERE r.city_from_id = 1

-- ------------------------------------ CITIES ------------------------------------ --

-- Indices por query utilizadas en el CityDao

-- getById (utiliza city_id) - indice PRIMARY
explain SELECT * FROM cities WHERE city_id = 1

-- ------------------------------------ PHONE_LINES ------------------------------------ --

-- Indices por query utilizadas en el PhoneLineDao

-- findByNumber (utiliza line_number) - indice unq_line_number
create index idx_phoneline_line_number on phone_lines(line_number) using HASH;
explain select * from phone_lines where line_number = "2234548725"

-- ------------------------------------ INVOICES ------------------------------------ --

-- Indices por query utilizadas en el InvoiceDao

-- getInvoiceByDate (utiliza invoice_date y user_id) - indice idx_invoice_date
create index idx_invoices_date on invoices(invoice_date) using BTREE;
	
	-- Ejemplo 1
explain select * from invoices where invoice_date between "2020-05-28" and "2020-06-30"
	
	-- Ejemplo 2 (utilizado en InvoiceDao)
explain select * from invoices i inner join phone_lines pl on i.invoice_line_id = pl.line_id 
inner join users u on pl.line_user_id = u.user_id
where i.invoice_date between "2020-05-28" and "2020-06-30" and u.user_id = 1
order by i.invoice_date

-- getInvoiceByClient (utiliza user_id) - indice PRIMARY
explain select * from invoices i inner join phone_lines pl on i.invoice_line_id = pl.line_id 
inner join users u on pl.line_user_id = u.user_id 
where u.user_id = 1
order by i.invoice_id 

-- ------------------------------------ CALL ------------------------------------ --

-- Indices por query utilizadas en el CallDao

-- getCallByDate (utiliza call_date y user_id) - indice PRIMARY - fk_line_user_id - fk_call_line_id_from - idx_calls_date_time (possible key, con más registros debería utilizar el indice)
create index idx_calls_date_time on calls(call_date) using BTREE;
	
	-- Ejemplo 1
explain select * from calls where call_date between "2020-05-28" and "2020-06-30"
	
	-- Ejemplo 2 (utilizado en CallDao)
explain select plo.line_number as OriginNumber, cio.city_name as OriginCity, pld.line_number as DestinationNumber, cid.city_name as DestinationCity, (co.call_minute_price * (co.call_duration_seg/60)) as TotalPrice, co.call_duration_seg as Duration, co.call_date as CallDate
from calls co inner join phone_lines plo on plo.line_id = co.call_line_id_from
inner join cities cio on cio.city_prefix = (SELECT SUBSTRING(plo.line_number,1,LENGTH(plo.line_number)-7))
inner join phone_lines pld on pld.line_id = co.call_line_id_to
inner join cities cid on cid.city_prefix = (SELECT SUBSTRING(pld.line_number,1,LENGTH(pld.line_number)-7))
inner join users u on u.user_id = plo.line_user_id
where (co.call_date between "2020-05-28" and "2020-06-30") and u.user_id = 1
order by co.call_date;



-- getCallByClient (utiliza user_id) - indice PRIMARY - fk_line_user_id - fk_call_line_id_from
explain select plo.line_number as OriginNumber, cio.city_name as OriginCity, pld.line_number as DestinationNumber, cid.city_name as DestinationCity, (co.call_minute_price * (co.call_duration_seg/60)) as TotalPrice, co.call_duration_seg as Duration, co.call_date as CallDate
	from calls co inner join phone_lines plo on plo.line_id = co.call_line_id_from
	inner join cities cio on cio.city_prefix = (SELECT SUBSTRING(plo.line_number,1,LENGTH(plo.line_number)-7))
	inner join phone_lines pld on pld.line_id = co.call_line_id_to
	inner join cities cid on cid.city_prefix = (SELECT SUBSTRING(pld.line_number,1,LENGTH(pld.line_number)-7))
	inner join users u on u.user_id = plo.line_user_id
	where u.user_id = 1
	order by u.user_id

-- getMostCalledCities (utiliza line_user_id) - indices fk_line_user_id - unq_line_number - PRIMARY - fk_call_line_id_from - unq_city_prefix
explain select ci.city_name City, count(ph.line_number) Calls from calls c
	inner join phone_lines ph on c.call_line_id_to = ph.line_id
	inner join cities ci on ci.city_id = (select city_id from cities where city_prefix = SUBSTRING(ph.line_number,1,LENGTH(ph.line_number)-7))
	where c.call_line_id_from in (select line_id from phone_lines where line_user_id = 2)
	group by city_name
	order by count(ph.line_number) desc
	limit 10