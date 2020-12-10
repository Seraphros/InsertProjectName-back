package fr.sleafy.dao;

import fr.sleafy.api.Humidity;
import fr.sleafy.api.utils.StmtParams;
import fr.sleafy.services.DBService;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
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

}
