package com.saloon.beauty.repository.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Procedure {

    private  long id;

    private String name;

    private String description;

    private int price;

}
