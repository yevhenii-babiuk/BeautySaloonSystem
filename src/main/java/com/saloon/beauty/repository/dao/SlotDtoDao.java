package com.saloon.beauty.repository.dao;

import com.saloon.beauty.repository.dto.SlotDto;

import java.util.Optional;

/**
 * Dao for SlotDtoDao entity
 */
public interface SlotDtoDao {


    /**
     * Create entity of {@code SlotDto} by id of slot
     * @param id - entity id in the DB
     * @return Optional with {@code SlotDto} or empty Optional
     */
    Optional<SlotDto> getFullInformation(long id);

}
