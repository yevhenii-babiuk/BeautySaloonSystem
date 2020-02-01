package com.saloon.beauty.services;

import com.saloon.beauty.repository.DaoManager;
import com.saloon.beauty.repository.DaoManagerFactory;
import com.saloon.beauty.repository.dao.ProcedureDao;
import com.saloon.beauty.repository.entity.Procedure;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Service class which has methods bound with Procedure operations
 * and DAO
 */
public class ProcedureService extends Service {

    private DaoManagerFactory daoManagerFactory;

    public ProcedureService(DaoManagerFactory daoManagerFactory) {
        this.daoManagerFactory = daoManagerFactory;
    }

    /**
     * Adds new procedure
     * @param nameUkr - ukrainian name of procedure
     * @param descriptionUkr - ukrainian description of procedure
     * @param nameEn - english name of procedure
     * @param descriptionEn - english description of procedure
     * @param nameRus - russian name of procedure
     * @param descriptionRus - russian name of procedure
     * @param price - procedure`s price
     * @return identifier of new added procedure
     */
    public long addNewProcedure(String nameUkr, String descriptionUkr,
                                String nameEn, String descriptionEn,
                                String nameRus, String descriptionRus,
                                int price){

        DaoManager daoManager = daoManagerFactory.createDaoManager();

        Procedure procedure = Procedure.builder()
                .nameUkr(nameUkr)
                .nameEn(nameEn)
                .nameRus(nameRus)
                .descriptionUkr(descriptionUkr)
                .descriptionEn(descriptionEn)
                .descriptionRus(descriptionRus)
                .price(price)
                .build();

        Object executionResult = daoManager.executeTransaction(manager -> addNewProcedureCommand(manager, procedure));

        return checkAndCastObjectToLong(executionResult);
    };

    /**
     * Method that return list of all procedure
     * @return {@code List} of all procedures
     */
    public List<Procedure> getAllProcedure(){
        DaoManager daoManager = daoManagerFactory.createDaoManager();

        Object executionResult = daoManager.executeAndClose(this::getAllProcedureCommand);

        return checkAndCastObjectToList(executionResult);
    }


     /**
     * Method, that returns procedure if such exist by identifier
     * @param procedureId - identifier of required procedure
     * @return {@code Optional} of required procedure
     */
    public Optional<Procedure> getProcedureById(long procedureId){
        DaoManager daoManager = daoManagerFactory.createDaoManager();

        Object executingResult = daoManager.executeAndClose(manager -> manager.getProcedureDao().get(procedureId));

        return checkAndCastObjectToOptional(executingResult);
    }

    public boolean updateProcedure(long procedureId,
                                   String nameUkr, String descriptionUkr,
                                   String nameEn, String descriptionEn,
                                   String nameRus, String descriptionRus,
                                   int price){

        Procedure procedure = Procedure.builder()
                .id(procedureId)
                .nameUkr(nameUkr)
                .nameEn(nameEn)
                .nameRus(nameRus)
                .descriptionUkr(descriptionUkr)
                .descriptionEn(descriptionEn)
                .descriptionRus(descriptionRus)
                .price(price)
                .build();

        DaoManager daoManager = daoManagerFactory.createDaoManager();

        Object executionResult = daoManager.executeAndClose(manager -> updateProcedureDataCommand(manager, procedure));

        return checkAndCastExecutingResult(executionResult);
    }

    public long getProcedureSearchResultCount() {
        DaoManager daoManager = daoManagerFactory.createDaoManager();

        Object executionResult = daoManager.executeAndClose(manager ->
                manager.getProcedureDao().getProcedureSearchResultCount());

        return checkAndCastObjectToLong(executionResult);
    }

    public List<Procedure> getProcedureParametrized(int recordsQuantity, int previousRecordNumber){
        DaoManager daoManager = daoManagerFactory.createDaoManager();

        Object executionResult = daoManager.executeAndClose(manager -> getProcedureParametrizedCommand(manager, recordsQuantity, previousRecordNumber));
        return checkAndCastObjectToList(executionResult);
    }


    //Commands which is needed to be executed in corresponding public service methods
    List<Procedure> getAllProcedureCommand(DaoManager manager) throws SQLException {
        return manager.getProcedureDao().getAll();
    }

    List<Procedure> getProcedureParametrizedCommand(DaoManager manager, int recordsQuantity, int previousRecordNumber) throws SQLException {
        return manager.getProcedureDao()
                .getAllProcedureParametrized(recordsQuantity, previousRecordNumber);
    }

    synchronized long addNewProcedureCommand(DaoManager manager, Procedure procedure) throws SQLException{
        long procedureId= manager.getProcedureDao().save(procedure);

        if (procedureId < 0) {
            //There is such a procedure in the DB
            return procedureId;
        } else {
            procedure.setId(procedureId);
        }

        return procedureId;
    }

    synchronized boolean updateProcedureDataCommand(DaoManager manager, Procedure procedure) throws SQLException{

        ProcedureDao procedureDao = manager.getProcedureDao();

        procedureDao.update(procedure);

        return EXECUTING_SUCCESSFUL;
    }
}