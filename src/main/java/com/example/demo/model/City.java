package com.example.demo.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Data
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    @Min(value = 0, message = "Không thể âm")
    @Pattern(regexp = "(^$|[0-9]{1,20})" , message = "Là Số")
    private String area;

    @NotNull
    @Min(value = 0, message = "Không thể âm")
    @Pattern(regexp = "(^$|[0-9]{1,20})" , message = "Là Số")
    private String population;

    @NotNull
    @Min(value = 0, message = "Không thể âm")
    @Pattern(regexp = "(^$|[0-9]{1,20}$)" , message = "Là Số")
    private String gdp;

    @NotNull
    private String description;

    @ManyToOne
    private Country country;
}
