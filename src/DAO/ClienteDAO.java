package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DTO.ClienteDTO;
import DTO.VentaDTO;
import Model.ConectorBBDD;

public class ClienteDAO implements InterfaceDAO<ClienteDTO>{
	
	private final String SQL_CREATE="CREATE TABLE CLIENTES ("
								  + " CIF VARCHAR(9) PRIMARY KEY,"
								  + " NOMBRE VARCHAR(50) NOT NULL,"
								  + " DIRECCION VARCHAR(50),"
								  + " POBLACION VARCHAR(50),"
								  + " TELEFONO VARCHAR(20))";
	private final String SQL_INSERT="INSERT INTO CLIENTES"
								+ " (CIF, NOMBRE, DIRECCION, POBLACION, TELEFONO)"
								+ " VALUES (?,?,?,?,?)";
	private final String SQL_DELETE="DELETE FROM CLIENTES WHERE CIF=?";
	private final String SQL_UPDATE="UPDATE CLIENTES"
								 + " NOMBRE=?,"
								 + " DIRECCION=?,"
								 + " POBLACION=?,"
								 + " TELEFONO=?"
								 + " WHERE CIF=?";
	private final String SQL_READ="SELECT * FROM CLIENTES WHERE CIF=?";
	private final String SQL_READ_ALL="SELECT * FROM CLIENTES";
	
	private final static ConectorBBDD cnn=ConectorBBDD.saberEstado(); //Singleton

	@Override
	public boolean create() throws SQLException {
		try{
			PreparedStatement ps;
			ps=cnn.getConexion().prepareStatement(SQL_CREATE);
			if(ps.executeUpdate()>0){
				return true;
			}
			
		}finally{
			if(cnn!=null)cnn.cerrarConexion();
		}
		return false;
	}

	@Override
	public boolean insert(ClienteDTO c) throws SQLException {
		try{
			PreparedStatement ps;
			ps=cnn.getConexion().prepareStatement(SQL_INSERT);
			ps.setString(1, c.getCif());
			ps.setString(2, c.getName());
			ps.setString(3, c.getDirec());
			ps.setString(4, c.getPoblacion());
			ps.setString(5, c.getTelef());
			if(ps.executeUpdate()>0){
				return true;
			}
			return false;
		}finally{
			if(cnn!=null)cnn.cerrarConexion();
		}
	}

	@Override
	public boolean delete(Object key) throws SQLException {
		try{
			PreparedStatement ps;
			ps=cnn.getConexion().prepareStatement(SQL_DELETE);
			ps.setString(1, key.toString());
			if(ps.executeUpdate()>0){
				return true;
			}
			return false;
		}finally{
			if(cnn!=null)cnn.cerrarConexion();
		}
	}

	@Override
	public boolean update(ClienteDTO c) throws SQLException {
		try{
			PreparedStatement ps;
			ps=cnn.getConexion().prepareStatement(SQL_DELETE);		
			ps.setString(1, c.getName());
			ps.setString(2, c.getDirec());
			ps.setString(3, c.getPoblacion());
			ps.setString(4, c.getTelef());
			ps.setString(5, c.getCif());
			if(ps.executeUpdate()>0){
				return true;
			}
			return false;
		}finally{
			if(cnn!=null)cnn.cerrarConexion();
		}
	}

	@Override
	public ClienteDTO read(Object key) throws SQLException {
		try{
			ClienteDTO c=null;
			ResultSet r;
			PreparedStatement ps;
			ps=cnn.getConexion().prepareStatement(SQL_READ);
			ps.setString(1, key.toString());
			r=ps.executeQuery();
			while(r.next()){
				c=new ClienteDTO(r.getString(1), r.getString(2), r.getString(3),
								r.getString(4),r.getString(5));
			}
			return c;
		}finally{
			if(cnn!=null)cnn.cerrarConexion();
		}
	}

	@Override
	public ArrayList<ClienteDTO> readAll() throws SQLException {
		try{
			ArrayList<ClienteDTO> array=new ArrayList<ClienteDTO>();
			ResultSet r;
			PreparedStatement ps;
			ps=cnn.getConexion().prepareStatement(SQL_READ_ALL);
			r=ps.executeQuery();
			while(r.next()){
				array.add(new ClienteDTO(r.getString(1), r.getString(2), r.getString(3),
								r.getString(4),r.getString(5)));
			}
			return array;
		}finally{
			if(cnn!=null)cnn.cerrarConexion();
		}
	}

	@Override
	public boolean exist() {
		PreparedStatement ps=null;
		ResultSet r=null;
		boolean existe=false;
		try{
			ps=cnn.getConexion().prepareStatement(SQL_READ_ALL);
			r=ps.executeQuery();			
			existe=true;//si llega ha esta linea es pq ha encontrado la tabla en la bbdd si no pasara al bloque catch retornando false
			
		} catch (SQLException e) {
			return existe;
		}finally{
				try {
					if(ps!=null)ps.close();
					if(r!=null)r.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}		
		}
		return existe;
	}






}
