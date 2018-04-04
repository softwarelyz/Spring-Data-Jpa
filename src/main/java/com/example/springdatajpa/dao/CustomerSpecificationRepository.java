package com.example.springdatajpa.dao;

import com.example.springdatajpa.dto.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CustomerSpecificationRepository extends JpaRepository<Customer,Long>,JpaSpecificationExecutor<Customer> {

}
