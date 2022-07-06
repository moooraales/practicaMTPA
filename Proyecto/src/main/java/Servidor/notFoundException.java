package Servidor;
//Excepcion que salta cuando no hay usuarios en la lista de usuarios logeados
public class notFoundException extends Exception{

    @Override
    public String toString(){
        return "No hay usuarios logeados";
    }

}