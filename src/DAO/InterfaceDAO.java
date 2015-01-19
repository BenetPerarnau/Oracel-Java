package DAO;

import java.sql.SQLException;
import java.util.ArrayList;

import DTO.VentaDTO;

	
	/*
	 * Esta interface tiene los metodos genericos de consultas en una bbdd que tendran que implementar 
	 * aquellas clases que lo utilizen.
	 */

	public interface InterfaceDAO <CualquierCosa>{
			public boolean create() throws SQLException;//creara una tabla con el nombre de la clase en la bbdd 
			public boolean insert(CualquierCosa c)throws SQLException;//recibe un objecto x y lo insertara en la tabla y dependiendo en que clase se llame
			public boolean delete(Object key)throws SQLException;//recibe un Objeto generico ya que no en todas las tablas la clave primaria es un string o un entero
			public boolean update(CualquierCosa c)throws SQLException;//recibe un objecto x modificado excepto la clave primaria y actualiza todo conservando la clave primaria		
			public CualquierCosa read(Object key)throws SQLException;//recibe un parametro de busca y devuelve un registro		
			public ArrayList<CualquierCosa> readAll()throws SQLException;//devuelve todos los registros de la tabla
			public boolean exist();//saber si existe o no la tabla
			
			//public int getStockProducto(Object key)throws SQLException;
			//public ArrayList<VentaDTO> selectVentasByClinete(String cif);
	}
