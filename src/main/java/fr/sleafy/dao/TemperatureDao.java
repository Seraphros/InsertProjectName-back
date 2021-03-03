package fr.sleafy.dao;

import fr.sleafy.api.Temperature;
import fr.sleafy.api.utils.StmtParams;
import fr.sleafy.services.DBService;
import lombok.extern.slf4j.Slf4j;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class TemperatureDao {

    private final DBService dbService;

    public TemperatureDao(DBService dbService) {
        this.dbService = dbService;
    }

    public Temperature insertReading(Temperature temperature) {
        String insertESPQuery = "INSERT INTO temperature (id, espId, value, time) VALUES (NULL, ?, ?, NOW())";
        List<StmtParams> paramsList = new ArrayList<>();
        paramsList.add(new StmtParams(1, temperature.getEspId()));
        paramsList.add(new StmtParams(2, temperature.getValue()));
        int idGenerated = dbService.insertQuery(insertESPQuery, paramsList);

        if (idGenerated != 0) {
            temperature.setId(idGenerated);
        }

        return temperature;
    }

    public List<Temperature> getLastTemperaturesValues(Integer size, Integer esp) {

        String getQuery = "SELECT h.id as id, h.espId as espId, h.value as value, h.time as time, e.uuid as uuid FROM temperature h " +
                "INNER JOIN esp e ON h.espId = e.id  " +
                "WHERE h.espId = ? " +
                "ORDER BY h.time DESC " +
                "LIMIT ?";
        List<StmtParams> paramsList = new ArrayList<>();
        paramsList.add(new StmtParams(1, esp));
        paramsList.add(new StmtParams(2, size));
        ResultSet result = dbService.executeQuery(getQuery, paramsList);
        List<Temperature> temperatures = new ArrayList<>();
        try{
            while (result.next()){
                Temperature temperature = new Temperature();
                temperature.setEspId(result.getInt("espId"));
                temperature.setValue(result.getFloat("value"));
                temperature.setId(result.getInt("id"));
                temperature.setTime(result.getTimestamp("time"));
                temperature.setEspUUID(result.getString("uuid"));
                temperatures.add(temperature);
            }
        } catch (SQLException throwables) {
            log.error("An exception occured during the request : {}", throwables.getMessage());
            return null;
        }
        return temperatures;
    }

}
