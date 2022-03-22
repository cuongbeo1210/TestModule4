package com.example.demo.controller;

import com.example.demo.model.City;
import com.example.demo.model.Country;
import com.example.demo.service.ICityService;
import com.example.demo.service.impl.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class CityController {

        @Autowired
        private ICityService iCityService;

        @Autowired
        private CountryService iCountryService;

        @ModelAttribute("countries")
        public Iterable<Country> getAll(){
            return iCountryService.findAll();
        }

        @GetMapping
        public ModelAndView showCity(@Valid @RequestParam("regex") Optional<String> regex, @SortDefault(sort = {"id"}) Pageable pageable){
            ModelAndView modelAndView = new ModelAndView("index");
            Page<City> cities;
            if (regex.isPresent()) {
                cities = iCityService.findAllByName(regex.get(), pageable);
            } else {
                cities = iCityService.findAll(pageable);
            }
            modelAndView.addObject("regex", regex.orElse(""));
            modelAndView.addObject("cities", cities);
            return modelAndView;
        }

        @GetMapping("/view/{id}")
        public ModelAndView viewCity(@PathVariable("id") Long id){
            ModelAndView modelAndView = new ModelAndView("view");
            City city = iCityService.findCity(id);
            modelAndView.addObject(city);
            return modelAndView;
        }

        @GetMapping("/delete/{id}")
        public ModelAndView deleteCity(@PathVariable("id") Long id, @SortDefault(sort = {"id"}) Pageable pageable){
            iCityService.delete(id);
            ModelAndView modelAndView = new ModelAndView("index");
            Page<City> cities = iCityService.findAll(pageable);
            modelAndView.addObject("cities", cities);
            modelAndView.addObject("success");
            return modelAndView;
        }

        @GetMapping("/create")
        public ModelAndView createCity(){
            ModelAndView modelAndView = new ModelAndView("create");
            modelAndView.addObject("city", new City());
            return modelAndView;
        }

        @PostMapping("/create")
        public ModelAndView saveCity(@Valid @ModelAttribute("city") City city, BindingResult bindingResult,
                                     @SortDefault(sort = {"id"}) Pageable pageable){
            if(bindingResult.hasErrors()){
                ModelAndView modelAndView = new ModelAndView("create");
                modelAndView.addObject("city", city);
                return modelAndView;
            }
            iCityService.save(city);
            Page<City> cities = iCityService.findAll(pageable);
            ModelAndView modelAndView = new ModelAndView("/index");
            modelAndView.addObject("cities", cities);
            modelAndView.addObject("success");
            return modelAndView;
        }

        @GetMapping("/edit/{id}")
        public ModelAndView editCity(@PathVariable("id") Long id){
            City city = iCityService.findCity(id);
            ModelAndView modelAndView = new ModelAndView("edit");
            modelAndView.addObject("city", city);
            return modelAndView;
        }

        @PostMapping("/edit/{id}")
        public ModelAndView updateCity(@Valid @ModelAttribute("city") City city, BindingResult bindingResult,
                                       @SortDefault(sort = {"id"}) Pageable pageable){
            if(bindingResult.hasErrors()){
                ModelAndView modelAndView = new ModelAndView("edit");
                modelAndView.addObject("city", city);
                return modelAndView;
            }
            iCityService.save(city);
            Page<City> cities = iCityService.findAll(pageable);
            ModelAndView modelAndView = new ModelAndView("index");
            modelAndView.addObject("cities", cities);
            modelAndView.addObject("success");
            return modelAndView;
        }
}
