package com.saloon.beauty.services;

import com.saloon.beauty.repository.DaoManager;
import com.saloon.beauty.repository.DaoManagerFactory;
import com.saloon.beauty.repository.dao.ProcedureDao;
import com.saloon.beauty.repository.entity.Procedure;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProcedureServiceTest {

    @Mock
    ProcedureService mockService;

    @Mock
    DaoManager mockDaoManager;

    @Mock
    ProcedureDao mockProcedureDao;

    @Mock
    Procedure mockProcedure;


    @Before
    public void setUp() throws Exception {
        mockService = spy(new ProcedureService(new DaoManagerFactory()));
        when(mockDaoManager.getProcedureDao()).thenReturn(mockProcedureDao);
    }

    @Test
    public void addNewProcedureCommandShouldReturnPositiveId() throws SQLException {
        final long TEST_PROCEDURE_ID = 3L;

        when(mockProcedureDao.save(any(Procedure.class))).thenReturn(TEST_PROCEDURE_ID);

        long result = mockService.addNewProcedureCommand(mockDaoManager, mockProcedure);
        verify(mockProcedureDao).save(mockProcedure);
        assertEquals(result, TEST_PROCEDURE_ID);
    }

    @Test
    public void addNewProcedureCommandShouldReturnNegativeResult() throws SQLException {
        final long TEST_PROCEDURE_ID = -1L;

        when(mockProcedureDao.save(any(Procedure.class))).thenReturn(TEST_PROCEDURE_ID);

        long result = mockService.addNewProcedureCommand(mockDaoManager, mockProcedure);
        verify(mockProcedureDao).save(mockProcedure);
        assertEquals(result, TEST_PROCEDURE_ID);
    }

    @Test
    public void updateProcedureDataCommandShouldReturnTrue() throws SQLException {
        doNothing().when(mockProcedureDao).update(any(Procedure.class));

        boolean result = mockService.updateProcedureDataCommand(mockDaoManager, mockProcedure);

        verify(mockProcedureDao).update(mockProcedure);
        assertTrue(result);
    }

    @Test
    public void getAllProcedureCommandShouldReturnListOfProcedure() throws SQLException {
        List<Procedure> procedures = List.of(Procedure.builder().id(1L).build(), Procedure.builder().id(2L).build());
        when(mockProcedureDao.getAll()).thenReturn(procedures);

        List<Procedure> result = mockService.getAllProcedureCommand(mockDaoManager);
        verify(mockProcedureDao).getAll();

        assertEquals(procedures, result);
    }

    @Test
    public void getProcedureParametrizedCommandShouldReturnListOfProcedureAndReturnFalse() throws SQLException {
        List<Procedure> procedures = List.of(Procedure.builder().id(1L).build(), Procedure.builder().id(2L).build());
        when(mockProcedureDao.getAllProcedureParametrized(anyInt(), anyInt())).thenReturn(procedures);
        when(mockProcedureDao.getAllProcedureParametrized(1,0)).thenReturn(List.of(Procedure.builder().id(1L).build()));

        List<Procedure> result = mockService.getProcedureParametrizedCommand(mockDaoManager,1,0);
        verify(mockProcedureDao).getAllProcedureParametrized(1,0);

        assertNotEquals(procedures, result);
    }

    @Test
    public void getProcedureParametrizedCommandShouldReturnListOfProcedureAndReturnTrue() throws SQLException {
        List<Procedure> procedures = List.of(Procedure.builder().id(1L).build(), Procedure.builder().id(2L).build());
        when(mockProcedureDao.getAllProcedureParametrized(anyInt(), anyInt())).thenReturn(procedures);

        List<Procedure> result = mockService.getProcedureParametrizedCommand(mockDaoManager, 2,0);

        assertEquals(procedures, result);
    }

}