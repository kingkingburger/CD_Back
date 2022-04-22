package com.example.demo.entity;


import lombok.Data;
import lombok.ToString;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table
@Component
@ToString(exclude = "ProductList")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int categoryid;
    private String first;
    private String second;

//    @OneToMany(mappedBy = "productid", fetch=FetchType.EAGER)
//    private List<Product> ProductList = new ArrayList<Product>();

}
