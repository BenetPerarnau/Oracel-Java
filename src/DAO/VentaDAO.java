package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DTO.ProductoDTO_VentaDTO;
import DTO.VentaDTO;
import Model.ConectorBBDD;

public class VentaDAO implements InterfaceDAO<VentaDTO>{
	
	private final String SQL_CREATE="CREATE TABLE VENTAS ("
			  					+ " IDVENTA INTEGER PRIMARY KEY AUTO_INCREMENT,"
			  					+ " FECHAVENTA DATE NOT NULL,"
			  					+ " CANTIDAD INTEGER CHECK (CANTIDAD>0),"
			  					+ " CLIENTE VARCHAR(9),"
			  					+ " FOREIGN KEY (CLIENTE) REFERENCES CLIENTES (CIF))";
	private final String SQL_INSERT="INSERT INTO VENTAS"
								+ " (IDVENTA, FECHAVENTA, CANTIDAD, CLIENTE)"
								+ " VALUES (?,?,?,?)";
	private final String SQL_DELETE="DELETE FROM VENTAS WHERE IDVENTA=?";
	private final String SQL_UPDATE="UPDATE VENTAS"
								+ " FECHAVENTA=?,"
								+ " CANTIDAD=?,"
								+ " CLIENTE=?"
								+ " WHERE IDVENTA=?";
	private final String SQL_READ="SELECT * FROM VENTAS WHERE IDVENTA=?";
	private final String SQL_READ_ALL="SELECT * FROM VENTAS";
	
	private final String SQL_READ_VENTAS_CLIENTE="SELECT * FROM VENTAS WHERE CLIENTE=?";
	private final String SQL_RETIRAR_STOCK="UPDATE PRODUCTOS"
										+ " SET STOCKACTUAL = ?"
										+ " WHERE ID = ?";
	private final String SQL_INSERT_IN_PRODUCTOs_VENTAS="INSERT INTO PRODUCTOS_VENTAS"
													  + " (ID, IDPRODUCTO, IDVENTA)"
													  + " VALUES(?,?,?)";
	private final String SQL_GET_NEXT_ID="SELECT MAX(IDVENTA) FROM VENTAS";
	
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
	public boolean insert(VentaDTO c) throws SQLException {
		try{
			PreparedStatement ps;
			ps=cnn.getConexion().prepareStatement(SQL_INSERT);
			ps.setInt(1, c.getIdVenta());
			ps.setDate(2, c.getFechaVenta());
			ps.setInt(3, c.getCantidad());
			ps.setString(4, c.getCliente());
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
	public boolean update(VentaDTO c) throws SQLException {
		try{
			PreparedStatement ps;
			ps=cnn.getConexion().prepareStatement(SQL_DELETE);		
			ps.setDate(1, c.getFechaVenta());
			ps.setInt(2, c.getCantidad());
			ps.setString(3, c.getCliente());
			ps.setInt(4, c.getIdVenta());
			if(ps.executeUpdate()>0){
				return true;
			}
			return false;
		}finally{
			if(cnn!=null)cnn.cerrarConexion();
		}
	}

	@Override
	public VentaDTO read(Object key) throws SQLException {
		try{
			VentaDTO c=null;
			ResultSet r;
			PreparedStatement ps;
			ps=cnn.getConexion().prepareStatement(SQL_READ);
			ps.setInt(1, Integer.parseInt(key.toString()));
			r=ps.executeQuery();
			while(r.next()){
				c=new VentaDTO(r.getInt(1), r.getDate(2), r.getInt(3),
								r.getString(4));
			}
			return c;
		}finally{
			if(cnn!=null)cnn.cerrarConexion();
		}
	}

	@Override
	public ArrayList<VentaDTO> readAll() throws SQLException {
		try{
			ArrayList<VentaDTO> array=new ArrayList<VentaDTO>();
			ResultSet r;
			PreparedStatement ps;
			ps=cnn.getConexion().prepareStatement(SQL_READ_ALL);
			r=ps.executeQuery();
			while(r.next()){
				array.add(new VentaDTO(r.getInt(1), r.getDate(2), r.getInt(3),
						r.getString(4)));
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
	
	public ArrayList<VentaDTO> selectVentasByClinete(Object key)  throws SQLException {
		try{
			ArrayList<VentaDTO> array=new ArrayList<VentaDTO>();
			ResultSet r;
			PreparedStatement ps;
			ps=cnn.getConexion().prepareStatement(SQL_READ_VENTAS_CLIENTE);
			ps.setString(1, key.toString());
			r=ps.executeQuery();
			while(r.next()){
				array.add(new VentaDTO(r.getInt(1), r.getDate(2), r.getInt(3),
						r.getString(4)));
			}
			return array;
		}finally{
			if(cnn!=null)cnn.cerrarConexion();
		}
	}

	public boolean insertVentaRestandoStock(int cantidadStock, int idProducto, VentaDTO v, ProductoDTO_VentaDTO pv) throws SQLException{
		
		PreparedStatement ps;
		cnn.getConexion().setAutoCommit(false);
		boolean correct=false;
		try{
			ps=cnn.getConexion().prepareStatement(SQL_RETIRAR_STOCK);
			ps.setInt(1, cantidadStock);
			ps.setInt(2, idProducto);
			if(ps.executeUpdate()>0){//ejecutamos la sentencia que actualiza en la tabla productos el nuevo stock
				ps=cnn.getConexion().prepareStatement(SQL_INSERT);
				ps.setInt(1, v.getIdVenta());
				ps.setDate(2, v.getFechaVenta());
				ps.setInt(3, v.getCantidad());
				ps.setString(4, v.getCliente());
				if(ps.executeUpdate()>0){//ejecutamos la sentencia que inserta el nuevo registro en ventas
					ps=cnn.getConexion().prepareStatement(SQL_INSERT_IN_PRODUCTOs_VENTAS);
					ps.setInt(1, pv.getId());//id es auto increment 
					ps.setInt(2, pv.getIdProducto());
					ps.setInt(3, pv.getIdVenta());
					if(ps.executeUpdate()>0){//ejecutamos la sentencia que inserta en la tabla Productos_Ventas
						correct=true;
					}
				}
			}
			if(correct){
				cnn.getConexion().commit();
				return true;
			}else{
				cnn.getConexion().rollback();
				return false;
			}
		}finally{
			if(cnn.getConexion()!=null)cnn.getConexion().close();
			if(cnn!=null)cnn.cerrarConexion();
		}
		
	}

	public int getNextID() throws SQLException{
	try{
		PreparedStatement ps=cnn.getConexion().prepareStatement(SQL_GET_NEXT_ID);
		ResultSet r=ps.executeQuery();
		if(r.next()){
			return ((r.getInt(1))+1);
		}
		return -1;
	}finally{
		if(cnn!=null)cnn.cerrarConexion();
	}	
}
}
