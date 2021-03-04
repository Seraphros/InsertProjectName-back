package fr.sleafy.controllers;

import fr.sleafy.api.UserInformation;
import fr.sleafy.dao.UserDao;
import lombok.extern.slf4j.Slf4j;

import java.sql.ResultSet;

@Slf4j
public class UserController {

    private UserDao userDao;

    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }

    public UserInformation getUserInformation(String user){
        UserInformation information = userDao.getUserInformation(user);
        return information;
    }

    public UserInformation saveUserCity(String user, String city) {
        UserInformation information  = userDao.getUserInformation(user);
        if(information != null){
            userDao.updateCityForUser(user, city);
            return userDao.getUserInformation(user);
        }
        else{
            userDao.addCityForUser(user, city);
            return userDao.getUserInformation(user);
        }
    }
}
