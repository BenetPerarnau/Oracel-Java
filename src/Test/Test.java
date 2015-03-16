package Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Test {
	
	
	/*
	 * Enunciado:
	 * 
	 * 
	 * Crea una aplicación modular Java que permita las siguientes acciones de forma segura 
	 * sobre la base de datos creada (no es necesario que tenga GUI):
	 * 	1.- Ver clientes
	 * 	2.- Dar de alta cliente
	 * 	3.- Actualizar teléfono de cliente
	 * 	4.- Actualizar descuento de cliente
	 * 	5.- Salir
	 * Al entrar a la aplicación, se mostrará el Menú Principal con las acciones indicadas para que el usuario pueda escoger.
	 * 	CASO USO 1 ‘Ver clientes’: 
	 * 		La aplicación mostrará por consola los datos (CIF+Nombre y Apellidos+Descuento) 
	 * 		de todos los clientes registrados en la base de datos y volverá al menú principal.
	 * 		En caso de no existir clientes informará al usuario.
	 * 	CASO USO 2 ‘Dar de alta cliente’: 
	 * 		el programa recibirá los datos del cliente (CIF, nombre, etc). 
	 * 		Recuerda que los únicos campos obligatorios son CIF y nombre y apellidos, el resto son opcionales.
	 * 
	 * Una vez insertado el objeto en la tabla visualizar un mensaje informativo indicándolo. 
	 * Si no se ha podido realizar la inserción mostrar un mensaje de error.
	 * En ambos casos, a continuación se regresará al menú principal.
	 * 	
	 * 	CASO USO 3 ‘Actualizar teléfono de cliente’: el programa recibirá los datos del cliente (CIF). 
	 * 	Si el cliente ya dispone de teléfonos, se le dará a escoger cual quiere actualizar y se le solicitará 
	 * 	el nuevo teléfono. Si el cliente no dispone de teléfonos, se le solicitará el nuevo teléfono. 
	 * 	Si se ha podido realizar la operación de actualización se mostrará un mensaje informativo, 
	 * 	en caso contrario se mostrará un mensaje de error. En ambos casos, a continuación se regresará al menú principal.
	 * 	CASO USO 4 ‘Actualizar descuento de cliente’: el programa recibirá los datos del cliente (CIF)
	 * 	y el valor del descuento (un porcentaje) a actualizar. Si se ha podido realizar la operación de actualización 
	 * 	se mostrará un mensaje informativo, en caso contrario se mostrará un mensaje de error.
	 * 	En ambos casos, a continuación se regresará al menú principal.
	 * 
	 */
	 
	
	public void main(String arg[]){
		byte op=-1;
		BufferedReader stdin=new BufferedReader(new InputStreamReader(System.in));
		do{
			try {
				op=showMenuP(stdin);
				
				switch(op){
				case 1:
					/*
					 * CASO USO 1 ‘Ver clientes’: 
					 * La aplicación mostrará por consola los datos (CIF+Nombre y Apellidos+Descuento) 
					 * de todos los clientes registrados en la base de datos y volverá al menú principal.
					 * En caso de no existir clientes informará al usuario.
					 */
					break;
				case 2:
					/*
					 * CASO USO 2 ‘Dar de alta cliente’: 
					 * el programa recibirá los datos del cliente (CIF, nombre, etc). 
					 * Recuerda que los únicos campos obligatorios son CIF y nombre y apellidos, el resto son opcionales.
					 */
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
	
	public byte showMenuP(BufferedReader stdin) throws NumberFormatException, IOException{
		System.out.println("1.- Ver clientes");
		System.out.println("2.- Dar de alta cliente");
		System.out.println("3.- Actualizar teléfono de cliente");
		System.out.println("4.- Actualizar descuento de cliente");
		System.out.println("5.- Salir");
		System.out.print("OP => ");
		return Byte.parseByte(stdin.readLine());
		
	}

}
