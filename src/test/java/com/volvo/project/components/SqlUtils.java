package com.project.project.components;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.Arrays;
import java.util.List;

//import com.project.jvs.runtime.platform.ContainerManaged;

/**
 * Simple SQL utility to support executing SQL scripts from given paths.
 * 
 * @author PawelPie
 * 
 */
//@ContainerManaged
public class SqlUtils {

    private List<String> loadSqlQueries(String sqlLocation) throws IOException {
        String sqlScript = FileUtils.readFileToString(new File(sqlLocation));

        String[] split = StringUtils.split(sqlScript, ";");
        return Arrays.asList(split);

    }

    /**
     * Executes sql scripts given by array of paths on database represented by dataSource.
     * 
     * @param dataSource
     *            db datasource on which sql script will be executed
     * @param sqlScriptPaths
     *            array of sql script paths
     * @throws IOException
     * @throws SQLException
     */
    public void executeSqls(DataSource dataSource, String[] sqlScriptPaths) throws IOException, SQLException {
        for (String sqlScriptPath : sqlScriptPaths) {
            List<String> sqls = loadSqlQueries(sqlScriptPath);
            Connection connection = dataSource.getConnection();
            try {
                for (String sql : sqls) {
                    executeQuery(sql, connection);
                }
            } finally {
                connection.close();

            }
        }
    }

    /**
     * Executes sql script given by path, passing arguments whenever applicable in sql query from sql script.
     * 
     * @param dataSource
     *            db datasource on which sql script will be executed
     * @param sqlScriptPath
     *            sql script path
     * @param args
     *            arguments for sql query
     * @throws IOException
     * @throws SQLException
     */
    public void executeSql(DataSource dataSource, String sqlScriptPath, Object... args) throws IOException, SQLException {

        List<String> sqls = loadSqlQueries(sqlScriptPath);
        Connection connection = dataSource.getConnection();
        try {
            for (String sql : sqls) {
                executeQuery(sql, connection, args);
            }
        } finally {
            connection.close();
        }
    }

    /**
     * Executes sql on database represented by dataSource.
     * 
     * @param dataSource
     *            db datasource on which sql script will be executed
     * @param sql
     *            sql statement
     * @throws IOException
     * @throws SQLException
     */
    public void executeSql(DataSource dataSource, String sql) throws IOException, SQLException {

        Connection connection = dataSource.getConnection();
        try {
            executeQuery(sql, connection);
        } finally {
            connection.close();
        }
    }

    /**
     * Executes sql on database represented by dataSource and returns the result set.
     * 
     * @param dataSource
     *            db datasource on which sql script will be executed
     * @param sql
     *            sql statement
     * @return result set containing results of the query
     * @throws IOException
     * @throws SQLException
     */
    public String executeSqlAndGetResults(DataSource dataSource, final String sql, String coulmn) throws IOException, SQLException {

        Connection connection = null;
        Statement stmt = null;
        String result = null;
        ResultSet rs = null;
        try {
            connection = dataSource.getConnection();
            try {
                stmt = connection.createStatement();
                try {
                    rs = stmt.executeQuery(sql);
                    while (rs.next()) {
                        result = rs.getString(coulmn);
                    }
                } finally {
                    if (null != rs) {
                        rs.close();
                    }
                }
            } finally {
                if (null != stmt) {
                    stmt.close();
                }
            }
        } finally {
            if (null != connection) {
                connection.close();
            }
        }
        return result;
    }

    private void executeQuery(String sql, Connection connection, Object... args) throws SQLException {
        final String sqlQuery = StringUtils.trim(sql);
        if (StringUtils.isNotEmpty(sqlQuery)) {
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            try {
                for (int i = 0; i < args.length; i++) {
                    statement.setObject(i + 1, args[i]);
                }
                statement.execute();
            } finally {
                statement.close();
            }
        }
    }

}
