package edu.utn.utnphones.dao;

import edu.utn.utnphones.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {

    @Query(value = "SELECT * FROM users u WHERE u.username = ?1 and u.userpass = ?2", nativeQuery = true)
    User getByUsername(String username, String password);

    @Query(value = "SELECT * FROM users u WHERE u.user_dni = ?1", nativeQuery = true)
    User findByDni(String dni);

    @Query(value = "SELECT * FROM users u WHERE u.user_type_id = 1 and u.user_id = ?1", nativeQuery = true)
    User findByIdAndUserType(int idClient);

    @Query(value = "SELECT * FROM users u WHERE u.username = ?1", nativeQuery = true)
    User findByUserName(String username);

    @Transactional
    @Modifying
    @Query(value = "UPDATE users u SET u.user_active = ?1 where user_id = ?2", nativeQuery = true)
    void updateStatus(Boolean active, Integer idClient);
}
