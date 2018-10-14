package net.daw.control;

import java.io.IOException;
import java.sql.Connection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.daw.connection.publicinterface.ConnectionInterface;
import net.daw.constant.ConnectionConstants;
import net.daw.factory.ConnectionFactory;
import net.daw.helper.EncodingHelper;

/**
 * Servlet implementation class json
 */
public class json extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public json() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub

        String strJson = "";
        String strOp = request.getParameter("op");
        String strCon = request.getParameter("con");
        int tipo = 0;

        if (strCon.equalsIgnoreCase(ConnectionConstants.EnumConstans.Hikari.toString())) {
            tipo = 1;
        } else if (strCon.equalsIgnoreCase(ConnectionConstants.EnumConstans.vibur.toString())) {
            tipo = 2;
        } else if (strCon.equalsIgnoreCase(ConnectionConstants.EnumConstans.c3p0.toString())) {
            tipo = 3;
        }

        if (strOp != null) {
            if (!strOp.equalsIgnoreCase("")) {

                if (strOp.equalsIgnoreCase("connect")) {
                    // conexion a la base de datos

                    try {
                        Class.forName("com.mysql.jdbc.Driver");

                    } catch (Exception ex) {
                        strJson = "{\"status\":500,\"msg\":\"jdbc driver not found\"}";
                    }

                    try {
                        switch (tipo) {
                            case 1:
                                ConnectionInterface oConnectionPoolH = ConnectionFactory.getConnection(ConnectionConstants.connectionHikari);
                                Connection oConnectionH = oConnectionPoolH.newConnection();
                                // servir la petición utilizando oConnection
                                oConnectionPoolH.disposeConnection();
                                strJson = "{\"status\":200,\"msg\":\"Hikari Connection OK\"}";
                                break;
                            case 2:
                                ConnectionInterface oConnectionPoolV = ConnectionFactory.getConnection(ConnectionConstants.connectionVibur);
                                Connection oConnectionV = oConnectionPoolV.newConnection();
                                // servir la petición utilizando oConnection
                                oConnectionPoolV.disposeConnection();
                                strJson = "{\"status\":200,\"msg\":\"Vibur Connection OK\"}";
                                break;
                            case 3:
                                ConnectionInterface oConnectionPoolC = ConnectionFactory.getConnection(ConnectionConstants.connectionC3p0);
                                Connection oConnectionC = oConnectionPoolC.newConnection();
                                // servir la petición utilizando oConnection
                                oConnectionPoolC.disposeConnection();
                                strJson = "{\"status\":200,\"msg\":\"C3p0 Connection OK\"}";
                                break;
                            default:
                                ConnectionInterface oConnectionPoolD = ConnectionFactory.getConnection(ConnectionConstants.connectionHikari);
                                Connection oConnectionD = oConnectionPoolD.newConnection();
                                // servir la petición utilizando oConnection
                                oConnectionPoolD.disposeConnection();
                                strJson = "{\"status\":200,\"msg\":\"Hikari Connection OK\"}";
                                

                        }

                    } catch (Exception ex) {
                        strJson = "{\"status\":500,\"msg\":\"Bad Connection: " + EncodingHelper.escapeQuotes(ex.getMessage()) + "\"}";
                    }

                }

                // http://localhost:8081/authentication/json?op=login&user=nombre&pass=password
                // http://localhost:8081/authentication/json?op=check
                // http://localhost:8081/authentication/json?op=logout
                response.setContentType("application/json;charset=UTF-8");
                HttpSession oSession = request.getSession();

                if (strOp.equalsIgnoreCase("login")) {
                    String strUser = request.getParameter("user");
                    String strPass = request.getParameter("pass");
                    if (strUser.equals("rafa") && strPass.equals("thebest")) {
                        oSession.setAttribute("daw", strUser);
                        strJson = "{\"status\":200,\"msg\":\"" + strUser + "\"}";
                    } else {
                        strJson = "{\"status\":401,\"msg\":\"Authentication error\"}";
                    }
                }
                if (strOp.equalsIgnoreCase("logout")) {
                    oSession.invalidate();
                    strJson = "{\"status\":200,\"msg\":\"Session is closed\"}";
                }
                if (strOp.equalsIgnoreCase("check")) {
                    String strUserName = (String) oSession.getAttribute("daw");
                    if (strUserName != null) {
                        strJson = "{\"status\":200,\"msg\":\"" + strUserName + "\"}";
                    } else {
                        strJson = "{\"status\":401,\"msg\":\"Authentication error\"}";
                    }
                }
                if (strOp.equalsIgnoreCase("getsecret")) {
                    String strUserName = (String) oSession.getAttribute("daw");
                    if (strUserName != null) {
                        strJson = "{\"status\":200,\"msg\":\"985739847598\"}";
                    } else {
                        strJson = "{\"status\":401,\"msg\":\"Authentication error\"}";
                    }
                }

            } else {
                strJson = "{\"status\":200,\"msg\":\"operation empty\"}";
            }
        } else {
            strJson = "{\"status\":200,\"msg\":\"operation can't be null\"}";
        }
        response.getWriter().append(strJson).close();
    }

}
