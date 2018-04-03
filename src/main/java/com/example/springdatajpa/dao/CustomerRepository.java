package com.example.springdatajpa.dao;

import com.example.springdatajpa.dto.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.awt.print.Book;
import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
    Customer getCustomerById(Long id);
    List<Customer> findByLastName(String lastName);
    Customer findByFirstName(String bauer);

    //模糊匹配@Query注解形式
    @Query("select c from Customer c where c.firstName=?1")
    Customer findByFirstName2(String bauer);

    @Query("select c from Customer c where c.lastName=?1 order by c.id desc")
    List<Customer> findByLastName2(String lastName);

    /**
     * 一个参数，匹配两个字段
     * @param name2
     * @return
     * 这里Param的值和=:后面的参数匹配，但不需要和方法名对应的参数值对应
     */
    @Query("select c from Customer c where c.firstName=:name or c.lastName=:name  order by c.id desc")
    List<Customer> findByName(@Param("name") String name2);

    /**
     * 一个参数，匹配两个字段
     * @param name
     * @return
     * 这里的%只能放在占位的前面，后面不行
     */
    @Query("select c from Customer c where c.firstName like %?1")
    List<Customer> findByName2(@Param("name") String name);


    /**
     * 一个参数，匹配两个字段
     * @param name
     * @return
     * 开启nativeQuery=true，在value里可以用原生SQL语句完成查询
     */
    @Query(nativeQuery = true,value = "select * from Customer c where c.first_name like concat('%' ,?1,'%') ")
    List<Customer> findByName3(@Param("name") String name);

}
