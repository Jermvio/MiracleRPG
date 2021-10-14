package knikita.dao;

import java.lang.reflect.Field;
import java.sql.*;

import knikita.model.Model;
import net.dv8tion.jda.api.entities.User;

public class DatabaseHandler extends Config {
    Connection dbConnection;

    private Connection getDbConnection()
            throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;

        Class.forName("com.mysql.jdbc.Driver");

        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);

        return dbConnection;
    }

    public void insertIntoTable(Model model) {
        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(getInsertQueryForModel(model));

            Field[] fields = model.getClass().getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {

                if (String.class.equals(fields[i].getType())) {
                    preparedStatement.setString(i + 1, (String) fields[i].get(model));
                }
                if (int.class.equals(fields[i].getType())) {
                    preparedStatement.setInt(i + 1, (Integer) fields[i].get(model));
                }
                if (long.class.equals(fields[i].getType())) {
                    preparedStatement.setLong(i + 1, (Long) fields[i].get(model));
                }
                if (boolean.class.equals(fields[i].getType())) {
                    preparedStatement.setBoolean(i + 1, (Boolean) fields[i].get(model));
                }
            }

            preparedStatement.executeUpdate();
            preparedStatement.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private String getInsertQueryForModel(Model model) {
        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO ");
        query.append(model.getClass().getSimpleName());
        query.append("(");
        for (String fieldName : model.getFieldNames()) {
            query.append(fieldName).append(",");
        }
        query.deleteCharAt(query.length() - 1);
        query.append(") VALUES(");
        query.append("?,".repeat(model.getFieldsCount()));
        query.deleteCharAt(query.length() - 1);
        query.append(")");
        return query.toString();
    }

    public ResultSet selectFromTable(Model model) {

        ResultSet resultSet = null;
        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(getSelectQueryForModel(model));

            int counter = 1;
            for (Field field : model.getClass().getDeclaredFields()) {

                if (!model.fieldNotNull(field.getName()))
                    continue;

                if (String.class.equals(field.getType())) {
                    preparedStatement.setString(counter, (String) field.get(model));
                }
                if (int.class.equals(field.getType())) {
                    preparedStatement.setInt(counter, (Integer) field.get(model));
                }
                if (long.class.equals(field.getType())) {
                    preparedStatement.setLong(counter, (Long) field.get(model));
                }
                if (boolean.class.equals(field.getType())) {
                    preparedStatement.setBoolean(counter, (Boolean) field.get(model));
                }
                counter++;
            }
            resultSet = preparedStatement.executeQuery();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    private String getSelectQueryForModel(Model model) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM ");
        query.append(model.getClass().getSimpleName());
        query.append(" WHERE ");

        for (String fieldName : model.getFieldNames()) {
            if (model.fieldNotNull(fieldName)) {
                query.append(fieldName).append(" =? AND ");
            }

        }

        query.delete(query.length() - 5, query.length() - 1);
        return query.toString();
    }

    public void addHero(User user) {
        String query = "INSERT INTO " + Const.HEROES_TABLE + "(" + Const.USER_ID + ")" + "VALUES(?)";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(query);
            prSt.setLong(1, user.getIdLong());

            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public ResultSet getHero(User user) {
        ResultSet resultSet = null;

        String query = "SELECT * FROM " + Const.HEROES_TABLE + " WHERE " + Const.USER_ID + "=?";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(query);
            prSt.setLong(1, user.getIdLong());

            resultSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return resultSet;
    }

    public ResultSet getItemById(int itemID) {
        ResultSet resultSet = null;

        String query = "SELECT * FROM " + Const.ITEM_TABLE + " WHERE " + Const.ITEM_ID + "=?";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(query);
            prSt.setInt(1, itemID);

            resultSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return resultSet;
    }

    public void setCommandCoolDown(User user, String commandName, long coolDownLastTime) {
        String query = "INSERT INTO " + Const.COMMANDS_TABLE + "(" + Const.COMMANDS_USER_ID + "," + Const.COMMAND_NAME + "," +
                Const.COMMAND_COOLDOWN + ")" + " VALUES(?,?,?)";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(query);

            prSt.setLong(1, user.getIdLong());
            prSt.setString(2, commandName);
            prSt.setLong(3, coolDownLastTime);
            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public void updateCommandCoolDown(User user, String commandName, long coolDownLastTime) {
        String query = "UPDATE " + Const.COMMANDS_TABLE + " SET " + Const.COMMAND_COOLDOWN + " =?" +
                " WHERE " + Const.COMMANDS_USER_ID + " =?" + " AND " + Const.COMMAND_NAME + " =?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(query);

            prSt.setLong(1, coolDownLastTime);
            prSt.setLong(2, user.getIdLong());
            prSt.setString(3, commandName);
            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public long getCommandLastUse(User user, String commandName) {
        ResultSet resultSet;
        long result = -1;

        String query = "SELECT * FROM " + Const.COMMANDS_TABLE + " WHERE " + Const.COMMANDS_USER_ID + " =?" +
                " AND " + Const.COMMAND_NAME + " =?";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(query);

            prSt.setLong(1, user.getIdLong());
            prSt.setString(2, commandName);

            resultSet = prSt.executeQuery();

            if (resultSet.next()) {
                result = resultSet.getLong(3);
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return result;
    }

    private String getEquipPartName(int equip_id) {
        ResultSet itemResultSet = getItemById(equip_id);
        try {
            itemResultSet.next();
            return itemResultSet.getString(7) + "_id";
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
