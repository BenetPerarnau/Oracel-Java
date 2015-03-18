package DAO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OperacionesDAO {

	
	public ArrayList<oracle.sql.STRUCT> selectAll() throws SQLException;
	public oracle.sql.STRUCT select(String cif)throws SQLException;
	
	public boolean insert(oracle.sql.STRUCT object)throws SQLException;
	public boolean updatePhones(String cif, oracle.sql.ARRAY telef)throws SQLException;
	public boolean updateDiscount(String cif, float discount)throws SQLException;
	
	
}
