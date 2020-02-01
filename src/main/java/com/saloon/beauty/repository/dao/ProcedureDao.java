package com.saloon.beauty.repository.dao;

import com.saloon.beauty.repository.entity.Procedure;

import java.util.List;

/**
 * Dao for Procedure entity
 */
public interface ProcedureDao extends Dao<Procedure>{

    /**
     * Counts amount of all procedures
     * @return amount of all procedures
     */
    long getProcedureSearchResultCount();

    /**
     * Finds all procedures in a DB
     * @param limit the number of procedure
     * @param offset the number of procedure
     * @return - list with all procedures
     */
    List<Procedure> getAllProcedureParametrized(int limit, int offset);
}
