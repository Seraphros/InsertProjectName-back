package fr.sleafy.services;

import fr.sleafy.api.utils.Database;
import fr.sleafy.api.utils.StmtParams;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.List;

@Slf4j
public class DBService {

    private final Database database;
    private Connection connection;

    public DBService(Database database) {
        this.database = database;

        try {
            // Load Driver
            Class.forName("org.mariadb.jdbc.Driver");
            log.info("[DATABASE] - Connecting to database...");
            connection = DriverManager.getConnection(database.getUrl(), database.getUser(), database.getPassword());
            log.info("[DATABASE] - Creating Test Statement...");
            Statement stmt = connection.createStatement();
            log.info("[DATABASE] - Database Initialized !");

        } catch (Exception e) {
            System.out.println("error during db initialization : " + e);
        }
    }

    private void keepAlive() {
        try {
            Statement stmt = connection.createStatement();
            stmt.execute("SELECT 1");
        } catch (Exception e) {
            try {
                connection = DriverManager.getConnection(database.getUrl(), database.getUser(), database.getPassword());
            } catch (Exception e2) {
                log.error("Unable to keep database alive :" + e2);
            }
        }
    }

    public ResultSet executeQuery(String sql, List<StmtParams> params) {
        this.keepAlive();

        try {
            PreparedStatement stmt = prepareStatement(sql, params);
            try {
                return stmt.executeQuery();
            } catch (Exception e) {
                log.error("Unable to execute query : " + e);
                return null;
            }
        } catch ( Exception e) {
            log.error("Unable to prepare statement : " + e);
            return null;
        }
    }

    public int insertQuery(String sql, List<StmtParams> params) {
        this.keepAlive();

        try {
            PreparedStatement stmt = prepareStatement(sql, params);
            try {
                int affectedRows = stmt.executeUpdate();

                if (affectedRows == 0) {
                    throw new SQLException("Create failed, no rows affected");
                }

                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);
                    }
                    else {
                        return 0;
                    }
                }
            } catch (Exception e) {
                log.error("Unable to execute query : " + e);
                return 0;
            }
        } catch ( Exception e) {
            log.error("Unable to prepare statement : " + e);
            return 0;
        }
    }

    private PreparedStatement prepareStatement(String sql, List<StmtParams> params) throws Exception{
        PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        for (StmtParams param : params) {
            switch (param.getType()) {
                case "string":
                    stmt.setString(param.getId(), param.getStringValue());
                    break;
                case "int":
                    stmt.setInt(param.getId(), param.getIntValue());
                    break;
                case "float":
                    stmt.setFloat(param.getId(), param.getFloatValue());
                    break;
                case "boolean":
                    stmt.setBoolean(param.getId(), param.isBoolValue());
                default:
                    break;
            }
        }

        return stmt;
    }
}
