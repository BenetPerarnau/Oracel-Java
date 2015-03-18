package DAO;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Model.Conector;
import oracle.sql.STRUCT;

public class ClienteDAO implements OperacionesDAO{

	
	private final String SQL_SELECT_ALL="SELECT value(u) FROM CLIENTES_TABLE u";
	private final String SQL_SELECT="SELECT value(u) FROM CLIENTES_TABLE u WHERE CIF=?";
	private final String SQL_INSERT="INSERT INTO CLIENTES_TABLE VALUES (?)";
	/*
	 * update agenda_tabla
set numeros=(arrayTelefonos(telefono_o('00','000','0000000'),
							telefono_o('00','000','0000000'),
							telefono_o('00','000','0000000'),
							telefono_o('00','000','0000000'),
							telefono_o('00','000','0000000')))
			where nombre='Marta';
	 */
	private final String SQL_UPDATE_PHONES="UPDATE CLIENTES_TABLE SET TELEFONOS=? WHERE CIF=?";
	private final String SQL_UPDATE_DISCOUNT="UPDATE CLIENTES_TABLE SET DECUENTO=? WHERE CIF=?";
	
	private static Conector cn=Conector.getInstancia();
	
	private PreparedStatement ps;
	private ResultSet rs;
	
	@Override
	public ArrayList<STRUCT> selectAll() throws SQLException {
		
		ArrayList<STRUCT> data=new ArrayList<STRUCT>();
		
		ps=cn.getConexion().prepareStatement(SQL_SELECT_ALL);
		rs=ps.executeQuery();
		
		while(rs.next()){
			data.add((STRUCT)rs.getObject(1));
		}
				
		return data;
	}

	@Override
	public STRUCT select(String cif) throws SQLException {
		STRUCT cliente=null;
		
		ps=cn.getConexion().prepareStatement(SQL_SELECT);
		ps.setString(1, cif);
		
		rs=ps.executeQuery();
		
		if(rs.next()){
			cliente=(STRUCT)rs.getObject(1);
		}
		
		return cliente;
	}

	@Override
	public boolean insert(STRUCT object) throws SQLException {
		boolean result=true;
		
		ps=cn.getConexion().prepareStatement(SQL_INSERT);
		ps.setObject(1, object);
		
		if(ps.executeUpdate()!=1){
			result= false;
		}
		
		
		return result;
	}

	@Override
	public boolean updatePhones(String cif, oracle.sql.ARRAY telef)
			throws SQLException {
		boolean result=false;
		ps=cn.getConexion().prepareStatement(SQL_UPDATE_PHONES);
		ps.setObject(1, telef);
		ps.setString(2, cif);
		
		if(ps.executeUpdate()>0){
			result=true;
		}
		return result;
	}

	@Override
	public boolean updateDiscount(String cif, float discount)
			throws SQLException {
		boolean result=false;
		ps=cn.getConexion().prepareStatement(SQL_UPDATE_DISCOUNT);
		ps.setFloat(1, discount);
		ps.setString(2, cif);
		if(ps.executeUpdate()>0){
			result=true;
		}
		return result;
	}
	
	
	

}
