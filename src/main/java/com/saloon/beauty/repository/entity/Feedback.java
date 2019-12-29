package com.saloon.beauty.repository.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Feedback {

    private long id;

    private long slot;

    private String text;
}
