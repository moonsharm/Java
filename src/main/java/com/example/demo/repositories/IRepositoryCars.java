package com.example.demo.repositories;


import com.example.demo.model.CCar;
import com.example.demo.model.CReportItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRepositoryCars extends JpaRepository<CCar, Long> {
    @Query(
            value = """
                SELECT c.id, c.name AS name, c.price AS price, COUNT(p.car_id) AS car_count
                FROM cars c
                JOIN purchase p ON c.id = p.car_id
                GROUP BY c.name, c.price, c.id
                order by car_count desc
                limit 5;
            """,
            nativeQuery = true)
    List<CReportItem> bestSellingCars();
}

