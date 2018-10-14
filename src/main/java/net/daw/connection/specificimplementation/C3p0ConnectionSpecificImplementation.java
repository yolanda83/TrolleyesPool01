/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.daw.connection.specificimplementation;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import net.daw.connection.publicinterface.ConnectionInterface;
import net.daw.constant.ConnectionConstants;
import org.vibur.dbcp.ViburDBCPDataSource;

/**
 *
 * @author Yolanda
 */
public class C3p0ConnectionSpecificImplementation implements ConnectionInterface {

    private Connection oConnection;


    public Connection newConnection() throws Exception {

        ComboPooledDataSource config = new ComboPooledDataSource();


        config.setJdbcUrl(ConnectionConstants.getConnectionChain());
        config.setUser(ConnectionConstants.databaseLogin);
        config.setPassword(ConnectionConstants.databasePassword);
        config.setMaxPoolSize(ConnectionConstants.getDatabaseMaxPoolSize);
        config.setMinPoolSize(ConnectionConstants.getDatabaseMinPoolSize);
        // the settings below are optional -- c3p0 can work with defaults
        config.setAcquireIncrement(5);
        config.setMaxStatements(180);

        try {

            oConnection = config.getConnection();

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

    }

}
