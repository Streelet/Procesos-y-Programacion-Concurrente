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

public class Proceso1 extends Thread {
    public void run() {
        try {
            Prioridad.semaforo1.acquire();
            System.out.println("Proceso 1 ejecutandose.");
            Thread.sleep(1000);
            System.out.println("Proceso 1 finalizado.");
            Prioridad.semaforo3.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

