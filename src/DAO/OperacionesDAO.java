package DAO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OperacionesDAO {

	
	public ArrayList<oracle.sql.STRUCT> selectAll() throws SQLException;
	public oracle.sql.STRUCT select(String cif)throws SQLException;
	
	public boolean insert(oracle.sql.STRUCT object)throws SQLException;
	public boolean updatePhone(String cif, String phone, oracle.sql.STRUCT object)throws SQLException;
	public boolean updateDiscount(String cif, String discount)throws SQLException;
	
	
}
