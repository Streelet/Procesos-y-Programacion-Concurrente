/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.productor_consumidor_monitores;

/**
 *
 * @author erick
 */
import java.util.LinkedList;
import java.util.Queue;

public class CintaTransportadora {
    private Queue<String> juguetesEnCinta;
    private int capacidad;

    public CintaTransportadora(int capacidad) {
        this.capacidad = capacidad;
        this.juguetesEnCinta = new LinkedList<>(); // Usamos LinkedList como implementación de Queue
    }

    public synchronized void colocarJuguete(String juguete) throws InterruptedException {
        while (juguetesEnCinta.size() == capacidad) {
            System.out.println("Cinta llena. Operario de ensamblaje esperando...");
            wait();
        }
        juguetesEnCinta.offer(juguete); // Añadimos al final de la cola
        System.out.println("Operario de ensamblaje coloca: " + juguete + " en la cinta.");
        notifyAll();
    }

    public synchronized String tomarJuguete() throws InterruptedException {
        while (juguetesEnCinta.isEmpty()) {
            System.out.println("Cinta vacia. Operario de empaquetado esperando...");
            wait();
        }
        String juguete = juguetesEnCinta.poll(); // Retiramos del frente de la cola
        System.out.println("Operario de empaquetado toma: " + juguete + " de la cinta.");
        notifyAll();
        return juguete;
    }
}