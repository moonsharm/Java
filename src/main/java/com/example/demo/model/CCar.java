package com.example.demo.model;
import java.time.Year;

import jakarta.persistence.*;

@Entity(name = "cars")
public class CCar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String brand;

    @Column
    private Double price;

    @Column
    private Integer year;

    public CCar() {
        this.name = "";
        this.price = 0.0;
        this.year = 0;
        this.brand = "";
    }

    public CCar(String name, Double price, Integer year, String brand) {
        this.name = name;
        this.price = price;
        this.year = year;
        this.brand = brand;
    }

    public CCar(Long id, String name, Double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        int currentYear = Year.now().getValue();
        if (year >= 0 && year <= currentYear) {
            this.year = year;
        }
    }


    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setPrice(Double price) {
        if (price>=0)
            this.price = price;
    }
}
