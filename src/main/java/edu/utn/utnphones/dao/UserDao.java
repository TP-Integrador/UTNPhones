package edu.utn.utnphones.dao;

import edu.utn.utnphones.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository<User,Integer> {

    //Query nativa JPQL
    @Query(value = "SELECT * FROM users u WHERE u.username = ?1 and u.userpass = ?2", nativeQuery = true)
    User getByUsername(String username, String password);

    @Query(value = "SELECT * FROM users u WHERE u.user_dni = ?1", nativeQuery = true)
    Optional<User> findByDni(String dni);
}
