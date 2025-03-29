/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.procesos;

/**
 *
 * @author erick
 */
import java.util.concurrent.Semaphore;


/*
Proceso 4 de Primero
Proceso 1 ira despues del 4
Proceso 2 ira despues del 3
Proceso 3 ira despues del 4 y 1*
*/

public class Prioridad {
   
    static Semaphore semaforo4 = new Semaphore(1); // Proceso 4 inicia primero
    static Semaphore semaforo1 = new Semaphore(0); // Proceso 1 depende del 4
    static Semaphore semaforo3 = new Semaphore(0); // Proceso 3 depende del 4 y 1
    static Semaphore semaforo2 = new Semaphore(0); // Proceso 2 depende del 3

    public static void main(String[] args) {
        
        new Proceso1().start();
        new Proceso2().start();
        new Proceso3().start();
        new Proceso4().start();
    }
}
