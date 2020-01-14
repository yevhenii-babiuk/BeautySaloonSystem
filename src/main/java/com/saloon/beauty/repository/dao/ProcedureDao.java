package com.saloon.beauty.repository.dao;

import com.saloon.beauty.repository.entity.Procedure;

import java.util.Map;
import java.util.Optional;

public interface ProcedureDao extends Dao<Procedure>{

    Optional<Procedure> getProcedureByName(String name);

    int getPriceByName(String name);

    Map<String, String> getDescriptionByName(String name);

}
