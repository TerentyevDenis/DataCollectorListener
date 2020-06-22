package ru.terentev;

import java.sql.*;

import org.postgresql.*;
import org.postgresql.Driver;
import ru.terentev.Models.Test;

public class DBhelper {

    static String TEST_TABLE = "tests";
    static String RESULTS_TABLE = "results";
    static String TEST_TMS_ID = "tmsId";
    static String TEST_NAME = "name";
    static String TEST_TMS_URL = "tmsURL";
    static String RESULT_ELAPSED_TIME = "elapsedTime";
    static String RESULT_START_TIME = "startTime";
    static String RESULT_STATUS = "status";
    static String RESULT_BUILD_ID = "buildID";
    static String RESULT_BUILD_URL = "buildUrl";
    static String RESULT_ID = "id";
    static String RESULT_TMS_ID = "tmsId";

    static void createTable() {
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(CollectorListenersCapabilities.getdBUrl(), CollectorListenersCapabilities.getdBUser(), CollectorListenersCapabilities.getdBPassword());
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            String createTestTable = "CREATE TABLE IF NOT EXISTS " + TEST_TABLE +
                    "(" + TEST_TMS_ID + " bigint primary key," +
                    TEST_NAME + " text NOT NULL," +
                    TEST_TMS_URL + " text)";
            String createResultTable = "CREATE TABLE IF NOT EXISTS " + RESULTS_TABLE +
                    "(" + RESULT_ID + " bigserial primary key," +
                    RESULT_TMS_ID + " bigint REFERENCES " + TEST_TABLE + "(" + TEST_TMS_ID + ") ON DELETE CASCADE," +
                    RESULT_ELAPSED_TIME + " bigint NOT NULL," +
                    RESULT_STATUS + " text NOT NULL," +
                    RESULT_START_TIME + " TIMESTAMP NOT NULL," +
                    RESULT_BUILD_ID + " text," +
                    RESULT_BUILD_URL + " text)";
            statement.execute(createTestTable);
            statement.execute(createResultTable);
            connection.commit();
            connection.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    static void putTestInDB(Test test) {
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(CollectorListenersCapabilities.getdBUrl(), CollectorListenersCapabilities.getdBUser(), CollectorListenersCapabilities.getdBPassword());
            connection.setAutoCommit(false);
            String getTest = "SELECT * FROM "+TEST_TABLE+" WHERE "+TEST_TMS_ID+"= ?";
            PreparedStatement qwrGetTests = connection.prepareStatement(getTest);
            qwrGetTests.setLong(1,Long.valueOf(test.getTms_id()));
            ResultSet tests = qwrGetTests.executeQuery();
            if (!tests.next()){ //add test
                String createTest = "INSERT INTO "+TEST_TABLE+" ("+TEST_TMS_ID+","+TEST_NAME+","+TEST_TMS_URL+") VALUES (?,?,?)";
                PreparedStatement qwrCreateTest = connection.prepareStatement(createTest);
                qwrCreateTest.setLong(1,Long.valueOf(test.getTms_id()));
                qwrCreateTest.setString(2,test.getTestName());
                qwrCreateTest.setString(3,test.getTms_url());
                qwrCreateTest.execute();
            }else { //update test name if changed
                if (!tests.getString(TEST_NAME).equals(test.getTestName())){
                    String updateTest = "UPDATE "+TEST_TABLE+" SET "+TEST_NAME+"=? WHERE "+TEST_TMS_ID+"="+test.getTms_id();
                    PreparedStatement qwrUpdateTest = connection.prepareStatement(updateTest);
                    qwrUpdateTest.setString(1,test.getTestName());
                    qwrUpdateTest.execute();
                }
            }
            //insert test result
            String insertResult = "INSERT INTO "+RESULTS_TABLE+" ("+RESULT_TMS_ID+","+RESULT_STATUS+", "+RESULT_ELAPSED_TIME+", "+RESULT_START_TIME+", "+RESULT_BUILD_ID+", "+RESULT_BUILD_URL+") VALUES (?,?,?,?,?,?)";
            PreparedStatement qwrInsertResult = connection.prepareStatement(insertResult);
            qwrInsertResult.setLong(1,Long.valueOf(test.getTms_id()));
            qwrInsertResult.setString(2,test.getTestResult().toString());
            qwrInsertResult.setLong(3,test.getElapsedTime());
            qwrInsertResult.setObject(4,test.getStartTime());
            qwrInsertResult.setString(5,test.getBuild_id());
            qwrInsertResult.setString(6,test.getBuild_URL());
            qwrInsertResult.execute();
            connection.commit();
            connection.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
