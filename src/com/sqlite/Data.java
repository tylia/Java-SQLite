package com.sqlite;
import java.sql.*;

public class Data {
    public static String DB_NAME = "test.db";
    public static final String CONNECTION_STRING = "jdbc:sqlite:C:\\" + DB_NAME;

    public static final String TABLE_TEST = "test_database";
    public static final String TEST_ID = "id";
    public static final String TEST_NAME = "name";
    public static final String TEST_NUMBER = "number";

    public static final String QUERY_TABLE = "SELECT * FROM " + TABLE_TEST;

    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_TEST + " " +
            " (" + TEST_ID + " integer PRIMARY KEY, " +
                   TEST_NAME + " text, " +
                   TEST_NUMBER + " integer" +
            " )";

    public static final String INSERT_DATA = "INSERT INTO " + TABLE_TEST +
            " (" + TEST_NAME + ", " + TEST_NUMBER + ") VALUES (?, ?)";

    public static final String UPDATE_DATA = "UPDATE " + TABLE_TEST + " SET " + TEST_NAME + " =?, " +
            TEST_NUMBER + " = ? WHERE " + TEST_ID + " = ?";

    public static final String DELETE_DATA = "DELETE FROM " + TABLE_TEST + "  WHERE " + TEST_ID + " =?";

    private Connection con;
    private PreparedStatement insertIntoDB;
    private PreparedStatement updateDB;
    private PreparedStatement deleteDB;

    public Data() {
    }


    public boolean open() {
        try {
            con = DriverManager.getConnection(CONNECTION_STRING);
            Statement statement = con.createStatement();
            statement.execute(CREATE_TABLE);
            insertIntoDB = con.prepareStatement(INSERT_DATA);
            updateDB = con.prepareStatement(UPDATE_DATA);
            deleteDB = con.prepareStatement(DELETE_DATA);

            return true;
        } catch (SQLException e) {
            System.out.println("Couldn't connect to database " + e.getMessage());
            return false;
        }
    }

    public void close() {
        try {
            if (insertIntoDB != null) {
                insertIntoDB.close();
            }
            if (updateDB != null) {
                updateDB.close();
            }
            if (deleteDB != null) {
                deleteDB.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            System.out.println("Couldn't close database " + e.getMessage());
        }
    }

    public void queryAllData() {

        try (Statement statement = con.createStatement();
             ResultSet results = statement.executeQuery(QUERY_TABLE)){
            while (results.next()) {
                System.out.println(results.getInt(TEST_ID) + " "
                                +  results.getString(TEST_NAME) + " "
                                +  results.getInt(TEST_NUMBER));
            }
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        }
    }

    public void insert(String name, int number) throws SQLException {
        insertIntoDB.setString(1, name);
        insertIntoDB.setInt(2, number);

        insertIntoDB.executeUpdate();

//        ResultSet results = insertIntoDB.executeQuery();
    }

    public void update(String name, int number, int id) throws SQLException {
        updateDB.setString(1, name);
        updateDB.setInt(2, number);
        updateDB.setInt(3, id);

        updateDB.executeUpdate();
    }

    public void delete(int id) throws SQLException {
        deleteDB.setInt(1, id);

        deleteDB.executeUpdate();
    }

}
