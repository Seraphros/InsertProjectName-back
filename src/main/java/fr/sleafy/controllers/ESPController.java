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
        if(espCreated ==  null){
            return null;
        }
        return new IDSecretKey(espCreated.getId(), espCreated.getUuid(), espCreated.getSecretKey());
    }

    public List<ESP> getUsersESP(String user) {
        return espDao.getESPfromUser(user);
    }

    public ESP getESPfromUUID(String uuid) {
        return espDao.getESPfromUUID(uuid);
    }

    public ESP changeESPName(String uuid, String name) {
        return espDao.setESPName(uuid, name);
    }

    public Boolean updateEsp(ESP espInput, String user) {
        ESP espFound = espDao.getESPfromUUID(espInput.getUuid());
        if(espFound != null){
            if(espFound.getUser().equals(user)){
                espInput.setUser(user);
                return espDao.updateEsp(espInput);
            }
        }
        return null;
    }

    public Boolean deleteEsp(Integer id, String user) {
        ESP espFound = espDao.getEspFromId(id);
        if(espFound != null){
            if(espFound.getUser().equals(user)){
                espDao.deleteEspFromId(id);
                return true;
            }
        }
        return false;
    }

    public IDSecretKey resetSecretKey(Integer id, String user) {
        ESP espFound = espDao.getEspFromId(id);
        if(espFound != null) {
            if(espFound.getUser().equals(user)){
               ESP espAfter = espDao.updateSecretKeyForEsp(espFound);
               return new IDSecretKey(espAfter.getId(), espAfter.getUuid(), espAfter.getSecretKey());
            }
        }
        return null;
    }
}
