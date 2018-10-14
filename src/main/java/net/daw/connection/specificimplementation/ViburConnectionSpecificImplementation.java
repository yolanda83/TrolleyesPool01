/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.daw.connection.specificimplementation;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import net.daw.connection.publicinterface.ConnectionInterface;
import net.daw.constant.ConnectionConstants;
import org.vibur.dbcp.ViburConfig;
import org.vibur.dbcp.ViburDBCPDataSource;

/**
 *
 * @author Yolanda
 */
public class ViburConnectionSpecificImplementation implements ConnectionInterface {

    private Connection oConnection;
//    private ViburDBCPDataSource oConnectionPool;
//    private HikariDataSource oConnectionPool;
    
    public Connection newConnection() throws Exception {

        ViburDBCPDataSource config = new ViburDBCPDataSource();
        config.setJdbcUrl(ConnectionConstants.getConnectionChain());
        config.setUsername(ConnectionConstants.databaseLogin);
        config.setPassword(ConnectionConstants.databasePassword);
        config.setPoolMaxSize(ConnectionConstants.getDatabaseMaxPoolSize);
        config.setPoolInitialSize(ConnectionConstants.getDatabaseMinPoolSize);
//      config.setPoolInitialSize(10);
//      config.setPoolMaxSize(100);

        config.setConnectionIdleLimitInSeconds(30);
        config.setTestConnectionQuery("isValid");

        config.setLogQueryExecutionLongerThanMs(500);
        config.setLogStackTraceForLongQueryExecution(true);

        config.setStatementCacheMaxSize(200);
        config.start();

        try {
//            oConnectionPool = new ViburDBCPDataSource(config);
//            oConnection = (Connection) oConnectionPool.getConnection();
            Connection oConnection = (Connection) config.getConnection();

            return oConnection;

        } catch (SQLException ex) {
            String msgError = this.getClass().getName() + ":" + (ex.getStackTrace()[1]).getMethodName();
            throw new Exception(msgError, ex);
        }

    }

    public void disposeConnection() throws Exception {
        if (oConnection != null) {
            oConnection.close();
        }
//        if (oConnectionPool != null) {
//            oConnectionPool.close();
//        }
    }
}
