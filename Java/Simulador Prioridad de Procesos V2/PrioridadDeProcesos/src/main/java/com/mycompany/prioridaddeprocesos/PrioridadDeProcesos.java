/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.prioridaddeprocesos;

import java.util.concurrent.Semaphore;

/**
 * Programa que demuestra la prioridad de procesos (simulada con hilos) utilizando semáforos
 * para controlar el orden de ejecución de cinco procesos (P1 a P5).
 *
 * El orden de ejecución definido es el siguiente:
 *
 * P1 comienza primero.
 * P2 puede comenzar independientemente de P1.
 * P3 solo comienza después de que tanto P1 como P2 hayan terminado.
 * P4 puede comenzar independientemente de P1, P2 y P3.
 * P5 solo comienza después de que P4 haya terminado.
 *
 * @author erick
 */


public class PrioridadDeProcesos {

    /** Semáforo para señalar que el Proceso 1 ha terminado. */
    static Semaphore p1Terminado = new Semaphore(0);
    /** Semáforo para señalar que el Proceso 2 ha terminado. */
    static Semaphore p2Terminado = new Semaphore(0);
    /** Semáforo para señalar que el Proceso 4 ha terminado. */
    static Semaphore p4Terminado = new Semaphore(0);

    /**
     * Método principal que crea e inicia los hilos para cada uno de los procesos.
     * El orden de inicio de los hilos no determina el orden de ejecución, el cual
     * está controlado por los semáforos.
     *
     * @param args Argumentos de la línea de comandos (no se utilizan).
     */
    public static void main(String[] args) {
        // Crear los Runnables para cada proceso
        Thread proceso1 = new Thread(new Proceso1());
        Thread proceso2 = new Thread(new Proceso2());
        Thread proceso3 = new Thread(new Proceso3());
        Thread proceso4 = new Thread(new Proceso4());
        Thread proceso5 = new Thread(new Proceso5());

        // Iniciar los hilos
        proceso1.start();
        proceso2.start();
        proceso3.start();
        proceso4.start();
        proceso5.start();
    }

    /**
     * Representa el Proceso 1.
     * Ejecuta una tarea simulada y luego libera el semáforo para permitir que el Proceso 3 comience.
     */
    static class Proceso1 implements Runnable {
        /**
         * Método que contiene la lógica del Proceso 1.
         * Simula un trabajo y libera el semáforo `p1Terminado`.
         */
        @Override
        public void run() {
            System.out.println("P1: Iniciando...");
            try {
                Thread.sleep((long) (Math.random() * 2000));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("P1: Terminado. Liberando permiso para P3.");
            p1Terminado.release();
        }
    }

    /**
     * Representa el Proceso 2.
     * Ejecuta una tarea simulada y luego libera el semáforo para permitir que el Proceso 3 comience.
     */
    static class Proceso2 implements Runnable {
        /**
         * Método que contiene la lógica del Proceso 2.
         * Simula un trabajo y libera el semáforo `p2Terminado`.
         */
        @Override
        public void run() {
            System.out.println("P2: Iniciando...");
            try {
                Thread.sleep((long) (Math.random() * 2000));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("P2: Terminado. Liberando permiso para P3.");
            p2Terminado.release();
        }
    }

    /**
     * Representa el Proceso 3.
     * Espera a que tanto el Proceso 1 como el Proceso 2 terminen antes de ejecutar su propia tarea.
     */
    static class Proceso3 implements Runnable {
        /**
         * Método que contiene la lógica del Proceso 3.
         * Espera la liberación de los semáforos `p1Terminado` y `p2Terminado`
         * antes de simular su propio trabajo.
         */
        @Override
        public void run() {
            System.out.println("P3: Iniciando...");
            try {
                System.out.println("P3: Esperando a que P1 termine...");
                p1Terminado.acquire();
                System.out.println("P3: Permiso de P1 obtenido.");
                System.out.println("P3: Esperando a que P2 termine...");
                p2Terminado.acquire();
                System.out.println("P3: Permiso de P2 obtenido.");
                Thread.sleep((long) (Math.random() * 2000));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("P3: Terminado.");
        }
    }

    /**
     * Representa el Proceso 4.
     * Ejecuta una tarea simulada y luego libera el semáforo para permitir que el Proceso 5 comience.
     */
    static class Proceso4 implements Runnable {
        /**
         * Método que contiene la lógica del Proceso 4.
         * Simula un trabajo y libera el semáforo `p4Terminado`.
         */
        @Override
        public void run() {
            System.out.println("P4: Iniciando...");
            try {
                Thread.sleep((long) (Math.random() * 2000));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("P4: Terminado. Liberando permiso para P5.");
            p4Terminado.release();
        }
    }

    /**
     * Representa el Proceso 5.
     * Espera a que el Proceso 4 termine antes de ejecutar su propia tarea.
     */
    static class Proceso5 implements Runnable {
        /**
         * Método que contiene la lógica del Proceso 5.
         * Espera la liberación del semáforo `p4Terminado` antes de simular su propio trabajo.
         */
        @Override
        public void run() {
            System.out.println("P5: Iniciando...");
            try {
                System.out.println("P5: Esperando a que P4 termine...");
                p4Terminado.acquire();
                System.out.println("P5: Permiso de P4 obtenido.");
                Thread.sleep((long) (Math.random() * 2000));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("P5: Terminado.");
        }
    }
}