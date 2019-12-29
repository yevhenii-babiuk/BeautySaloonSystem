package com.saloon.beauty.repository.dao.implementation;

import com.saloon.beauty.repository.dao.SlotDao;
import com.saloon.beauty.repository.dao.UserDao;
import com.saloon.beauty.repository.entity.Slot;
import com.saloon.beauty.repository.entity.Status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public class SlotDaoImpl implements SlotDao {

    private static final Logger LOG = LogManager.getLogger(UserDao.class);

    private Connection connection;

    public SlotDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Slot> getSlotByDataTime(LocalDate minDate, LocalDate maxDate, LocalTime minTime, LocalTime maxTime) {
        return null;
    }

    @Override
    public List<Slot> getSlotByMaster(String master) {
        return null;
    }

    @Override
    public List<Slot> getSlotByStatus(String status) {
        return null;
    }

    @Override
    public void updateSlotStatus(long id, Status status) {

    }

    @Override
    public Optional<Slot> get(long id) {
        return Optional.empty();
    }

    @Override
    public List<Slot> getAll() {
        return null;
    }

    @Override
    public long save(Slot slot) {
        return 0;
    }

    @Override
    public void update(Slot slot) {

    }

    @Override
    public void delete(Slot slot) {

    }
}
