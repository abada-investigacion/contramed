/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.farmatools.domain;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import org.apache.commons.logging.Log;

/**
 *
 * @author aroldan
 */
public class SQLUtils{

    /**
     * Execute several SQL statements but don't return any ResultSet (INSERT,UDPATE,DELETE)
     * @param SQLQuerys
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws IOException
     */
    public static void execute(Connection connection, String... SQLQuerys) throws SQLException, ClassNotFoundException, IOException {
        connection.setAutoCommit(false);
        Statement statement = connection.createStatement();
        if (connection.getMetaData().supportsBatchUpdates()) {
            for (String SQLQuery : SQLQuerys) {
                statement.addBatch(SQLQuery);
            }
            statement.executeBatch();
        } else {
            for (String SQLQuery : SQLQuerys) {
                statement.executeUpdate(SQLQuery);
            }
        }
        connection.commit();
        statement.close();
        connection.setAutoCommit(true);
    }

    /**
     * Execute one readOnly SQL statement and return the ResultSet (SELECT).
     * NOTE that Statement is not closed and must be closed manually for the user
     * (resultSet.getStatement().close())
     * @param SQLQuerys
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws IOException
     */
    public static ResultSet executeQuery(Connection connection, String SQLQuery) throws SQLException, ClassNotFoundException, IOException {
        //connection.setReadOnly(true);
        return executeQuery(connection, SQLQuery, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
    }

    /**
     * Execute one readOnly SQL statement and return the ResultSet (SELECT).
     * NOTE that Statement is not closed and must be closed manually for the user
     * (resultSet.getStatement().close())
     * @param SQLQuerys
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws IOException
     */
    public static ResultSet executeQuery(Connection connection, String SQLQuery, int resultSetType, int resultSetConcurrency) throws SQLException, ClassNotFoundException, IOException {
        ResultSet result = null;
        Statement statement = connection.createStatement(resultSetType, resultSetConcurrency);
        result = statement.executeQuery(SQLQuery);
        //connection.setReadOnly(false);
        return result;
    }

    /**
     * Inserts the values from original ResultSet to table table. Note that
     * ResultSet and table must have exactly the same columns (with identical names,
     * values, etc)
     * @param connection
     * @param original
     * @param table
     * @throws SQLException
     */
    public static void insert(Connection connection, ResultSet original, String table, Log log) throws SQLException {
        ResultSetMetaData originalMetaData = original.getMetaData();
        if (originalMetaData.getColumnCount() > 0) {
            //connection.setAutoCommit(false);

            String[] columns = new String[originalMetaData.getColumnCount()];
            int[] columsType = new int[originalMetaData.getColumnCount()];
            String[] columnsTypeName = new String[originalMetaData.getColumnCount()];
            for (int i = 0; i < originalMetaData.getColumnCount(); i++) {
                columns[i] = originalMetaData.getColumnLabel(i + 1);
                columsType[i] = originalMetaData.getColumnType(i + 1);
                columnsTypeName[i] = originalMetaData.getColumnTypeName(i + 1);
            }

            String query = "INSERT INTO " + table + " (";
            String qColumns = "";
            String qValues = "";
            for (int i = 0; i < columns.length - 1; i++) {
                qColumns += columns[i] + ",";
                qValues += "?,";
            }
            qColumns += columns[columns.length - 1] + ")";
            qValues += "?";

            query += qColumns + " VALUES (" + qValues + ")";
            PreparedStatement statement = connection.prepareStatement(query);


            int j = 0;
            int i = 0;
            while (original.next()) {
                String params = "";
                for (i = 0; i < columns.length; i++) {
                    //statement.setString(i + 1, (String)original.getObject(aColumns[i]));
                    try {
                        //Fixed Bug in JdbcOdbc : cause a SQLException with N.000 where N is a number
                        if ((columsType[i] == Types.NUMERIC || columsType[i] == Types.DECIMAL)) {
                            double element = original.getDouble(columns[i]);
                            if (original.wasNull()) {
                                statement.setNull(i + 1, columsType[i]);
                                params += "NULL,";
                            } else {
                                statement.setDouble(i + 1, element);
                                params += Double.toString(element) + ",";
                            }
                        } else {
                            Object o = original.getObject(columns[i]);
                            if (original.wasNull()) {
                                statement.setNull(i + 1, columsType[i]);
                                params += "NULL,";
                            } else {
                                statement.setObject(i + 1, o, columsType[i]);
                                params += "'" + o.toString() + "',";
                            }
                        }
                    } catch (Exception e) {
                        log.error(e.toString());
                        log.error(params);
                        log.error(j + " " + i);
                    }
                }
                try {
                    statement.executeUpdate();
                } catch (Exception e) {
                    String faultQuery = "INSERT INTO " + table + " (" + qColumns + " VALUES (" + params.substring(0, params.length() - 1) + ")";
                    log.error(faultQuery);
                    log.error(e.toString());
                }
                j++;
            }

            /*connection.commit();
            connection.setAutoCommit(true);*/
            statement.close();
        }
    }

}
