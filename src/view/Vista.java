package view;

import controller.Controlador; // Importa la clase Controlador
import model.Cliente; // Importar Cliente para lista
import java.util.Scanner; // Para leer la entrada del usuario
import java.util.List;  // Importar lista
public class Vista {

    private Controlador controlador; // Referencia al controlador
    private Scanner scanner; // Objeto para leer la entrada del usuario
    
    // Constructor de la vista que recibe el controlador
    public Vista(Controlador controlador){
        this.controlador = controlador;
        this.scanner = new Scanner(System.in); // Se inicializa el scanner
    }
    
    public void mMostrarMenuPrincipal(){
        int opcion = -1; // Se inicialliza opcion con un valor invalido
        while (opcion != 0) {
            System.out.println("MENU PRINCIPAL");
            System.out.println("1. Crear nuevo cliente");
            System.out.println("2. Lista de clientes");
            System.out.println("3. Crear registrador para cliente"); // NUEVA OPCION
            // mas adelante se añaden el resto de opciones 

            System.out.println("0. Salir");
            System.out.println("Seleccione una opcion: ");

            try{
                opcion = Integer.parseInt(scanner.nextLine()); // Se lee la opcion ingresada por el usuario
                switch (opcion){
                    case 1:
                        mMostrarMenuCrearCliente(); // Llamamos al metodo para crear un nuevo cliente
                        break;
                    case 2: 
                        mMostrarListaClientes(); // Llamamos al metodo para mostrar la lista de clientes
                        break;
                    case 3: 
                        mMostrarMenuCrearRegistrador(); // Llamamos al metodo para crear un nuevo registrador
                        break;
                    case 0:
                        System.out.println("Opcion no valida, intente de nuevo");
                        break;      
                    default:
                        System.out.println("Opcion no valida, intente de nuevo");
                        break;
                }
            } catch (NumberFormatException e){
                System.out.println("Error: Ingrese un numero valido");
                opcion = -1; // Resetea la opcion para el bucle continue
            } catch (Exception e){
                System.out.println("Ocurrio un error: " + e.getMessage());
                opcion = -1; // Resetea la opcion para el bucle continue
            }
            // Pausa simple para que el usurasio pueda leer antes de mostrar el menu 

            if (opcion != 0) {
                System.out.println("Presione enter para continuar");
                scanner.nextLine();
            }
        }
        scanner.close(); // Cerramos el Scanner al salir del bucle (Elegir 0)
        
    }

    // Metodo para solicitar los datos del nuevo cliente al usuario
    
    public void mMostrarMenuCrearCliente(){
        System.out.println("CREAR NUEVO CLIENTE");

        try {System.out.println("Ingrese el numero de identificacion: ");
        long numId = Long.parseLong(scanner.nextLine());
        System.out.println("Ingrese el tipo de identificacion: ");
        String tipoId = scanner.nextLine();
        System.out.println("Ingrese el correo electronico: ");
        String correo = scanner.nextLine();
        System.out.println("Ingrese la direccion: ");
        String direccion = scanner.nextLine();

        // Llamamos al metodo del controlador para crear al cliente
        controlador.mCrearCliente(numId, tipoId, correo, direccion);
        System.out.println("Cliente añadido desde la vista");
        } catch (NumberFormatException e) {
            System.out.println("Error al crear el cliente");
        } catch (Exception e) {
            System.out.println("Error al crear el cliente" + e.getMessage());
        }
        
    }
    
    public void mMostrarListaClientes(){
        System.out.println("Lista de clientes: ");
        List<Cliente> clientes = controlador.mGetListaClientes(); // Obtiene la lista del controlador
        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados");
        } else {
            // Itera sobre la lista e imprime cada cliente (Usando el metodo toString)
            for (int i = 0; i < clientes.size(); i++){
                System.out.println((i + 1) + ". " + clientes.get(i).toString());
            }
        }
    }


    public void mMostrarMenuCrearRegistrador(){
        System.out.println("Crear nuevo registrador ");

        // 1. Mostrar clientess para que el usuario elija 
        System.out.println("Clientes disponibles: ");
        mMostrarListaClientes(); // Reutilizamos el metodo para mostrar la lista de clientes
        List<Cliente> clientes = controlador.mGetListaClientes();
        if(clientes.isEmpty()){
            System.out.println("No hay clientes para crear un registrador");
            return;
        }

        try {
            // 2. Pedir al usuario que seleccione un cliente, mediante ID
            System.out.println("Ingrese el ID del cliente al que desea anadir el registrador: ");
            long idClienteSeleccionado = Long.parseLong(scanner.nextLine());

            // 3. Pedir los datos del nuevo registrador
            System.out.println("Ingrese el ID del cliente al que desea añadir el registrador: ");
            long idRegistrador = Long.parseLong(scanner.nextLine());
            System.out.println("Ingrese la direccion del registrador: ");
            String direccion = scanner.nextLine();
            System.out.println("Ingrese la ciudad del registrador: ");
            String ciudad = scanner.nextLine();

            // 4. llamar al controlador para añador el registrador 
            boolean exito = controlador.mCrearRegistradorParaCliente(idClienteSeleccionado, idRegistrador, direccion, ciudad);
            if (exito){
                System.out.println("Registrador creado exitosamente" + idClienteSeleccionado);
            } else {
                System.out.println("Error: No se pudo añádir el registrador, verifique el ID");
            }
            

        } catch (NumberFormatException e){
            System.out.println("Error, ingrese numeros validos");
        } catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
        }

    }









}
