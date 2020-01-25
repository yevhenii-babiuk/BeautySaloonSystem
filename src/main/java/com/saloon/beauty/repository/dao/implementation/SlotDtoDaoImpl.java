package com.saloon.beauty.repository.dao.implementation;

import com.saloon.beauty.repository.DaoException;
import com.saloon.beauty.repository.dao.SlotDtoDao;
import com.saloon.beauty.repository.dao.UserDao;
import com.saloon.beauty.repository.dto.SlotDto;
import com.saloon.beauty.repository.entity.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public class SlotDtoDaoImpl implements SlotDtoDao {

    private static final Logger LOG = LogManager.getLogger(UserDao.class);

    private Connection connection;

    public SlotDtoDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<SlotDto> getFullInformation(long id) {
        Optional<SlotDto> resultOptional = Optional.empty();
        try {
            PreparedStatement statement = connection.prepareStatement(DBQueries.GET_FULL_INFORMATION_ABOUT_SLOT_QUERY);
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                resultOptional = Optional.of(getSlotFromResultRow(resultSet));
            }

            resultSet.close();

        } catch (SQLException e) {
            String errorText = String.format("Can't get full information about slot by id: %s. Cause: %s.", id, e.getMessage());
            LOG.error(errorText, e);
            throw new DaoException(errorText, e);
        }
        return resultOptional;
    }

    /**
     * Creates an slot from given {@code ResultSet}
     *
     * @param resultSet - {@code ResultSet} with slot data
     * @return - slot was passed into
     * @throws SQLException if the columnLabels is not valid;
     *                      if a database access error occurs or result set is closed
     */
    SlotDto getSlotFromResultRow(ResultSet resultSet) throws SQLException {
        Slot slot = Slot.builder()
                .id(resultSet.getLong("slot_id"))
                .master(resultSet.getLong("slot_master"))
                .procedure(resultSet.getLong("slot_procedure"))
                .date(resultSet.getObject("slot_date", LocalDate.class))
                .startTime(resultSet.getObject("start_time", LocalTime.class))
                .endTime(resultSet.getObject("end_time", LocalTime.class))
                .status(Status.valueOf(resultSet.getString("status")))
                .feedbackRequest(resultSet.getBoolean("feedback_request"))
                .build();

        Feedback feedback = Feedback.builder()
                .text(resultSet.getString("feedback_text"))
                .build();

        User master = User.builder()
                .email(resultSet.getString("master_email"))
                .phone(resultSet.getString("master_phone"))
                .firstName(resultSet.getString("master_first_name"))
                .lastName(resultSet.getString("master_last_name"))
                .build();

        User client = User.builder()
                .email(resultSet.getString("user_email"))
                .phone(resultSet.getString("user_phone"))
                .firstName(resultSet.getString("user_first_name"))
                .lastName(resultSet.getString("user_last_name"))
                .build();

        Procedure procedure = Procedure.builder()
                .nameUkr(resultSet.getString("name_ukr"))
                .descriptionUkr(resultSet.getString("description_ukr"))
                .nameEn(resultSet.getString("name_en"))
                .descriptionEn(resultSet.getString("description_en"))
                .nameRus(resultSet.getString("name_rus"))
                .descriptionRus(resultSet.getString("description_rus"))
                .price(resultSet.getInt("procedure_price"))
                .build();

        return SlotDto.builder()
                .slot(slot)
                .client(client)
                .master(master)
                .procedure(procedure)
                .feedback(feedback)
                .build();
    }
}
