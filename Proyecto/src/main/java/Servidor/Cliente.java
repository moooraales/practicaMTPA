package Servidor;

import Servidor.inicioSesionException;
import Servidor.notFoundException;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class Cliente extends Thread{
    private static ArrayList<Cliente> listaClientesActivos = new ArrayList<>();

    private Socket cliente;
    private Usuario usuario;


    public Cliente(){
    }
    
    public Cliente(Socket socketCliente){
        this.cliente = socketCliente;
        this.usuario = null;
        this.start();
    }

//Getters y setters
public static ArrayList<Cliente> getListaClientesActivos() {
    return listaClientesActivos;
}

public static void setListaClientesActivos(ArrayList<Cliente> listaClientesActivos) {
    Cliente.listaClientesActivos = listaClientesActivos;
}

public Socket getCliente() {
    return cliente;
}

public void setCliente(Socket cliente) {
    this.cliente = cliente;
}

public Usuario getUsuario() {
    return usuario;
}

public void setUsuario(Usuario usuario) {
    this.usuario = usuario;
}

public static Cliente buscarClienteLogueado(String nombreUsuario){
    ArrayList<Cliente> listaClientes = listarClientesLogueados();
    Cliente resultado = null;
    for(int i = 0 ; i < listaClientes.size() ; i++){
        if(listaClientes.get(i).getUsuario().getNombre().equals(nombreUsuario)){
            resultado = listaClientes.get(i);
        }
    }
    return resultado;
}

//Permite que el usuario inicie sesion en el sistema
public ArrayList<String> opcionLogin(String[] solicitud){
    ArrayList<String> respuesta = new ArrayList<>();
    Usuario usuarioEncontrado = Usuario.login(solicitud[1], solicitud[2]);
    if(usuarioEncontrado!=null){
        this.setUsuario(usuarioEncontrado);
        respuesta.add("true");
    }else{
        respuesta.add("false");
        respuesta.add(new inicioSesionException().toString());
    }
    return respuesta;
}

//Muestra los clientes que estan activos
public static ArrayList<Cliente> listarClientesLogueados(){
    ArrayList<Cliente> lista = new ArrayList<>();
    for(int i = 0 ; i < listaClientesActivos.size() ; i++){
        if(listaClientesActivos.get(i).getUsuario()!=null){
            lista.add(listaClientesActivos.get(i));
        }
    }
    return lista;
}

//Muestra clientes que han iniciado sesion en el sistema
public static ArrayList<Usuario> listarUsuariosLogueados(){
    ArrayList<Usuario> listaUsuarios = new ArrayList<>();
    ArrayList<Cliente> listaClientes = listarClientesLogueados();
    for(int i = 0 ; i < listaClientes.size() ; i++){
        listaUsuarios.add(listaClientes.get(i).getUsuario());
    }
    return listaUsuarios;
}

private void enviar(String mensaje) throws IOException{
    byte[] mensajeCoded = mensaje.getBytes();
    cliente.getOutputStream().write(mensajeCoded);
}

public void enviar(ArrayList<String> mensaje) throws IOException{
    String mensajeConcat="";
    for (String parametro : mensaje) {
        mensajeConcat+=parametro+";";
    }
    enviar(mensajeConcat);
}

public String[] recibir() throws IOException{
    byte[] respuestaCoded = new byte[255];
    String respuesta;
    String [] datos;
    cliente.getInputStream().read(respuestaCoded);
    respuesta = new String(respuestaCoded);
    datos = respuesta.split(";");
    return datos;
}

//Elimina los retos de un usuario y lo quita de clientes activos
public void desconectar() throws IOException{
    if(usuario!=null){
        usuario.eliminarRetos();
    }
    this.getCliente().close();
    listaClientesActivos.remove(this);
}

public ArrayList<String> opcionRegistrar(String[] solicitud){
    ArrayList<String> respuesta = new ArrayList<>();
    if(Usuario.registrar(solicitud[1],solicitud[2])){
        respuesta.add("true");
    }else{
        respuesta.add("false");
        respuesta.add(new RegistroException().toString());
    }
    return respuesta;
}

@Override
    public void run(){
        try{
            String [] solicitud;
            ArrayList<String> respuesta = new ArrayList<>();
            while(true){
                solicitud = recibir();
                respuesta.clear();
                switch(solicitud[0]){
                    case "registrar":
                        respuesta=opcionRegistrar(solicitud);
                        enviar(respuesta);
                        break;
                    case "login":
                        respuesta=opcionLogin(solicitud);
                        enviar(respuesta);
                        break;
                    case "listarUsuariosActivos":
                        respuesta=opcionListarUsuariosActivos();
                        enviar(respuesta);
                        break;
                    case "retarJugador":
                        respuesta=opcionRetarJugador(solicitud);
                        enviar(respuesta);
                        break;
                    case "listarRetos":
                        respuesta=opcionListarRetos();
                        enviar(respuesta);
                        break;
                    case "responderReto":
                        respuesta=opcionResponderReto(solicitud);
                        enviar(respuesta);
                        break;
                    /* case "jugar":
                        respuesta=opcionJugar(solicitud);
                        enviar(respuesta);
                        break;
                    case "actualizarMarcador":
                        respuesta=opcionActualizarMarcador(solicitud);
                        enviar(respuesta);
                        break;
                    case "rondasRestantes":
                        respuesta=opcionRondasRestantes(solicitud);
                        enviar(respuesta);
                        break; */
                    case "verRanking":
                        respuesta=opcionVerRanking();
                        enviar(respuesta);
                        break;
               }
            }
            
        } catch(IOException ex){
            if(ex.getMessage().equals("Conexion reseteada")){
                System.out.println("Conexion perdida con el cliente");
            }else if(ex.getMessage().equals("Socket cerrado")){
                System.out.println("Conexion finalizada con el cliente");
            }else{
                System.out.println("LOG: "+new errorException().toString()+ex.getMessage());
            }
        }
        try {
            this.desconectar();
        } catch (IOException ex) {
            System.out.println("LOG: "+new errorException().toString()+":"+ex.getMessage());
        }
        
    }

//Muestra los usuarios que han iniciado sesión, es decir, que están activos
//Sirve para ver a qué usuarios se les puede retar
public ArrayList<String> opcionListarUsuariosActivos(){
    ArrayList<String> respuesta = new ArrayList<>();
    ArrayList<Usuario> usuarios = listarUsuariosLogueados();
    if(usuarios.size()>0){
        respuesta.add("true");
        for(int i=0; i<usuarios.size();i++){
            if(!usuarios.get(i).equals(usuario)){
                respuesta.add(usuarios.get(i).getNombre());
            }                
        }
    }else{
        respuesta.add("false");
        respuesta.add(new notFoundException().toString());
    }
    return respuesta;
}


//Metodo para retar a otro jugador
public ArrayList<String> opcionRetarJugador(String[] solicitud) throws IOException{
    ArrayList<String> respuesta = new ArrayList<>();
    Cliente clienteRival = buscarClienteLogueado(solicitud[1]);
    if(clienteRival!=null){
        if(clienteRival.getCliente().equals(this.getCliente())){
            respuesta.add("false");
            respuesta.add(new selfRetoException().toString());
        }else{
            if(clienteRival.getUsuario().addReto(this.getUsuario())==true){
                respuesta.add("true");
            }else{
                respuesta.add("false");
                respuesta.add(new selfRetoException().toString());
            }
        }
    }else{
       respuesta.add("false");
       respuesta.add(new notFoundException().toString());
    }
    return respuesta;
}

//Metodo para listar los retos existentes
public ArrayList<String> opcionListarRetos() throws IOException{
    ArrayList<String> respuesta = new ArrayList<>();
    Reto reto;
    if(this.getUsuario().getListaRetos().isEmpty()){
        respuesta.add("false");
        respuesta.add(new notFoundException().toString());
    }else{
        respuesta.add("true");
        for(int i=0;i<this.getUsuario().getListaRetos().size();i++){
            reto = this.getUsuario().getListaRetos().get(i);
            if(reto.getLocal().equals(this.getUsuario())){
                respuesta.add(reto.getVisitante().getNombre());
            }else{
                respuesta.add(reto.getLocal().getNombre());
            }
            respuesta.add(this.getUsuario().getListaRetos().get(i).getEstado().toString());
        }
    }
    return respuesta;
}

//Metodo para responder a un reto pendiente
public ArrayList<String> opcionResponderReto(String[] solicitud) throws IOException{
    ArrayList<String> respuesta = new ArrayList<>();
    Cliente rival = Cliente.buscarClienteLogueado(solicitud[1]);
    if(rival!=null){
        Reto reto = this.getUsuario().buscarReto(rival.getUsuario());
        if(this.getUsuario().equals(reto.getVisitante())){
            respuesta.add("true");
            if(solicitud[2].equals("true")){
                reto.setEstado(estadoReto.Aceptado);
            }else{
                reto.eliminar();
            }
        }else{
            respuesta.add("false");
            respuesta.add("No puedes responder a tu propio reto");
        }
    }else{
        respuesta.add("false");
        respuesta.add("El usuario no esta disponible");
    }
    return respuesta;
}

//IMPORTANTE: me esta dando error buscarReto, me dice que no se le puede meter un String pero no entiendo por que
//Metodo para actualizar el marcador
/* public ArrayList<String> opcionActualizarMarcador(String[] solicitud) throws IOException{
    ArrayList<String> respuesta = new ArrayList<>();
    Reto reto = this.getUsuario().buscarReto(solicitud[1]);
    if(reto == null){
        respuesta.add("false");
        respuesta.add(new notFoundException().toString());
    }else{
        respuesta.add("true");
        respuesta.add(String.valueOf(reto.getSala().getPuntosLocal()));
        respuesta.add(String.valueOf(reto.getSala().getPuntosVisitante()));
    }
    return respuesta;
} */

//Metodo para ver las rondas restantes
/* public ArrayList<String> opcionRondasRestantes(String[] solicitud) throws IOException{
    ArrayList<String> respuesta = new ArrayList<>();
    Reto reto = this.getUsuario().buscarReto(solicitud[1]);
    int jugadas,restantes;
    
    if(reto == null){
        respuesta.add("false");
        respuesta.add(new notFoundException().toString());
    }else{
        jugadas = reto.getSala().getPuntosLocal() + reto.getSala().getPuntosVisitante();
        restantes = reto.getSala().getRondas() - jugadas;
        respuesta.add("true");
        respuesta.add(String.valueOf(restantes));
    }
    return respuesta;
} */

//Metodo para mostrar el ranking al cliente
public ArrayList<String> opcionVerRanking(){
    ArrayList<String> respuesta = new ArrayList<>();
    ArrayList<Usuario> usuarios = Ranking.getRanking().getUsuarios();
    if(usuarios.size()>0){
        respuesta.add("true");
        for(int i=0;i<usuarios.size();i++){
            respuesta.add(usuarios.get(i).getNombre());
            respuesta.add(String.valueOf(usuarios.get(i).getPartidasGanadas()));
        }
    }else{
        respuesta.add("false");
        respuesta.add("No hay datos disponibles");
    }
    return respuesta;
}

    
}
