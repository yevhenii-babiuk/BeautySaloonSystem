package com.saloon.beauty.repository.dto;
import com.saloon.beauty.repository.entity.Feedback;
import com.saloon.beauty.repository.entity.Procedure;
import com.saloon.beauty.repository.entity.Slot;
import com.saloon.beauty.repository.entity.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SlotDto {

    private Slot slot;

    private Feedback feedback;

    private User client;

    private User master;

    private Procedure procedure;
}
