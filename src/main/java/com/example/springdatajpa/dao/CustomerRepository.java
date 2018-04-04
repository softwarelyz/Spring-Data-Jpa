package com.example.springdatajpa.dao;

import com.example.springdatajpa.dto.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import static org.hibernate.jpa.QueryHints.HINT_COMMENT;
import javax.persistence.QueryHint;
import javax.transaction.Transactional;
import java.awt.print.Book;
import java.util.Collection;
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
    
    /*
    ？加数字表示占位符，？1代表在方法参数里的第一个参数，区别于其他的index，这里从1开始

    =:加上变量名，这里是与方法参数中有@Param的值匹配的，而不是与实际参数匹配的

    JPQL的语法中，表名的位置对应Entity的名称，字段对应Entity的属性,详细语法见相关文档

    要使用原生SQL需要在@Query注解中设置nativeQuery=true，然后value变更为原生SQL即可
    */


    /**
     * 一个参数，匹配两个字段
     * @param name2
     * @param sort 指定排序的参数，可以根据需要进行调整
     * @return
     * 这里Param的值和=:后面的参数匹配，但不需要和方法名对应的参数值对应
     *
     */
    @Query("select c from Customer c where c.firstName=:name or c.lastName=:name")
    List<Customer> findByName4(@Param("name") String name2,Sort sort);


    /**
     * 根据lastName去更新firstName，返回结果是更改数据的行数
     * @param firstName
     * @param lastName
     * @return
     */
    @Modifying//更新查询
    @Transactional//开启事务
    @Query("update Customer c set c.firstName = ?1 where c.lastName = ?2")
    int setFixedFirstnameFor(String firstName, String lastName);

    /**
     * 一个参数，匹配两个字段
     * @param name2
     * @Param pageable 分页参数
     * @return
     * 这里Param的值和=:后面的参数匹配，但不需要和方法名对应的参数值对应
     * 这里增加了@QueryHints注解，是给查询添加一些额外的提示
     * 比如当前的name值为HINT_COMMENT是在查询的时候带上一些备注信息
     */
    @QueryHints(value = { @QueryHint(name = HINT_COMMENT, value = "a query for pageable")})
    @Query("select c from Customer c where c.firstName=:name or c.lastName=:name")
    Page<Customer> findByName(@Param("name") String name2, Pageable pageable);

    @Query("SELECT c.firstName as firstName,c.lastName as lastName from Customer  c")
    Collection<CustomerProjection> findAllProjectedBy();
}
