package fr.sleafy.dao;

import fr.sleafy.api.Humidity;
import fr.sleafy.api.utils.StmtParams;
import fr.sleafy.services.DBService;
import lombok.extern.slf4j.Slf4j;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
public class HumidityDao {

    private final DBService dbService;

    public HumidityDao(DBService dbService) {
        this.dbService = dbService;
    }

    public Humidity insertReading(Humidity humidity) {
        String insertESPQuery = "INSERT INTO humidity (id, espId, value, time) VALUES (NULL, ?, ?, NOW())";
        List<StmtParams> paramsList = new ArrayList<>();
        paramsList.add(new StmtParams(1, humidity.getEspId()));
        paramsList.add(new StmtParams(2, humidity.getValue()));
        int idGenerated = dbService.insertQuery(insertESPQuery, paramsList);

        if (idGenerated != 0) {
            humidity.setId(idGenerated);
        }

        return humidity;
    }

    public List<Humidity> getLastHumiditiesValues(Integer size, Integer esp) {

        String getQuery = "SELECT h.id as id, h.espId as espId, h.value as value, h.time as time, e.uuid as uuid FROM humidity h " +
                "INNER JOIN esp e ON h.espId = e.id  " +
                "WHERE h.espId = ? " +
                "ORDER BY h.time DESC " +
                "LIMIT ?";
        List<StmtParams> paramsList = new ArrayList<>();
        paramsList.add(new StmtParams(1, esp));
        paramsList.add(new StmtParams(2, size));
        ResultSet result = dbService.executeQuery(getQuery, paramsList);
        List<Humidity> humidities = new ArrayList<>();
        try{
            while (result.next()){
                Humidity humidity = new Humidity();
                humidity.setEspId(result.getInt("espId"));
                humidity.setValue(result.getFloat("value"));
                humidity.setId(result.getInt("id"));
                humidity.setTime(result.getDate("time"));
                humidity.setEspUUID(result.getString("uuid"));
                humidities.add(humidity);
            }
        } catch (SQLException throwables) {
            log.error("An exception occured during the request : {}", throwables.getMessage());
            return null;
        }
        return humidities;
    }

}
