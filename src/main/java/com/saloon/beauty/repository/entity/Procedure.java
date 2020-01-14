package com.saloon.beauty.repository.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Procedure {

    private  long id;

    private String nameUkr;

    private String descriptionUkr;

    private String nameEn;

    private String descriptionEn;

    private String nameRus;

    private String descriptionRus;

    private int price;

}
