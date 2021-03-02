package fr.sleafy.dao;

import fr.sleafy.api.ESP;
import fr.sleafy.api.utils.IDSecretKey;
import fr.sleafy.api.utils.StmtParams;
import fr.sleafy.services.DBService;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.GenericDeclaration;
import java.security.SecureRandom;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
public class ESPDao {

    private final DBService dbService;

    public ESPDao(DBService dbService) {
        this.dbService = dbService;
    }

    public ESP insertESP(ESP esp) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedKey = IDSecretKey.get_SHA_512_SecurePassword(UUID.randomUUID().toString());

        String insertESPQuery = "INSERT INTO esp (id, user, uuid, secretKey, name, humi_sensor, heat_sensor, hygrometry, watering, watering_frequency, watering_duration, sleep_time) " +
                "VALUES (NULL, ?, ?, ?, ?, ? , ? , ? , ? , ?, ? , ?)";
        List<StmtParams> paramsList = new ArrayList<>();
        paramsList.add(new StmtParams(1, esp.getUser()));

        paramsList.add(new StmtParams(3, encodedKey));
        paramsList.add(new StmtParams(4, esp.getName()));
        paramsList.add(new StmtParams(5, esp.getHumiditySensor()));
        paramsList.add(new StmtParams(6, esp.getHeatSensor()));
        paramsList.add(new StmtParams(7, esp.getHygrometry()));
        paramsList.add(new StmtParams(8, esp.getWatering()));
        paramsList.add(new StmtParams(9, esp.getWateringFrequency()));
        paramsList.add(new StmtParams(10, esp.getWateringDuration()));
        paramsList.add(new StmtParams(11, esp.getSleepTime()));
        String uuid =  UUID.randomUUID().toString();
        paramsList.add(new StmtParams(2, uuid));
        int idGenerated = dbService.insertQuery(insertESPQuery, paramsList);

        if (idGenerated != 0) {
            esp.setId(idGenerated);
            return getESPfromUUID(uuid);
        }

        return null;
    }

    public List<ESP> getESPfromUser(String user) {
        List<ESP> espList = new ArrayList<>();
        String getUsersESPQuery = "SELECT * from esp WHERE user = ?";
        List<StmtParams> paramsList = new ArrayList<>();
        paramsList.add(new StmtParams(1, user));
        ResultSet set = dbService.executeQuery(getUsersESPQuery, paramsList);
        try {
            while (set.next()) {
                espList.add(buildESPfromResultSet(set));
            }
        } catch (Exception e) {
            log.error("Unable to read resultset :" + e);
            return null;
        }
        return espList;
    }

    public ESP getESPfromUUID(String uuid) {
        String getUsersESPQuery = "SELECT * from esp WHERE uuid = ?";
        List<StmtParams> paramsList = new ArrayList<>();
        paramsList.add(new StmtParams(1, uuid));
        ResultSet set = dbService.executeQuery(getUsersESPQuery, paramsList);
        try {
            if (set.next()) {
                return buildESPfromResultSet(set);
            }
        } catch (Exception e) {
            log.error("Unable to read resultset :" + e);
        }

        return null;
    }

    public ESP setESPName(String uuid, String name) {
        ESP espToChange = getESPfromUUID(uuid);
        String setNameESPQuery = "UPDATE esp SET name = ? WHERE esp.id = ?";
        List<StmtParams> paramsList = new ArrayList<>();
        paramsList.add(new StmtParams(1, name));
        paramsList.add(new StmtParams(2, espToChange.getId()));
        dbService.executeQuery(setNameESPQuery, paramsList);
        return getESPfromUUID(uuid);
    }

    private ESP buildESPfromResultSet(ResultSet set) throws Exception {
        ESP esp = new ESP();
        esp.setId(set.getInt("id"));
        esp.setUser(set.getString("user"));
        esp.setUuid(set.getString("uuid"));
        esp.setSecretKey(set.getString("secretKey"));
        esp.setName(set.getString("name"));
        esp.setHumiditySensor(set.getBoolean("humi_sensor"));
        esp.setHeatSensor(set.getBoolean("heat_sensor"));
        esp.setHygrometry(set.getBoolean("hygrometry"));
        esp.setWatering(set.getBoolean("watering"));
        esp.setWateringFrequency(set.getInt("watering_frequency"));
        esp.setWateringDuration(set.getInt("watering_duration"));
        esp.setSleepTime(set.getInt("sleep_time"));
        return esp;
    }
}
