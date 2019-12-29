package com.saloon.beauty.repository.dao.implementation;

import com.saloon.beauty.repository.dao.ProcedureDao;
import com.saloon.beauty.repository.dao.UserDao;
import com.saloon.beauty.repository.entity.Procedure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class ProcedureDaoImpl implements ProcedureDao {

    private static final Logger LOG = LogManager.getLogger(UserDao.class);

    private Connection connection;

    public ProcedureDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<Procedure> getProcedureByName(String name) {
        return Optional.empty();
    }

    @Override
    public int getPriceByName(String name) {
        return 0;
    }

    @Override
    public String getDescriptionByName(String name) {
        return null;
    }

    @Override
    public Optional<Procedure> get(long id) {
        return Optional.empty();
    }

    @Override
    public List<Procedure> getAll() {
        return null;
    }

    @Override
    public long save(Procedure procedure) {
        return 0;
    }

    @Override
    public void update(Procedure procedure) {

    }

    @Override
    public void delete(Procedure procedure) {

    }
}
