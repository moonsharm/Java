package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity(name = "purchase")
public class CPurchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "car_id", nullable = false)
    @JsonIgnore
    private CCar car;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "date_of_purchase")
    private LocalDate dateOfPurchase;

}
