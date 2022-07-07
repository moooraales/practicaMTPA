package Servidor;

public class inicioSesionException extends Exception{

    @Override
    public String toString(){

        return "Error al iniciar sesion";
    }
}