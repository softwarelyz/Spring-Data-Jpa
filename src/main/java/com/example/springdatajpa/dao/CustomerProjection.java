package com.example.springdatajpa.dao;

import org.springframework.beans.factory.annotation.Value;

//投影Projection的用法
public interface CustomerProjection {
    @Value("#{target.firstName + ' ' + target.lastName}")
    String getFullName();

    String getFirstName();

    String getLastName();
}
