package fr.sleafy.controllers;

import fr.sleafy.api.ESP;
import fr.sleafy.api.Humidity;
import fr.sleafy.dao.ESPDao;
import fr.sleafy.dao.HumidityDao;

public class HumidityController {

    private final ESPDao espDao;
    private final HumidityDao humidityDao;

    public HumidityController(ESPDao espDao,
                              HumidityDao humidityDao) {
        this.espDao = espDao;
        this.humidityDao = humidityDao;
    }

    public void storeHumidityReadingFromUUID(Humidity humidity) {
        ESP associatedESP = espDao.getESPfromUUID(humidity.getEspUUID());
        humidity.setEspId(associatedESP.getId());
        humidityDao.insertReading(humidity);
    }
}