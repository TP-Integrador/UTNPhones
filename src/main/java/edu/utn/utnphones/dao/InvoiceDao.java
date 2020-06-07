package edu.utn.utnphones.dao;

import edu.utn.utnphones.domain.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface InvoiceDao extends JpaRepository<Invoice,Integer>{

    @Query(value = "select * from invoices i inner join phone_lines pl on i.invoice_line_id = pl.line_id \n" +
            "inner join users u on pl.line_user_id = u.user_id\n" +
            "where i.invoice_date  >= ?1 and i.invoice_date <= ?2 and u.user_id = ?3",nativeQuery = true)
    List<Invoice> getInvoicesByDate(Date dateFrom, Date dateTo, int userId);
    
}
