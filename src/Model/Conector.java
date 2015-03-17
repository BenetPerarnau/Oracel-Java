package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import Utils.Constants;
import oracle.jdbc.driver.OracleConnection;

public class Conector {
	
	private static Conector instancia;
	private static Connection conexion;
	private Conector(){
		
		try {
			Class.forName(Constants.DRIVER);//Driver			
			conexion=DriverManager.getConnection(Constants.DRIVER_URL+Constants.IP+Constants.PORT+Constants.NS,
											     Constants.USER,
											     Constants.PASSWORD);
			
		} catch (ClassNotFoundException e) {			
			e.printStackTrace();		
		} catch (SQLException e) {
			System.out.println("ERROR SQL!!");
			while(e!=null){
				System.out.println("SQL State => "+e.getSQLState());
				System.out.println("Error Code => "+e.getErrorCode());
				System.out.println("Message => "+e.getMessage());
				Throwable t=e.getCause();
				while(t!=null){
					System.out.println("Cause => "+t);
					t=t.getCause();
				}
				e=e.getNextException();
			}
		}
	}
	
	
	public static Conector getInstancia(){
		
		if(instancia==null){
				instancia=new Conector();
		}
				
		return instancia;
	}
	
	public static Connection getConexion(){
		return conexion;
	}
	public static void closeConexion(){
		if(instancia!=null){
			instancia=null;
		}
	}

}
