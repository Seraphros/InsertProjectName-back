package fr.sleafy.controllers;

import fr.sleafy.api.ESP;
import fr.sleafy.api.Humidity;
import fr.sleafy.dao.ESPDao;
import fr.sleafy.dao.HumidityDao;

import javax.annotation.Nullable;
import java.util.List;

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

    public List<Humidity> getHumidityvalues(@Nullable Long size) {
        List<Humidity> humidities = humidityDao.getLastHumiditiesValues(size != null ? size: 1);
        return humidities;
    }
}
