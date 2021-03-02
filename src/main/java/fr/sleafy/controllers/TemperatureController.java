package fr.sleafy.controllers;

import fr.sleafy.api.ESP;
import fr.sleafy.api.Temperature;
import fr.sleafy.dao.ESPDao;
import fr.sleafy.dao.TemperatureDao;

import javax.annotation.Nullable;
import java.util.List;

public class TemperatureController {

    private final ESPDao espDao;
    private final TemperatureDao temperatureDao;

    public TemperatureController(ESPDao espDao,
                                 TemperatureDao temperatureDao) {
        this.espDao = espDao;
        this.temperatureDao = temperatureDao;
    }

    public Temperature storeTemperatureReadingFromUUID(Temperature temperature) {
        ESP associatedESP = espDao.getESPfromUUID(temperature.getEspUUID());
        temperature.setEspId(associatedESP.getId());
        return temperatureDao.insertReading(temperature);
    }

    public List<Temperature> getTemperatureValues(@Nullable Integer size, Integer esp) {
        List<Temperature> humidities = temperatureDao.getLastTemperaturesValues(size != null ? size: 1, esp);
        return humidities;
    }
}
