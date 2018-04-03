package com.example.springdatajpa.controller;

import com.example.springdatajpa.dao.CustomerRepository;
import com.example.springdatajpa.dto.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping
public class CustomerController {
    @Autowired
    CustomerRepository customerRepository;

    //初始化数据
    @RequestMapping("/index")
    public void index() {
        customerRepository.save(new Customer("Jack", "Bauer"));
        customerRepository.save(new Customer("Chloe", "O'Brian"));
        customerRepository.save(new Customer("Kim", "Bauer"));
        customerRepository.save(new Customer("David", "Palmer"));
        customerRepository.save(new Customer("Michelle", "Dessler"));
        customerRepository.save(new Customer("Bauer", "Dessler"));
    }

    //查询所有数据
    @RequestMapping("/findAll")
    public void findAll() {
        List<Customer> result = customerRepository.findAll();
        for (Customer customer : result) {
            System.out.println(customer.toString());
        }
        System.out.println("-------------------------------------------");
    }

    //查询单个
    @RequestMapping("/findOne")
    public void findOne() {
        Customer result = customerRepository.getCustomerById(1L);
        if (result != null) {
            System.out.println(result.toString());
        }
        System.out.println("-------------------------------------------");
    }

    /**
     * 查询ID为1的数据后删除
     */
    @RequestMapping("/delete")
    public void delete() {

        System.out.println("删除前数据：");
        List<Customer> customers = customerRepository.findAll();
        for (Customer customer : customers) {
            System.out.println(customer.toString());
        }

        System.out.println("删除ID=3数据：");
        customerRepository.deleteById(3l);

        System.out.println("删除后数据：");
        customers = customerRepository.findAll();
        for (Customer customer : customers) {
            System.out.println(customer.toString());
        }
        System.out.println("-------------------------------------------");
    }

    //通过LastNamelaic来查询
    @RequestMapping("/findByLastName")
    public void findByLastName(){
        List<Customer> result = customerRepository.findByLastName("Bauer");

        for (Customer customer : result) {
            System.out.println(customer.toString());
        }

        System.out.println(".................");
    }

    //预定义查询
    @RequestMapping("/findByFirstName")
    public void findByFirstName(){
        Customer customer = customerRepository.findByFirstName("Bauer");
        if (customer!=null){
            System.out.println(customer.toString());
        }
        System.out.println("............");
    }


    //@Query注解方式查询
    @RequestMapping("/findByFirstName2")
    public void findByFirstName2(){
        Customer customer = customerRepository.findByFirstName("Bauer");
        if (customer!=null){
            System.out.println(customer.toString());
        }
        System.out.println("............");
    }
    @RequestMapping("/findByLastName2")
    public void findByLastName2(){
        List<Customer> result = customerRepository.findByLastName("Bauer");

        for (Customer customer : result) {
            System.out.println(customer.toString());
        }

        System.out.println(".................");
    }

    /**
     * @Query注解方式查询,
     * 用@Param指定参数，匹配firstName和lastName
     */
    @RequestMapping("/findByName")
    public void findByName(){
        List<Customer> result = customerRepository.findByName("Bauer");
        for (Customer customer:result){
            System.out.println(customer.toString());
        }
        System.out.println("-------------------------------------------");
    }

    /**
     * @Query注解方式查询,使用关键词like
     * 用@Param指定参数，firstName的结尾为e的字符串
     */
    @RequestMapping("/findByName2")
    public void findByName2(){
        List<Customer> result = customerRepository.findByName2("e");
        for (Customer customer:result){
            System.out.println(customer.toString());
        }
        System.out.println("-------------------------------------------");
    }

    /**
     * @Query注解方式查询，模糊匹配关键字e
     */
    @RequestMapping("/findByName3")
    public void findByName3(){
        List<Customer> result = customerRepository.findByName3("e");
        for (Customer customer:result){
            System.out.println(customer.toString());
        }
        System.out.println("-------------------------------------------");
    }
}