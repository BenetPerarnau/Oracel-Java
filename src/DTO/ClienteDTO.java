package DTO;

public class ClienteDTO {
	
	private String cif;//varchar9 clave primaria
	private String name;//varchar50, not null
	private String direc;//varchar50
	private String poblacion;//varchar50
	private String telef;//varchar20
	
	public ClienteDTO(String cif, String name, String direc, String poblacion,String telef) {
		setCif(cif);
		setName(name);
		setDirec(direc);
		setPoblacion(poblacion);
		setTelef(telef);
	}
	
	public String getCif() {return cif;}
	public String getName() {return name;}
	public String getDirec() {return direc;}
	public String getPoblacion() {return poblacion;}
	public String getTelef() {return telef;}

	@Override
	public String toString() {
		return "Cliente [CIF=" + cif + ", NOMBRE=" + name + ", DIRECCION=" + direc
				+ ", POBLACION=" + poblacion + ", TELEFONO=" + telef + "]";
	}

	public void setCif(String cif) {
		this.cif = cif;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDirec(String direc) {
		this.direc = direc;
	}

	public void setPoblacion(String poblacion) {
		this.poblacion = poblacion;
	}

	public void setTelef(String telef) {
		this.telef = telef;
	}
	
	

}
