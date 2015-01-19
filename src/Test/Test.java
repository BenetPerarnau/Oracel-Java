package Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;


//DAO
import DAO.InterfaceDAO;
import DAO.ObjectDAOFactory;
import DAO.ProductoDAO;
import DAO.ProductoDTO_VentaDTO_DAO;
import DAO.VentaDAO;
//DTO
import DTO.ClienteDTO;
import DTO.ProductoDTO;
import DTO.VentaDTO;
import DTO.ProductoDTO_VentaDTO;

public class Test {
		
	private static InterfaceDAO<ClienteDTO> c;
	private static InterfaceDAO<ProductoDTO> p;
	private static InterfaceDAO<VentaDTO> v;
	private static InterfaceDAO<ProductoDTO_VentaDTO> pv;
	
	public static void main(String[]args){
		p=ObjectDAOFactory.getDAO((byte)1);
		c=ObjectDAOFactory.getDAO((byte)2);
		v=ObjectDAOFactory.getDAO((byte)3);
		pv=ObjectDAOFactory.getDAO((byte)4);
		byte op=0;
		do{
			menuP();
			try{
				BufferedReader stdin=new BufferedReader(new InputStreamReader(System.in));
				op=Byte.parseByte(stdin.readLine());
				switch(op){
				case 1:
					verClientes();
					break;
				case 2:
					verProductos();
					break;
				case 3:
					insertVentas(stdin);
					break;
				case 4:
					consularVentasDeCliente(stdin);
					break;
				case 5:
					createTables();
					break;
				case 6:
					System.out.println("Bye.");
					break;
					default:
						System.out.println("Opción no válida.");
						break;
				}
			}catch(IOException e){
				e.printStackTrace();
			}catch(NumberFormatException e){
				System.out.println("Se espera un valor numerico del 1 al 6.");
			}catch(NullPointerException e){
				
			} catch (SQLException e) {
				System.out.println("ERROR SQL!!");
				while(e!=null){
					System.out.println("SQL State => "+e.getSQLState());
					System.out.println("Error Code => "+e.getErrorCode());
					System.out.println("Message => "+e.getMessage());
					Throwable t=e.getCause();
					while(t!=null){
						System.out.println("Cause => "+t);
						t=t.getCause();
					}
					e=e.getNextException();
				}
			}
		}while(op!=6);
	}
	//CASO USO 1 ‘Ver clientes’: 
	//La aplicación mostrará por consola los datos de todos los clientes registrados en la base de datos 
	//y volverá al menú principal. En caso de no existir clientes informará al usuario.
	public static void verClientes() throws SQLException{//case 1
			if(c.exist()){
				ArrayList<ClienteDTO> array=c.readAll();
				if(array.size()>0){
					for( ClienteDTO fila:array){
						System.out.println(fila.toString());
					}
				}else{
					System.out.println("La tabla Clientes esta vacia.");
				}
			}else{
				System.out.println("La tabla Clientes no existe.");
			}
	}
	/*
	CASO USO 2 ‘Ver productos’: 
	La aplicación mostrará por consola los datos de todos los productos registrados en la base de datos 
	y volverá al menú principal. En caso de no existir productos informará al usuario. 
	*/
	public static void verProductos() throws SQLException{//case 2
			if(p.exist()){
				ArrayList<ProductoDTO> array=p.readAll();
				if(array.size()>0){
					for( ProductoDTO fila:array){
						System.out.println(fila.toString());
					}
				}else{
					System.out.println("La tabla Productos esta vacia.");
				}
			}else{
				System.out.println("La tabla Productos no existe.");
			}
	}
	/*
	CASO USO 3 ‘Insertar ventas’: 
	El programa recibirá los siguientes datos del usuario: 
	CIF cliente, identificador del producto, cantidad producto. 
	Antes de insertar la venta se deberán realizar las siguientes comprobaciones:
		• El CIF de cliente debe existir en la tabla CLIENTES
		• El identificador de producto debe existir en la tabla PRODUCTOS
		• La cantidad debe ser >0 y > stockactual del producto                              ???
		• La fecha de venta es la fecha actual.
	Una vez insertada la fila en la tabla visualizar un mensaje indicándolo. 
	Si no se ha podido realizar la inserción visualizar el motivo 
	(no existe el cliente, no existe el producto, cantidad menor o igual a 0, no hay stock...).
	En ambos casos, a continuación se regresará al menú principal.
	*/
	public static void insertVentas(BufferedReader stdin) throws IOException, SQLException{
		if(c.exist()){
		try {
			System.out.println("Nuevo Registro en Ventas:");
			System.out.print("CIF del Cliente => ");
			String cif=stdin.readLine();
			if(c.read(cif)!=null){//El CIF de cliente debe existir en la tabla CLIENTES
				System.out.print("ID del Producto => ");
				int idp=Integer.parseInt(stdin.readLine());
				if(p.read(idp)!=null){//El identificador de producto debe existir en la tabla PRODUCTOS
					System.out.print("Cantidad => ");
					int cantidad=Integer.parseInt(stdin.readLine());//cantidad que quiere comprar
					int stockBBDD=((ProductoDAO) p).getStockProducto(idp);//obtener cantidad que hay disponible
					if(cantidad<=stockBBDD && cantidad>0){//La cantidad debe ser >0 y <= stockactual del producto  
					
						System.out.println("Obteniendo fecha actual...");
						Calendar fecha=new GregorianCalendar();//La fecha de venta es la fecha actual.
						SimpleDateFormat formatoDeFecha = new SimpleDateFormat("yyyy/MM/dd");
						java.util.Date  utilDate =  formatoDeFecha.parse(fecha.get(Calendar.YEAR)+""
																   + "/"+fecha.get(Calendar.MONTH)+1+""
																   + "/"+fecha.get(Calendar.DAY_OF_MONTH));
						java.sql.Date newFecha=new java.sql.Date(utilDate.getTime());
						System.out.println("Fecha => "+newFecha);
						
						int nuevoStock=stockBBDD-cantidad;//calculamos el nuevo Stock
						
						/*Inicio de la transacción:
						 *	1) actualizar el stock en la tabla productos.
						 *	2) insertar el registro en la tabla ventas.
						 *	3) insertar en la tabla Productos_Ventas la PK del producto y la PK del registro en Ventas.
						 *Si las tres operaciones son true se hara el commit en la bbdd
						*/
						int idv=((VentaDAO)v).getNextID();//retorna el proximo número que toca en la tabla Ventas
						if(((VentaDAO)v).insertVentaRestandoStock(nuevoStock, idp,
								new VentaDTO(idv,newFecha, cantidad, cif), 
								new ProductoDTO_VentaDTO(0, idp, idv))){//el 0 da = pq en la tabla es auto increment pondra el numero que toque
							System.out.println("El registro ha sido añadido en la tabla Ventas y el Stock del producto ha diso modificado.");
						}else{
							System.out.println("No se ha añadido ningun registro en la bbdd.");
						}		
						
					}else{
						if(cantidad<=0){System.out.println("La cantidad no puede ser 0 o negativa.");}
						else{System.out.println("La cantidad no puede ser superior al stock del producto");}
					}					
				}else{
					System.out.println("El identificador del Producto no esixte.");
				}
			}else{
				System.out.println("No existe ningun cliente en con el CIF "+cif);
			}
		}catch (NumberFormatException e) {
			e.printStackTrace();
		}catch (ParseException e) {
			e.printStackTrace();
		}
		}else{
			System.out.println("No existe la tabla Clientes.");
		}
	}
	   /*
	    *CASO USO 4 ‘Consultar ventas de un cliente’:
	    *El programa recibirá como dato el CIF del cliente. 
	    *El programa visualizará: 
	    *Si el cliente no existe, o si no hay ventas asignadas al cliente se informará al usuario.
	    *Cuando se termine la operación se regresará al menú principal
	    */
	public static void consularVentasDeCliente(BufferedReader stdin) throws IOException, SQLException, NullPointerException{
	//lo primero sera mostrar todos lo empleados disponibles para asi poder ver su CIF para poder buscar las ventas relacionadas		
			if(c.exist()){
				ArrayList<ClienteDTO> array=c.readAll();
				if(array.size()>0){
					System.out.println("Clientes en la BBDD:");
					for(ClienteDTO fila:array){
						System.out.println(fila.toString());
					}
					System.out.print("CIF => ");
					String cif=stdin.readLine();
					//mirar en la bbdd si hay un cliente con el cif ingresado
					if(c.read(cif)!=null){//hay cliente con este cif			
						ArrayList<VentaDTO> array2=((VentaDAO) v).selectVentasByClinete(cif);//consultar en la tabla ventas si hay referencia a este cliente
						if(array2.size()>0){//se han encontrado coincidencias 
							System.out.println("Ventas del Cliente con CIF "+cif+":");
							float total=0;
							for(VentaDTO reg:array2){
								ProductoDTO_VentaDTO PV=pv.read(reg.getIdVenta());
								ProductoDTO P=p.read(PV.getIdProducto());
								System.out.println("VENTA : "+reg.getIdVenta());//id de venta 
								System.out.println("\tPRODUCTO "+P.getDesc());//descripción
								System.out.println("\tPVP: "+P.getPvp());//precio de venta unidad
								System.out.println("\tCANTIDAD: "+reg.getCantidad());//cantidad adquirida
								System.out.println("\tIMPORTE: "+(reg.getCantidad()*P.getPvp())+"€");//total pagado
								total+=reg.getCantidad()*P.getPvp();
							}
							System.out.println("Nº TOAL VENTAS: "+array.size());
							System.out.println("IMPORTE TOTAL: "+total+"€");
						}else{//no tiene ventas este cliente
							System.out.println("el Cliente con CIF "+cif+" no tiene ninguna venta asociada.");
						}
					}else{//no hay cliente con este cif
							System.out.println("No Existe ningun Cliente con CIF "+cif);
					}				
				}else{
					System.out.println("No hay registros en la tabla Clientes.");
				}
			}else{
				System.out.println("La tabla Clientes no existe.");
			}
	}
	/*
	 * crear las tablas en la bbdd si estas no existen
	 */
	public static void createTables() throws SQLException{						
			boolean clientesVacio=false;
			boolean productosVacio=false;
			if(!c.exist()){
				c.create();
				clientesVacio=true;
				System.out.println("La tabla Clientes se ha creado correctamente.");	
			}else{
				System.out.println("Ya existe la tabla Clientes en la BBDD.");
			}
			if(!p.exist()){
				p.create();
				productosVacio=true;
				System.out.println("La tabla Productos se ha creado correctamente.");
			
			}else{
				System.out.println("Ya existe la tabla Productos en la BBDD.");
			}
			if(!v.exist()){
				v.create();
				System.out.println("La tabla Ventas se ha creado correctamente.");
				
			}else{
				System.out.println("Ya existe la tabla Ventas en la BBDD.");
			}
			if(!pv.exist()){
				pv.create();
				System.out.println("La tabla auxiliar Productos_Ventas se ha creado correctamente.");							
			}else{
				System.out.println("Ya existe la tabla Productos_Ventas en la BBDD.");
			}
			///inserts
			if(clientesVacio && productosVacio){insertsAuto();}
	}
	public static void insertsAuto() throws SQLException{
		//insertar 20 productos 
		for(int i=0; i<20; i++){			
			int id=i;
			String desc="Esta es la descripcion del producto con id "+i;
			int stock=(int)(Math.random()*100)+1;//de 1 a 100 cantidad
			float pvp=((float)(Math.random()*1000))+1;// de 1 a 1000€
			p.insert(new ProductoDTO(id, desc, stock, pvp));			
		}
		//insertar 10 clientes
		for (int i=0; i<10; i++){
			String cif="00000000"+(char)(i+65);
			String name="Nombre "+i;
			String direc="Direccion del cliente "+i;
			String pob="Poblacion del cliente "+i;
			String telef="93452312"+i;
			c.insert(new ClienteDTO(cif,name,direc,pob,telef));
		}
		//insertar x ventas por cada cliente sin pasar por la tabla productos para restar el stock ni insertando nada en la tabla Productos-Ventas
		/*
		for(int i=1; i<=10; i++){
			int aux=(int)(Math.random()*10);////////////////////////////
			for (int j=0; j<aux; j++){
				int idVenta=0;//auto increment en la bbdd
				Calendar fecha=new GregorianCalendar();//La fecha de venta es la fecha actual.
				SimpleDateFormat formatoDeFecha = new SimpleDateFormat("yyyy/MM/dd");
				java.util.Date utilDate=null;
				try {
					utilDate = formatoDeFecha.parse(fecha.get(Calendar.YEAR)+"/"+fecha.get(Calendar.MONTH)+1+"/"+fecha.get(Calendar.DAY_OF_MONTH));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				java.sql.Date newFecha=new java.sql.Date(utilDate.getTime());
				int cantidad=(int)(Math.random()*100+1);
				String clienteFK="00000000"+(char)(i+64);
				v.insert(new VentaDTO(idVenta, newFecha, cantidad, clienteFK));
			}
		}*/
	}
	public static void menuP(){
		System.out.println("–––––––––––––––––––––––––––––––––––––––––");
		System.out.println("| 1.- Ver clientes                      |");
		System.out.println("| 2.- Ver productos                     |");
		System.out.println("| 3.- Insertar ventas                   |");
		System.out.println("| 4.- Consultar ventas de un cliente    |");
		System.out.println("| 5.- Crear Tablas                      |");
		System.out.println("| 6.- Salir                             |");
		System.out.println("–––––––––––––––––––––––––––––––––––––––––");
		System.out.print("OP => ");
	}
}
