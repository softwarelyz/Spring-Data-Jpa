package com.example.springdatajpa.controller;

import com.example.springdatajpa.dao.CustomerProjection;
import com.example.springdatajpa.dao.CustomerRepository;
import com.example.springdatajpa.dao.CustomerSpecificationRepository;
import com.example.springdatajpa.dto.Customer;
import com.example.springdatajpa.factory.SpecificationFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Controller
@RequestMapping
public class CustomerController {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    private CustomerSpecificationRepository csr;

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

    /**
     * @Query注解方式查询,
     * 用@Param指定参数，匹配firstName和lastName
     */
    @RequestMapping("/findByName4")
    public void findByName4(){
        //按照ID倒序排列
        System.out.println("直接创建sort对象，通过排序方法和属性名");
        Sort sort = new Sort(Sort.Direction.DESC,"id");
        List<Customer> result = customerRepository.findByName4("Bauer",sort);
        for (Customer customer:result){
            System.out.println(customer.toString());
        }
        System.out.println("-------------------------------------------");
        //按照ID倒序排列
        System.out.println("通过Sort.Order对象创建sort对象");
        Sort sortx = new Sort(new Sort.Order(Sort.Direction.DESC,"id"));
        List<Customer> resultx = customerRepository.findByName4("Bauer",sort);
        for (Customer customer:result){
            System.out.println(customer.toString());
        }
        System.out.println("-------------------------------------------");

        System.out.println("通过排序方法和属性List创建sort对象");
        List<String> sortProperties = new ArrayList <>();
        sortProperties.add("id");
        sortProperties.add("firstName");
        Sort sort2 = new Sort(Sort.Direction.DESC,sortProperties);
        List<Customer> result2 = customerRepository.findByName4("Bauer",sort2);
        for (Customer customer:result2){
            System.out.println(customer.toString());
        }
        System.out.println("-------------------------------------------");

        System.out.println("通过创建Sort.Order对象的集合创建sort对象");
        List<Sort.Order> orders = new ArrayList<>();
        orders.add(new Sort.Order(Sort.Direction.DESC,"id"));
        orders.add(new Sort.Order(Sort.Direction.ASC,"firstName"));
        List<Customer> result3 = customerRepository.findByName4("Bauer",new Sort(orders));
        for (Customer customer:result3){
            System.out.println(customer.toString());
        }
        System.out.println("-------------------------------------------");
    }

    /**
     * 根据FirstName进行修改
     */
    @RequestMapping("/modifying")
    public void modifying(){
        Integer result = customerRepository.setFixedFirstnameFor("Bauer","Dessler");
        if(result!=null){
            System.out.println("modifying result:"+result);
        }
        System.out.println("-------------------------------------------");

    }

    /**
     * 分页
     * 应用查询提示@QueryHints，这里是在查询的适合增加了一个comment
     * 查询结果是lastName和firstName都是bauer这个值的数据
     */
    @RequestMapping("/pageable")
    public void pageable(){
        //Pageable是接口，PageRequest是接口实现
        //PageRequest的对象构造函数有多个，page是页数，初始值是0，size是查询结果的条数，后两个参数参考Sort对象的构造方法
        Pageable pageable = new PageRequest(0,3, Sort.Direction.DESC,"id");
        Page<Customer> page = customerRepository.findByName("bauer",pageable);
        //查询结果总行数
        System.out.println(page.getTotalElements());
        //按照当前分页大小，总页数
        System.out.println(page.getTotalPages());
        //按照当前页数、分页大小，查出的分页结果集合
        for (Customer customer: page.getContent()) {
            System.out.println(customer.toString());
        }
        System.out.println("-------------------------------------------");
    }

    @RequestMapping("/findAllProjections")
    public void findAllProjections(){
        Collection<CustomerProjection> projections = customerRepository.findAllProjectedBy();
        System.out.println(projections);
        System.out.println(projections.size());
        for (CustomerProjection projection:projections){
            System.out.println("FullName:"+projection.getFullName());
            System.out.println("FirstName:"+projection.getFirstName());
            System.out.println("LastName:"+projection.getLastName());
        }
    }



    @RequestMapping("/spec")
    public void specificationQuery(){
        Specification<Customer> spec = SpecificationFactory.containsLike("lastName","bau");
        Pageable pageable = new PageRequest(0,5, Sort.Direction.DESC,"id");
        Page<Customer> page = csr.findAll(spec,pageable);
        System.out.println(page);
        System.out.println(page.getTotalElements());
        System.out.println(page.getTotalPages());
        for (Customer c:page.getContent()){
            System.out.println(c.toString());
        }
    }

    @RequestMapping("/spec2")
    public void specificationQuery2(){
        Specification<Customer> spec2 = Specifications
                .where(SpecificationFactory.containsLike("firstName","bau"))
                .or(SpecificationFactory.containsLike("lastName","bau"));
        Pageable pageable = new PageRequest(0,5, Sort.Direction.DESC,"id");
        Page<Customer> page = csr.findAll(spec2,pageable);
        System.out.println(page);
        System.out.println(page.getTotalElements());
        System.out.println(page.getTotalPages());
        for (Customer c:page.getContent()){
            System.out.println(c.toString());
        }
    }
}