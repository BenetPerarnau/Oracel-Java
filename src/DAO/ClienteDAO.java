package DAO;

import java.sql.SQLException;
import java.util.ArrayList;

import oracle.sql.STRUCT;

public class ClienteDAO implements OperacionesDAO{

	
	private final String SQL_SELECT_ALL="";
	private final String SQL_SELECT="";
	private final String SQL_INSERT="";
	private final String SQL_UPDATE_PHONE="";
	private final String SQL_UPDATE_DISCOUNT="";
	
	
	@Override
	public ArrayList<STRUCT> selectAll() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public STRUCT select(String cif) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean insert(STRUCT object) throws SQLException {
		// TODO Auto-generated method stub
		return false;
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
