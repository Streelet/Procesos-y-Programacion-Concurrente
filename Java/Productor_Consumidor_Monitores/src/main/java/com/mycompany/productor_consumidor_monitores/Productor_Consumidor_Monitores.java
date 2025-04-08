/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.productor_consumidor_monitores;

/**
 *
 * @author erick
 */
public class Productor_Consumidor_Monitores {

    public static void main(String[] args) {
        int capacidadCinta = 5; // Define la capacidad de la cinta transportadora
        CintaTransportadora cinta = new CintaTransportadora(capacidadCinta);

        Productor productor = new Productor(cinta);
        Empacador empacador = new Empacador(cinta); // Ahora instanciamos un Empacador

        productor.start();
        empacador.start(); // Iniciamos el hilo del Empacador
    }
}
