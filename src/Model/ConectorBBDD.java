package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConectorBBDD {
	
	public static ConectorBBDD instancia; //aplicar Singleton
	private static Connection conexion;
	
	private ConectorBBDD(){
		
		try {
			Class.forName(MYSQLDEMOConnection.driver);//Driver			
			conexion=DriverManager.getConnection(MYSQLDEMOConnection.url+MYSQLDEMOConnection.nameBBDD,
												MYSQLDEMOConnection.user,MYSQLDEMOConnection.pass);
			
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
	public static Connection getConexion() {
		return conexion;
	}
	
	public synchronized static ConectorBBDD saberEstado(){//singleton
		//la unica forma de hacer una conexion es invocando a este metodo
		if(instancia==null){
			instancia=new ConectorBBDD();			
		}
		return instancia;
	}
	public static void cerrarConexion(){
		if(instancia!=null)
			instancia=null;
	}
}
