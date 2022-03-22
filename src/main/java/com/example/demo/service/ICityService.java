package com.example.demo.service;

import com.example.demo.model.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICityService {

    Page<City> findAll(Pageable pageable);

    Page<City> findAllByName(String name, Pageable pageable);

    void save(City city);

    City findCity(Long id);

    void delete(Long id);
}
