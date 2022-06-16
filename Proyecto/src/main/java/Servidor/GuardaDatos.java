
package Servidor;

public class GuardaDatos extends Thread{
    public GuardaDatos(){
        this.start();
    }

    //Hacemos que los datos de los usuarios se guarden cada 15 segundos, para tener a cada momento
    //los datos mas actualizados posibles
    @Override
    public void run(){
        while(true){
            try {
                Thread.sleep(15000);
                UsuarioDatosPersistentes.guardarUsuarios();
            }catch(InterruptedException ex){
                System.out.println(ex.getMessage());
            }
        }
    }
}