package com.mycompany.productor_consumidor_monitores;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author erick
 */
import java.util.Random;

public class Productor extends Thread {
    private CintaTransportadora cintaTransportadora; // Nombre más descriptivo
    private Random random = new Random();
    private String[] nombresJuguetes = {"Pelota", "Muñeca", "Coche", "Robot", "Oso"};

    public Productor(CintaTransportadora cintaTransportadora) {
        this.cintaTransportadora = cintaTransportadora;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 10; i++) { // Ensambla 10 juguetes
                String juguete = nombresJuguetes[random.nextInt(nombresJuguetes.length)]; // Elige un juguete al azar
                cintaTransportadora.colocarJuguete(juguete);
                Thread.sleep(random.nextInt(500)); // Espera un tiempo aleatorio para ensamblar
            }
            System.out.println("Operario de ensamblaje terminó su turno.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}