
#  ---------------------------------------
#  -------------  Functions  -------------
#  ---------------------------------------
 
-- drop function if exists getPhone;
create function getPhone(pline_id int)
returns varchar(15)
reads sql data
begin
    declare vline_number varchar(15);
    set vline_number = (select line_number from phone_lines where line_id = pline_id);
	if (vline_number is not null) then
		return vline_number;
    else
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Phone number not exists', MYSQL_ERRNO = 1000;
    end if;
end


-- drop function if exists getCity;
create function getCity(pphone_number varchar(15))
returns int
reads sql data
begin
    declare vCityId int default 0;
	select city_Id into vCityId from cities where pphone_number like concat(city_prefix,"%")    
	order by length(city_prefix) DESC LIMIT 1;
	if (vCityId > 0) then
		return vCityId;
    else
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'City by prefix not exists', MYSQL_ERRNO = 1000;
    end if;
end


-- drop function if exists getRate;
create function getRate(pcity_from_id int, pcity_to_id int)
returns float
reads sql data
begin
    declare vRate_value float;
    select rate_value into vRate_value from rates where city_from_id = pcity_from_id and city_to_id = pcity_to_id;
    if(vRate_value is not null) then
		return vRate_value; 
    else
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Rate not exists', MYSQL_ERRNO = 1000;
    end if;
end


-- drop function if exists getCost;
create function getCost(pcity_from_id int, pcity_to_id int)
returns float
reads sql data
begin
    declare vRate_cost float;
    select rate_cost into vRate_cost from rates where city_from_id = pcity_from_id and city_to_id = pcity_to_id;
    if(vRate_cost is not null) then
		return vRate_cost; 
    else
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Rate cost not exists', MYSQL_ERRNO = 1000;
    end if;
end


#  ---------------------------------------
#  --------------  Triggers  -------------
#  ---------------------------------------


-- drop trigger  if exists tbi_add_calls 
create trigger tbi_add_calls before insert on calls for each row
Begin
	
	declare id_cityfrom int default 0;
	declare id_cityto int default 0;
	declare line_number_from varchar(15) default '';
	declare line_number_to varchar(15) default '';
	declare prefix_from varchar(5) default '';	
	declare prefix_to varchar(5) default '';

	-- obtengo line_number a partir del id
	set line_number_from = getPhone(new.call_line_id_from);
    set line_number_to = getPhone(new.call_line_id_to);

	-- obtengo ciudad a partir del prefijo
	set id_cityfrom = getCity(line_number_from);
	set id_cityto = getCity(line_number_to);
	
	-- recupero la tarifa correspondiente
	set new.call_minute_price  = getRate(id_cityfrom, id_cityto);
	set new.call_minute_cost   = getCost(id_cityfrom, id_cityto);
end


 
-- drop trigger if exists tbi_set_status_phone_line
create trigger tbi_set_status_phone_line before insert on phone_lines for each row
begin
	set new.line_status = "Active";
end


-- drop trigger if exists tbi_set_active_user
create trigger tbi_set_active_user before insert on users for each row
begin
	set new.user_active = true;
end


-- drop trigger if exists tbu_set_inactive_user
create trigger tbu_set_inactive_user before update on users for each row
begin
	declare aux bool default true;
	set aux = new.user_active;
	if (aux = false) then
		update phone_lines set line_status = 'Inactive' where line_user_id = new.user_id;
	end if;
end


#  ---------------------------------------------------
#  -------  SP Invoices + EVENT scheduler  --------
#  ---------------------------------------------------

set autocommit = 0;
select @@autocommit;

-- drop procedure if exists sp_call_billing
create procedure sp_call_billing()
begin
	
	-- declarar variables
	declare vIdInvoice int;
	declare vTotalPrice float;
	declare vTotalCost float;
	declare vCountCall int;
	declare vCall_id int;
	declare vCall_line_id_from int;
	declare vCall_line_id_from_old int default 0;
	declare vDateInvoice Date default now();
	declare vDateInvoice_due Date default DATE_ADD(NOW(),INTERVAL 15 DAY);
	declare vFinished int default 0;
	
	-- recuperar cursor con llamadas ordenado por telefono (from)
	declare cur_call_billing cursor for
	select call_id, call_line_id_from from calls where call_invoice_id is null order by call_line_id_from;
	declare continue handler for not found set vFinished = 1;
	
	start transaction;

	-- recorre el cursor por telefono
	open cur_call_billing;
	fetch cur_call_billing INTO vCall_id, vCall_line_id_from;
	while (vFinished = 0) do
		
		-- corte de control por telefono
		 if (vCall_line_id_from_old <> vCall_line_id_from) then

			set vTotalPrice = 0;
			set vTotalCost = 0;
			set vCountCall = 0;
		
			-- calculos (totales) de todas las llamadas de ese telefono
			select count(*), sum(call_minute_cost), sum((call_minute_price * (call_duration_seg/60))) into vCountCall, vTotalCost, vTotalPrice
			from calls where call_line_id_from = vCall_line_id_from and call_invoice_id is null;
			
			-- insert de nueva factura
			insert into invoices 
			(invoice_line_id, invoice_quantity_calls, invoice_cost_price, invoice_total_price, invoice_date, invoice_due_date) 
			VALUES(vCall_line_id_from, vCountCall, vTotalCost, vTotalPrice, vDateInvoice, vDateInvoice_due);
			
			set vIdInvoice = last_insert_id();				
			set vCall_line_id_from_old = vCall_line_id_from;
			
		 end if;
		
		-- actualizar llamadas con id de factura
		update calls set call_invoice_id = vIdInvoice where call_id = vCall_id and call_invoice_id is null;
	
		fetch cur_call_billing INTO vCall_id, vCall_line_id_from;
	end while;
	-- cierro cursor
	close cur_call_billing;
	commit;
end


SET GLOBAL event_scheduler = ON;
-- drop event if exists event_call_billing
CREATE EVENT event_call_billing
ON SCHEDULE EVERY "1" MONTH	Starts "2020-06-01 07:00:00"
DO
begin
	call sp_call_billing();
end; 
