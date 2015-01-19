package DTO;

import java.sql.Date;

public class VentaDTO {
	
	private int idVenta;//numerico auto increment, clave primaria
	private Date fechaVenta;//date
	private int cantidad;
	
	private String cliente;//FK para la relacion de 1 cliente n ventas
	
	
	public VentaDTO(int idVenta, Date fechaVenta, int cantidad, String cliente) {
		setIdVenta(idVenta);
		setFechaVenta(fechaVenta);
		setCantidad(cantidad);
		setCliente(cliente);
	}
	
	@Override
	public String toString() {
		return "Venta [ID=" + idVenta + ", FECHA=" + fechaVenta
				+ ", CANTIDAD=" + cantidad + ", CLIENTE=" + cliente + "]";
	}

	public int getIdVenta() {return idVenta;}
	public Date getFechaVenta() {return fechaVenta;}
	public int getCantidad() {return cantidad;}
	public String getCliente() {return cliente;}
	
	public void setIdVenta(int idVenta) {
		this.idVenta = idVenta;
	}

	public void setFechaVenta(Date fechaVenta) {
		this.fechaVenta = fechaVenta;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	
	

}
