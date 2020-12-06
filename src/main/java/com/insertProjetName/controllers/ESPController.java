package com.insertProjetName.controllers;

import com.insertProjetName.api.ESP;
import com.insertProjetName.dao.ESPDao;

import java.util.List;
import java.util.UUID;

public class ESPController {

    private final ESPDao espDao;

    public ESPController(ESPDao espDao) {
        this.espDao = espDao;
    }

    public ESP createNewESP(int userId) {
        return espDao.insertESP(new ESP(UUID.randomUUID().toString(), userId));
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
