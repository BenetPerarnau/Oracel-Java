package DAO;

public class ObjectDAOFactory {

	public static InterfaceDAO getDAO(byte i){
		switch(i){
		case 1:
			return new ProductoDAO();
		case 2:
			return new ClienteDAO();
		case 3:
			return new VentaDAO();			
		case 4:
			return new ProductoDTO_VentaDTO_DAO();		
			default:
				return null;				
		}
		
	}
	
}
