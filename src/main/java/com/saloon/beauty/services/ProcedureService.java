package com.saloon.beauty.services;

import com.saloon.beauty.repository.DaoManager;
import com.saloon.beauty.repository.DaoManagerFactory;
import com.saloon.beauty.repository.dao.ProcedureDao;
import com.saloon.beauty.repository.entity.Procedure;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ProcedureService extends Service {

    private DaoManagerFactory daoManagerFactory;

    public ProcedureService(DaoManagerFactory daoManagerFactory) {
        this.daoManagerFactory = daoManagerFactory;
    }

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

    public List<Procedure> getAllProcedure(){
        DaoManager daoManager = daoManagerFactory.createDaoManager();

        Object executionResult = daoManager.executeAndClose(this::getAllProcedureCommand);

        return checkAndCastObjectToList(executionResult);
    }

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

    //Commands which is needed to be executed in corresponding public service methods
    List<Procedure> getAllProcedureCommand(DaoManager manager) throws SQLException {
        return manager.getProcedureDao().getAll();
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
