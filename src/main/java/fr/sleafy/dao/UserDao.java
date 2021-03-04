package fr.sleafy.dao;

import fr.sleafy.api.UserInformation;
import fr.sleafy.api.utils.StmtParams;
import fr.sleafy.services.DBService;
import lombok.extern.slf4j.Slf4j;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class UserDao {

    private DBService dbService;

    public UserDao(DBService dbService) {
        this.dbService = dbService;
    }

    public UserInformation getUserInformation(String user){
        String getQuery = "SELECT * FROM user_information WHERE name = ?";
        List<StmtParams> paramsList = new ArrayList<>();
        paramsList.add(new StmtParams(1, user));
        try {
            ResultSet resultSet = dbService.executeQuery(getQuery, paramsList);
            while(resultSet.next()){
                UserInformation userInformation = retrieveInformation(resultSet);
                return userInformation;

            }
        } catch (SQLException e) {
            log.error("An Error occured during reception of the user information");
        }
        return null;
    }



    public UserInformation retrieveInformation(ResultSet resultSet) throws SQLException {
        UserInformation userInformation = new UserInformation();
        userInformation.setName(resultSet.getString("name"));
        userInformation.setCity(resultSet.getString("city"));
        return userInformation;
    }

    public void updateCityForUser(String user, String city) {
        String updateQuery = "UPDATE user_information " +
                "SET city = ? " +
                "WHERE name = ? ";
        List<StmtParams> paramsList = new ArrayList<>();
        paramsList.add(new StmtParams(1, city));
        paramsList.add(new StmtParams(2, user));
        dbService.executeQuery(updateQuery, paramsList);
    }

    public void addCityForUser(String user, String city) {
        String insertQuery = "INSERT INTO user_information (name, city) VALUES (?, ?)";
        List<StmtParams> paramsList = new ArrayList<>();
        paramsList.add(new StmtParams(1, user));
        paramsList.add(new StmtParams(2, city));
        dbService.executeQuery(insertQuery, paramsList);
    }
}
