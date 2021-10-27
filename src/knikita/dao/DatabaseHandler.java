package knikita.dao;

import java.lang.reflect.Field;
import java.sql.*;

import knikita.model.Model;
import knikita.model.items;
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

            setPreparedStatements(model, preparedStatement);

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

        Field[] fields = model.getClass().getDeclaredFields();
        String[] fieldNames = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            fieldNames[i] = fields[i].getName();
        }
        for (String fieldName : fieldNames) {
            query.append(fieldName).append(",");
        }
        query.deleteCharAt(query.length() - 1);
        query.append(") VALUES(");
        query.append("?,".repeat(fields.length));
        query.deleteCharAt(query.length() - 1);
        query.append(")");
        return query.toString();
    }

    public ResultSet selectFromTable(Model model) {

        ResultSet resultSet = null;
        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(getSelectQueryForModel(model));

            setPreparedStatements(model, preparedStatement);
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

    private void setPreparedStatements(Model model, PreparedStatement preparedStatement) throws SQLException, IllegalAccessException {
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
    }

    private String getSelectQueryForModel(Model model) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM ");
        query.append(model.getClass().getSimpleName());
        query.append(" WHERE ");

        Field[] fields = model.getClass().getDeclaredFields();
        String[] fieldNames = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            fieldNames[i] = fields[i].getName();
        }

        for (String fieldName : fieldNames) {
            if (model.fieldNotNull(fieldName)) {
                query.append(fieldName).append(" = ? AND ");
            }

        }

        query.delete(query.length() - 5, query.length() - 1);
        return query.toString();
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
        ResultSet itemResultSet = selectFromTable(new items().setItem_id(equip_id));
        try {
            itemResultSet.next();
            return itemResultSet.getString(7) + "_id";
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
