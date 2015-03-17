package Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;

import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;
import DAO.FactoryDAO;
import Model.Conector;

public class Test {
	
	
	/*
	 * Enunciado:
	 * 
	 * 
	 * Crea una aplicacioÌ�n modular Java que permita las siguientes acciones de forma segura 
	 * sobre la base de datos creada (no es necesario que tenga GUI):
	 * 	1.- Ver clientes
	 * 	2.- Dar de alta cliente
	 * 	3.- Actualizar teleÌ�fono de cliente
	 * 	4.- Actualizar descuento de cliente
	 * 	5.- Salir
	 * Al entrar a la aplicacioÌ�n, se mostraraÌ� el MenuÌ� Principal con las acciones indicadas para que el usuario pueda escoger.
	 * 	CASO USO 1 â€˜Ver clientesâ€™: 
	 * 		La aplicacioÌ�n mostraraÌ� por consola los datos (CIF+Nombre y Apellidos+Descuento) 
	 * 		de todos los clientes registrados en la base de datos y volveraÌ� al menuÌ� principal.
	 * 		En caso de no existir clientes informaraÌ� al usuario.
	 * 	CASO USO 2 â€˜Dar de alta clienteâ€™: 
	 * 		el programa recibiraÌ� los datos del cliente (CIF, nombre, etc). 
	 * 		Recuerda que los uÌ�nicos campos obligatorios son CIF y nombre y apellidos, el resto son opcionales.
	 * 
	 * Una vez insertado el objeto en la tabla visualizar un mensaje informativo indicaÌ�ndolo. 
	 * Si no se ha podido realizar la insercioÌ�n mostrar un mensaje de error.
	 * En ambos casos, a continuacioÌ�n se regresaraÌ� al menuÌ� principal.
	 * 	
	 * 	CASO USO 3 â€˜Actualizar teleÌ�fono de clienteâ€™: el programa recibiraÌ� los datos del cliente (CIF). 
	 * 	Si el cliente ya dispone de teleÌ�fonos, se le daraÌ� a escoger cual quiere actualizar y se le solicitaraÌ� 
	 * 	el nuevo teleÌ�fono. Si el cliente no dispone de teleÌ�fonos, se le solicitaraÌ� el nuevo teleÌ�fono. 
	 * 	Si se ha podido realizar la operacioÌ�n de actualizacioÌ�n se mostraraÌ� un mensaje informativo, 
	 * 	en caso contrario se mostraraÌ� un mensaje de error. En ambos casos, a continuacioÌ�n se regresaraÌ� al menuÌ� principal.
	 * 	CASO USO 4 â€˜Actualizar descuento de clienteâ€™: el programa recibiraÌ� los datos del cliente (CIF)
	 * 	y el valor del descuento (un porcentaje) a actualizar. Si se ha podido realizar la operacioÌ�n de actualizacioÌ�n 
	 * 	se mostraraÌ� un mensaje informativo, en caso contrario se mostraraÌ� un mensaje de error.
	 * 	En ambos casos, a continuacioÌ�n se regresaraÌ� al menuÌ� principal.
	 * 
	 */
	 
	
	public static void main(String arg[]){
		byte op=-1;
		BufferedReader stdin=new BufferedReader(new InputStreamReader(System.in));
		do{
			try {
				op=showMenuP(stdin);
				
				switch(op){
				case 1:
					/*
					 * CASO USO 1 â€˜Ver clientesâ€™: 
					 * La aplicacioÌ�n mostraraÌ� por consola los datos (CIF+Nombre y Apellidos+Descuento) 
					 * de todos los clientes registrados en la base de datos y volveraÌ� al menuÌ� principal.
					 * En caso de no existir clientes informaraÌ� al usuario.
					 */
					try {
						ArrayList<STRUCT> data=FactoryDAO.getClassDAO(0).selectAll();
						for(STRUCT a: data){
							Object[] atr=a.getAttributes();
//							cif varchar(9),
//							nombre varchar(20),
//							apellido varchar(20),
//							apellido2 varchar(20),
//							direccion direccion_t,
//							decuento number(5,2),
//							telefonos array_telefonos
							if(atr!=null){
								System.out.println("Cliente {\"CIF\": \""+atr[0]+"\""
														  + "\"NOMBRE\": \""+atr[1]+"\""
														  + "\"APELLIDO1\": \""+atr[2]+"\""
														  + "\"APELLIDO2\": \""+atr[3]+"\""
														  + "");
							}
						}
					} catch (SQLException e) {
						e.printStackTrace();
						System.out.println("ESTAT => "+e.getSQLState());
						try {
							Thread.sleep(100);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					
					
					break;
				case 2:
					/*
					 * CASO USO 2 â€˜Dar de alta clienteâ€™: 
					 * el programa recibiraÌ� los datos del cliente (CIF, nombre, etc). 
					 * Recuerda que los uÌ�nicos campos obligatorios son CIF y nombre y apellidos, el resto son opcionales.
					 */					
					try {
						STRUCT client=makeClient(stdin);
						if(FactoryDAO.getClassDAO(0).insert(client)){
							System.out.println("Clinete añadido correctamente.");
						}else{
							System.out.println("El Cliente no ha sido añadido.");
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
					break;
				case 3:
					break;
				case 4:
					break;
				case 5:
					break;
					default:
						break;
				}
				
				
				
				
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
		}while(op!=5);
		
		
		
	}
	
	public static byte showMenuP(BufferedReader stdin) throws NumberFormatException, IOException{
		System.out.println("1.- Ver clientes");
		System.out.println("2.- Dar de alta cliente");
		System.out.println("3.- Actualizar teleÌ�fono de cliente");
		System.out.println("4.- Actualizar descuento de cliente");
		System.out.println("5.- Salir");
		System.out.print("OP => ");
		return Byte.parseByte(stdin.readLine());
		
	}
	
	public static STRUCT makeClient(BufferedReader stdin) throws IOException, SQLException{
//		TBLA
//		cif varchar(9),
//		nombre varchar(20),
//		apellido varchar(20),
//		apellido2 varchar(20),
//		direccion direccion_t,
//		decuento number(5,2),
//		telefonos array_telefonos
		String dni, name, ape1, ape2; //required
		String calle, ciudad, cod, provincia; //object Dirección
		float descuento; //descuento
		String [] tlf=new String[3];//telefonos
		do{
		System.out.print("DNI => ");
		dni=stdin.readLine();
		}while(dni.length()!=9);
		do{
		System.out.print("Nombre => ");
		 name=stdin.readLine();
		}while(name.length()==0);
		do{
		System.out.print("Apellido 1 => ");
		 ape1=stdin.readLine();
		}while(ape1.length()==0);
		do{
		System.out.print("Apellido 2 => ");
		ape2=stdin.readLine();
		}while(ape2.length()==0);
//		DIRECCIóN
//		calle varchar(20),
//		poblacion varchar(20),
//		cod_postal varchar(5),
//		provincia varchar(20)
		System.out.print("Calle => ");
		calle=stdin.readLine();
		System.out.print("Población => ");
		ciudad=stdin.readLine();
		System.out.print("Cod Postal => ");
		cod=stdin.readLine();
		System.out.print("Provincia => ");
		provincia=stdin.readLine();
		//
		System.out.print("Descuento => ");
		descuento=Float.parseFloat(stdin.readLine());
		//
		for(int i=1; i<=tlf.length; i++){
		System.out.print("Telefono "+i+" => ");
		tlf[i-1]=stdin.readLine();
		}

		//Dirección
		StructDescriptor structDireccion = StructDescriptor.createDescriptor("DIRECCION_T", Conector.getInstancia().getConexion());
        Object[] attributesDireccion = new Object[]{calle,ciudad,cod,provincia};
        //Telefonos
      	StructDescriptor structTelef = StructDescriptor.createDescriptor("ARRAY_TELEFONOS", Conector.getInstancia().getConexion());
        Object[] attributesTelef = new Object[]{tlf[0],tlf[1],tlf[2]};
		//Final Cliente
		StructDescriptor structCliente = StructDescriptor.createDescriptor("CLIENTE_T", Conector.getInstancia().getConexion());
        Object[] attributesCliente = new Object[]{dni,name, ape1,ape2,attributesDireccion,descuento,attributesTelef};
        oracle.sql.STRUCT client = new oracle.sql.STRUCT(structCliente, Conector.getInstancia().getConexion(), attributesCliente);
		
		return client;
	}

}
