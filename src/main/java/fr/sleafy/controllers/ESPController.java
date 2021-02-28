package fr.sleafy.controllers;

import de.ahus1.keycloak.dropwizard.User;
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

    public IDSecretKey createNewESP(User user, ESP esp) {
        ESP espInit = new ESP();
        espInit.setName(esp.getName());
        espInit.setUser(user.getName());
        espInit.setUuid(UUID.randomUUID().toString());
        espInit.setSecretKey(UUID.randomUUID().toString());
        ESP espCreated = espDao.insertESP(espInit);
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
