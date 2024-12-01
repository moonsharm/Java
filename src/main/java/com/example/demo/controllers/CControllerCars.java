package com.example.demo.controllers;

import com.example.demo.model.CCar;
import com.example.demo.repositories.IRepositoryCars;
import com.example.demo.services.CServiceReport;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/cars")
public class CControllerCars {

    @Autowired
    private IRepositoryCars repositoryCars;

    @Autowired
    private CServiceReport serviceReport;

    @GetMapping
    public List<CCar> getAll() {
        return repositoryCars.findAll();
    }

    @GetMapping("/{id}")
    public Optional<CCar> getById(@PathVariable Long id) {
        return repositoryCars.findById(id);
    }

    @PostMapping
    public CCar createStudent(@RequestBody CCar car) {
        return repositoryCars.save(car);
    }

    @PutMapping("/{id}")
    public CCar update(@PathVariable Long id, @RequestBody CCar car)
    {
        return repositoryCars.findById(id)
                .map(cars -> {
                    cars.setName(car.getName());
                    cars.setPrice(car.getPrice());
                    cars.setBrand(car.getBrand());
                    cars.setYear(car.getYear());
                    return repositoryCars.save(cars);
                })
                .orElseGet(() -> {
                    return repositoryCars.save(car);
                });
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repositoryCars.deleteById(id);
    }

    @PostMapping(value = "/upload", consumes = {"*/*"})
    public String handleFileUpload(
            @RequestParam("file") MultipartFile file
    ) {
        try{
            Workbook wb = WorkbookFactory.create(file.getInputStream());
            Sheet sheet = wb.getSheetAt(0);
            int rows = sheet.getLastRowNum();
            Row row;
            String name;
            double price;
            int year;
            String brand;
            CCar car;
            for (int i=1; i<=rows; i++)
            {
                row = sheet.getRow(i);
                if (row==null) continue;

                name = row.getCell(0).getStringCellValue();
                price = (row.getCell(1).getNumericCellValue());
                brand = row.getCell(2).getStringCellValue();
                year = (int)(row.getCell(3).getNumericCellValue());

                car = new CCar(name, price, year, brand);

                repositoryCars.save(car);
            }
            repositoryCars.flush();
        }
        catch (IOException e)
        {
            return "Ошибка!"+e.getMessage();
        }

        return "Загружено!";
    }

    @GetMapping(value = "/report")
    public ResponseEntity<ByteArrayResource> report()
    {
        byte[] report = serviceReport.createReport(); // creates the workbook
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "force-download"));
        header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Report.docx");
        if (report.length>0)
            return new ResponseEntity<>(new ByteArrayResource(report),
                    header, HttpStatus.CREATED);
        else
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}