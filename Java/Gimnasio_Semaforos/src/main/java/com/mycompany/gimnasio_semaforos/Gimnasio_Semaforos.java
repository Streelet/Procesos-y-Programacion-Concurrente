/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.gimnasio_semaforos;

import java.util.concurrent.Semaphore;

/**
 *
 * @author erick
 */
public class Gimnasio_Semaforos {

     static Semaphore pesasDisponibles = new Semaphore(3);
     static Semaphore barraDominadasDisponible = new Semaphore(1);
    
    public static void main(String[] args) {
        
        
       
        for (int i = 1; i <= 5; i++) {
            new Thread(new Usuario(i)).start();
            
            
        }
    }
    
    static class Usuario extends Thread {
        private int idUsuario;
        
        public Usuario(int idUsuario){
            this.idUsuario= idUsuario;
        }
        
        @Override
        public void run(){
        try{
            System.out.println("Usuario " + idUsuario + " llega al gimnasio");
           
            System.out.println("Usuario " + idUsuario + " intenta usar las pesas.");
            pesasDisponibles.acquire(); // Intentar adquirir un permiso para las pesas
           
            System.out.println("Usuario " + idUsuario + " comienza a levantar pesas.");
            Thread.sleep(2000);
             
           System.out.println("Usuario " + idUsuario + " termina de levantar pesas.");
           pesasDisponibles.release(); //Deja de usar las pesas
           
           
           System.out.println("Usuario " + idUsuario + " intenta usar la barra de dominadas.");
           barraDominadasDisponible.acquire(); // Intentar adquirir un permiso para la barra de dominadas
           
          System.out.println("Usuario " + idUsuario + " comienza a hacer dominadas.");
          Thread.sleep(1500); // Simular tiempo haciendo dominadas (1.5 segundos)
          
          System.out.println("Usuario " + idUsuario + " termina de hacer dominadas.");
          barraDominadasDisponible.release(); // Liberar el permiso para que otro usuario pueda usar la barra
          
          System.out.println("Usuario " + idUsuario + " se va del gimnasio.");
          
          
        }
        catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
        }
    }
}
