package fr.sleafy.dao;

import fr.sleafy.api.ESP;
import fr.sleafy.api.utils.IDSecretKey;
import fr.sleafy.api.utils.StmtParams;
import fr.sleafy.services.DBService;
import lombok.extern.slf4j.Slf4j;

import java.security.SecureRandom;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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
        String encodedKey = IDSecretKey.get_SHA_512_SecurePassword(esp.getSecretKey());

        String insertESPQuery = "INSERT INTO esp (id, idUser, uuid, name, secretKey) VALUES (NULL, ?, ?, ?, ?)";
        List<StmtParams> paramsList = new ArrayList<>();
        paramsList.add(new StmtParams(1, esp.getUser()));
        paramsList.add(new StmtParams(2, esp.getUuid()));
        paramsList.add(new StmtParams(3, esp.getName()));
        paramsList.add(new StmtParams(4, encodedKey));
        int idGenerated = dbService.insertQuery(insertESPQuery, paramsList);

        if (idGenerated != 0) {
            esp.setId(idGenerated);
        }

        return esp;
    }

    public List<ESP> getESPfromUser(int userID) {
        List<ESP> espList = new ArrayList<>();
        String getUsersESPQuery = "SELECT * from esp WHERE idUser = ?";
        List<StmtParams> paramsList = new ArrayList<>();
        paramsList.add(new StmtParams(1, userID));
        ResultSet set = dbService.executeQuery(getUsersESPQuery, paramsList);
        try {
            while (set.next()) {
                espList.add(buildESPfromResultSet(set));
            }
        } catch (Exception e) {
            log.error("Unable to read resultset :" + e);
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
        return new ESP(set.getInt("id"), set.getString("uuid"), set.getString("secretKey"), set.getString("user"), set.getString("name"));
    }
}
