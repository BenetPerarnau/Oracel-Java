package DTO;

public class ProductoDTO {
	
	private int id; //clave primaria
	private String desc;//varchar50, not null
	private int stockActual;
	private float pvp;
	

	public ProductoDTO(int id, String desc, int stockActual, float pvp) {
		setId(id);
		setDesc(desc);
		setStockActual(stockActual);
		setPvp(pvp);
	}
	
	@Override
	public String toString() {
		return "Producto [ID=" + id + ", DESCRIPCION=" + desc + ", STOCK="
				+ stockActual + ", PVP=" + pvp +"€]";
	}

	public int getId() {return id;}
	public String getDesc() {return desc;}
	public int getStockActual() {return stockActual;}
	public float getPvp() {return pvp;}

	public void setId(int id) {
		this.id = id;
	}

	public void setDesc(String desc) {//Varchar(50)
		this.desc = desc;
	}

	public void setStockActual(int stockActual) {
		this.stockActual = stockActual;
	}

	public void setPvp(float pvp) {
		this.pvp = pvp;
	}
	
	

}
