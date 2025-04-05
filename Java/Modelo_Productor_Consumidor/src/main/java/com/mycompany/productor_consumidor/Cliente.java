/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.productor_consumidor;

/**
 * Representa a un cliente que compra pan de un almacén compartido.
 * Implementa la interfaz Runnable para poder ejecutarse en un hilo separado.
 * @author erick
 */
public class Cliente implements Runnable {

    /**
     * El almacén compartido del cual el cliente compra el pan.
     */
    private final Almacen almacen;

    /**
     * El identificador único de este cliente.
     */
    private final int idCliente;

    /**
     * Indica si el cliente está actualmente en funcionamiento (comprando pan).
     * La palabra clave 'volatile' asegura la visibilidad entre hilos.
     */
    private volatile boolean enFuncionamiento = true;

    /**
     * Constructor de la clase Cliente.
     *
     * @param almacen   La instancia del almacén del cual el cliente comprará el pan.
     * @param idCliente El identificador único de este cliente.
     */
    public Cliente(Almacen almacen, int idCliente) {
        this.almacen = almacen;
        this.idCliente = idCliente;
    }

    /**
     * Método que se ejecuta cuando se inicia el hilo del cliente.
     * Contiene el bucle principal de compra de pan.
     */
    @Override
    public void run() {
        try {
            while (enFuncionamiento) {
                String panComprado = almacen.comprarPan();
                Thread.sleep(800); // Simula tiempo de compra
            }
            System.out.println("Cliente " + idCliente + " dejó de comprar.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Método para detener la ejecución del cliente.
     * Cambia el valor de la variable 'enFuncionamiento' a false.
     */
    public void detener() {
        this.enFuncionamiento = false;
    }
}