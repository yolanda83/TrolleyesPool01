package net.daw.constant;

public class ConnectionConstants {

	public static enum EnumConstans  {
		Hikari,
                vibur,
                c3p0,
		DBCP
	};

	public static final EnumConstans connectionHikari = EnumConstans.Hikari;
	public static final EnumConstans connectionVibur = EnumConstans.vibur;
	public static final EnumConstans connectionC3p0 = EnumConstans.c3p0;

	public static final String connectionNameH = "hikari";
	public static final String databaseName = "trolleyes";
	public static final String databaseLogin = "root2";
	public static final String databasePassword = "bitnami";
	public static final String databasePort = "3306";
	public static final String databaseHost = "localhost";
	public static final int getDatabaseMaxPoolSize = 10;
	public static final int getDatabaseMinPoolSize = 5;

	public static String getConnectionChain() {
		return "jdbc:mysql://" + ConnectionConstants.databaseHost + ":" + ConnectionConstants.databasePort + "/"
				+ ConnectionConstants.databaseName;
	}

}
