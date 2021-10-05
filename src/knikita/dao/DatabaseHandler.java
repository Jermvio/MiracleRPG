package knikita.dao;

import java.sql.*;

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

    public void addHero(User user) {
        String query = "INSERT INTO " + Const.HEROES_TABLE + "(" + Const.USER_ID + ")" + "VALUES(?)";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(query);
            prSt.setLong(1, user.getIdLong());

            prSt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getHero(User user) {
        ResultSet resultSet = null;

        String query = "SELECT * FROM " + Const.HEROES_TABLE + " WHERE " + Const.USER_ID + "=?";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(query);
            prSt.setLong(1, user.getIdLong());

            resultSet = prSt.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    public void addItem(User owner, int itemId) {

        String query = "INSERT INTO " + Const.INVENTORY_TABLE + "(" + Const.USER_ID + "," + Const.ITEM_ID + ")" + "VALUES(?,?)";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(query);
            prSt.setLong(1, owner.getIdLong());
            prSt.setInt(2, itemId);

            prSt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getHeroItemsId(User user) {
        ResultSet resultSet = null;

        String query = "SELECT * FROM " + Const.INVENTORY_TABLE + " WHERE " + Const.USER_ID + "=?";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(query);
            prSt.setLong(1, user.getIdLong());

            resultSet = prSt.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
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
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
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
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
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
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public long getCommandLastUse(User user, String commandName) {
        ResultSet resultSet = null;
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
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return result;
    }
}
