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

public class Proceso3 extends Thread {
    public void run() {
        try {
            Prioridad.semaforo3.acquire(2); // Espera que Proceso 4 y Proceso 1 terminen
            System.out.println("Proceso 3 ejecutandose.");
            Thread.sleep(1000);
            System.out.println("Proceso 3 finalizado.");
            Prioridad.semaforo2.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
