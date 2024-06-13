package com.example.server.repository;

import com.example.server.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    public Customer findByEmail(String email);

    public void deleteByEmail(String email);

    @Query("SELECT c FROM Customer c WHERE c.firstName LIKE %:searchTerm%")
    List<Customer> findByFirstNameLike(String searchTerm);

    @Query("SELECT c FROM Customer c WHERE c.city LIKE %:searchTerm%")
    List<Customer> findByCityLike(String searchTerm);

    @Query("SELECT c FROM Customer c WHERE c.email LIKE %:searchTerm%")
    List<Customer> findByEmailLike(String searchTerm);

    @Query("SELECT c FROM Customer c WHERE c.phone LIKE %:searchTerm%")
    List<Customer> findByPhoneLike(String searchTerm);

}