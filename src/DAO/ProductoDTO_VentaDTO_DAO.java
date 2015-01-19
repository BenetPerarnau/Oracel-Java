package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DTO.ProductoDTO;
import DTO.ProductoDTO_VentaDTO;
import Model.ConectorBBDD;

public class ProductoDTO_VentaDTO_DAO implements InterfaceDAO<ProductoDTO_VentaDTO> {
	
	
	private final String SQL_CREATE="CREATE TABLE PRODUCTOS_VENTAS ("
			  					+ " ID INTEGER PRIMARY KEY AUTO_INCREMENT,"
			  					+ " IDPRODUCTO INTEGER,"
			  					+ " IDVENTA INTEGER,"
			  					+ " FOREIGN KEY (IDPRODUCTO) REFERENCES PRODUCTOS (ID),"
			  					+ " FOREIGN KEY (IDVENTA) REFERENCES VENTAS (IDVENTA))";
	private final String SQL_INSERT="INSERT INTO PRODUCTOS_VENTAS"
								+ " (ID, IDPRODUCTO, IDVENTA)"
								+ " VALUES (?,?,?)";
	private final String SQL_DELETE="DELETE FROM PRODUCTOS_VENTAS WHERE ID=?";
	private final String SQL_UPDATE="UPDATE PRODUCTOS_VENTAS"
								+ " IDPRODUCTO=?,"
								+ " IDVENTA=?"
								+ " WHERE ID=?";
	private final String SQL_READ="SELECT * FROM PRODUCTOS_VENTAS WHERE ID=?";
	private final String SQL_READ_ALL="SELECT * FROM PRODUCTOS_VENTAS";
	
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
	public boolean insert(ProductoDTO_VentaDTO c) throws SQLException {
		try{
			PreparedStatement ps;
			ps=cnn.getConexion().prepareStatement(SQL_INSERT);
			ps.setInt(1, c.getId());
			ps.setInt(2, c.getIdProducto());
			ps.setInt(3, c.getIdVenta());
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
	public boolean update(ProductoDTO_VentaDTO c) throws SQLException {
		try{
			PreparedStatement ps;
			ps=cnn.getConexion().prepareStatement(SQL_DELETE);		
			ps.setInt(1, c.getIdProducto());
			ps.setInt(2, c.getIdVenta());
			ps.setInt(3, c.getId());
			if(ps.executeUpdate()>0){
				return true;
			}
			return false;
		}finally{
			if(cnn!=null)cnn.cerrarConexion();
		}
	}

	@Override
	public ProductoDTO_VentaDTO read(Object key) throws SQLException {
		try{
			ProductoDTO_VentaDTO c=null;
			ResultSet r;
			PreparedStatement ps;
			ps=cnn.getConexion().prepareStatement(SQL_READ);
			ps.setInt(1, Integer.parseInt(key.toString()));
			r=ps.executeQuery();
			while(r.next()){
				c=new ProductoDTO_VentaDTO(r.getInt(1), r.getInt(2), r.getInt(3));
			}
			return c;
		}finally{
			if(cnn!=null)cnn.cerrarConexion();
		}
	}

	@Override
	public ArrayList<ProductoDTO_VentaDTO> readAll() throws SQLException {
		try{
			ArrayList<ProductoDTO_VentaDTO> array=new ArrayList<ProductoDTO_VentaDTO>();
			ResultSet r;
			PreparedStatement ps;
			ps=cnn.getConexion().prepareStatement(SQL_READ_ALL);
			r=ps.executeQuery();
			while(r.next()){
				array.add(new ProductoDTO_VentaDTO(r.getInt(1), r.getInt(2), r.getInt(3)));
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
