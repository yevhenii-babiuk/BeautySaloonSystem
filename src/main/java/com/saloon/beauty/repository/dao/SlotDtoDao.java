package com.saloon.beauty.repository.dao;

import com.saloon.beauty.repository.dto.SlotDto;

import java.util.Optional;

public interface SlotDtoDao {

    Optional<SlotDto> getFullInformation(long id);

}
