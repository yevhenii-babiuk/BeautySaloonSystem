package com.saloon.beauty.repository.dao.implementation;

import com.saloon.beauty.repository.DaoManager;
import com.saloon.beauty.repository.dao.SlotDtoDao;
import com.saloon.beauty.repository.dao.UserDao;
import com.saloon.beauty.repository.dto.SlotDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.util.Optional;

public class SlotDtoDaoImpl implements SlotDtoDao {

    private static final Logger LOG = LogManager.getLogger(UserDao.class);

    private Connection connection;
    private DaoManager manager;

    public SlotDtoDaoImpl(Connection connection, DaoManager manager) {
        this.connection = connection;
        this.manager = manager;
    }

    @Override
    public Optional<SlotDto> getFullInformation(long id) {
        return Optional.empty();
    }
}
