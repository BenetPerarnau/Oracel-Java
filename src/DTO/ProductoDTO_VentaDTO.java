package DTO;

public class ProductoDTO_VentaDTO {
	
	private int id;//pk
	private int idProducto;//fk, relacion de n a 1 con la tabla productos
	private int idVenta;//fk,    relacion de n a 1 con la tabla ventas
	
	
	public ProductoDTO_VentaDTO(int id, int idProducto, int idVenta) {
		setId(id);
		setIdProducto(idProducto);
		setIdVenta(idVenta);
	}
	
	public int getId() {return id;}
	public int getIdProducto() {return idProducto;}
	public int getIdVenta() {return idVenta;}
	
	
	
	@Override
	public String toString() {
		return "ProductoDTO_VentaDTO [id=" + id + ", idProducto=" + idProducto
				+ ", idVenta=" + idVenta + "]";
	}

	public void setId(int id) {
		this.id = id;
	}
	public void setIdProducto(int idProducto) {
		this.idProducto = idProducto;
	}
	public void setIdVenta(int idVenta) {
		this.idVenta = idVenta;
	}
}
