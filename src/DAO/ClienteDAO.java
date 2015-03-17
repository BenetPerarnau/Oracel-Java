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

	
	private final String SQL_SELECT_ALL="SELECT * FROM CLIENTES_TABLE";
	private final String SQL_SELECT="";
	private final String SQL_INSERT="INSERT INTO CLIENTES_TABLE VALUES (?)";
	private final String SQL_UPDATE_PHONE="";
	private final String SQL_UPDATE_DISCOUNT="";
	
	private static Conector cn=Conector.getInstancia();
	
	private PreparedStatement ps;
	private ResultSet rs;
	
	@Override
	public ArrayList<STRUCT> selectAll() throws SQLException {
		
		ArrayList<STRUCT> data=new ArrayList<STRUCT>();
		
		ps=cn.getConexion().prepareStatement(SQL_SELECT_ALL);
		rs=ps.executeQuery();
		
		while(rs.next()){
			data.add((oracle.sql.STRUCT)rs.getObject(1));
		}
				
		return data;
	}

	@Override
	public STRUCT select(String cif) throws SQLException {
		// TODO Auto-generated method stub
		return null;
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
	public boolean updatePhone(String cif, String phone, STRUCT object)
			throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateDiscount(String cif, String discount)
			throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	

}
