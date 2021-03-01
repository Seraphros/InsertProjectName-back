package fr.sleafy.controllers;

import fr.sleafy.api.ESP;
import fr.sleafy.api.utils.IDSecretKey;
import fr.sleafy.dao.ESPDao;

import java.util.List;
import java.util.UUID;

public class ESPController {

    private final ESPDao espDao;

    public ESPController(ESPDao espDao) {

        this.espDao = espDao;
    }

    public IDSecretKey createNewESP(ESP espInput, String user) {
        ESP esp = espInput;
        esp.setUser(user);
        ESP espCreated = espDao.insertESP(esp);
        return new IDSecretKey(espCreated.getId(), espCreated.getUuid(), espCreated.getSecretKey());
    }

    public List<ESP> getUsersESP(int userId) {
        return espDao.getESPfromUser(userId);
    }

    public ESP getESPfromUUID(String uuid) {
        return espDao.getESPfromUUID(uuid);
    }

    public ESP changeESPName(String uuid, String name) {
        return espDao.setESPName(uuid, name);
    }
}
