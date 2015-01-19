package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DTO.ProductoDTO;
import DTO.VentaDTO;
import Model.ConectorBBDD;

public class ProductoDAO implements InterfaceDAO<ProductoDTO> {
	
	private final String SQL_CREATE="CREATE TABLE PRODUCTOS ("
			  					+ " ID INTEGER PRIMARY KEY,"
			  					+ " DESCRIPCION VARCHAR(50) NOT NULL,"
			  					+ " STOCKACTUAL INTEGER CHECK (STOCKACTUAL>=0),"
			  					+ " PVP FLOAT (6,2))";
	private final String SQL_INSERT="INSERT INTO PRODUCTOS"
								+ " (ID, DESCRIPCION, STOCKACTUAL, PVP)"
								+ " VALUES (?,?,?,?)";
	private final String SQL_DELETE="DELETE FROM PRODUCTOS WHERE ID=?";
	private final String SQL_UPDATE="UPDATE PRODUCTOS"
								+ " DESCRIPCION = ?,"
								+ " STOCKACTUAL = ?,"
								+ " PVP = ?"
								+ " WHERE ID = ?";
	private final String SQL_READ="SELECT * FROM PRODUCTOS WHERE ID=?";
	private final String SQL_READ_ALL="SELECT * FROM PRODUCTOS";
	
	private final String SQL_GET_STOCK="SELECT STOCKACTUAL FROM PRODUCTOS WHERE ID = ? ";
	
	private final static ConectorBBDD cnn=ConectorBBDD.saberEstado(); //Singleton
	
	@Override
	public boolean create() throws SQLException {
		try{
			PreparedStatement ps;
			ps=cnn.getConexion().prepareStatement(SQL_CREATE);
			if(ps.executeUpdate()>0){
				return true;
			}
			return false;
		}finally{
			if(cnn!=null)cnn.cerrarConexion();
		}
	}

	@Override
	public boolean insert(ProductoDTO c) throws SQLException {
		try{
			PreparedStatement ps;
			ps=cnn.getConexion().prepareStatement(SQL_INSERT);
			ps.setInt(1, c.getId());
			ps.setString(2, c.getDesc());
			ps.setInt(3, c.getStockActual());
			ps.setFloat(4, c.getPvp());
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
			ps.setInt(1, Integer.parseInt(key.toString()));
			if(ps.executeUpdate()>0){
				return true;
			}
			return false;
		}finally{
			if(cnn!=null)cnn.cerrarConexion();
		}
	}

	@Override
	public boolean update(ProductoDTO c) throws SQLException {
		try{
			PreparedStatement ps;
			ps=cnn.getConexion().prepareStatement(SQL_DELETE);		
			ps.setString(1, c.getDesc());
			ps.setInt(2, c.getStockActual());
			ps.setFloat(3, c.getPvp());
			ps.setInt(4, c.getId());
			if(ps.executeUpdate()>0){
				return true;
			}
			return false;
		}finally{
			if(cnn!=null)cnn.cerrarConexion();
		}
	}

	@Override
	public ProductoDTO read(Object key) throws SQLException {
		try{
			ProductoDTO c=null;
			ResultSet r;
			PreparedStatement ps;
			ps=cnn.getConexion().prepareStatement(SQL_READ);
			ps.setInt(1, Integer.parseInt(key.toString()));
			r=ps.executeQuery();
			while(r.next()){
				c=new ProductoDTO(r.getInt(1), r.getString(2), r.getInt(3),
								r.getFloat(4));
			}
			return c;
		}finally{
			if(cnn!=null)cnn.cerrarConexion();
		}
	}

	@Override
	public ArrayList<ProductoDTO> readAll() throws SQLException {
		try{
			ArrayList<ProductoDTO> array=new ArrayList<ProductoDTO>();
			ResultSet r;
			PreparedStatement ps;
			ps=cnn.getConexion().prepareStatement(SQL_READ_ALL);
			r=ps.executeQuery();
			while(r.next()){
				array.add(new ProductoDTO(r.getInt(1), r.getString(2), r.getInt(3),
						r.getFloat(4)));
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

	public int getStockProducto(int id) throws SQLException{
		PreparedStatement ps;
		ResultSet r;
		try{
			ps=cnn.getConexion().prepareStatement(SQL_GET_STOCK);
			ps.setInt(1, id);
			r=ps.executeQuery();
			while(r.next()){
				return r.getInt(1);
			}
		}finally{
			cnn.cerrarConexion();
		}
		return -1;
		
		
	}


	
}
