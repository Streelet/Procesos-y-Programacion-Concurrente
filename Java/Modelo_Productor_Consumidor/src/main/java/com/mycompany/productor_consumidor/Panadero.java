/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.productor_consumidor;



/**
 *Representa a un panadero que hornea pan y lo coloca en un almacen compartido, es un hilo.
 * @author erick
 */



public class Panadero extends Thread {
    /** 
     * Instancia del almacen donde guarda el pan
     */
    private final Almacen almacen;
    /**
     * Identificador único
     */
    private final int idPanadero;
    /**
     * Variable de control para verificar si el hilo está ejecutandose
     * mediante hacerla volatile garantizamos que se obtenga el valor correcto de la variable desde la 
     * memoria principal.
     */
    private volatile boolean enFuncionamiento = true;
    /**
     * Contador de panes horneados por panadero
     */
    private int contadorPanes= 0;

    
    /**
     * Constructor de la clase
     * @param almacen
     * @param idPanadero 
     */
    public Panadero(Almacen almacen, int idPanadero) {
        this.almacen = almacen;
        this.idPanadero = idPanadero;
    }
    
    
    
   
  /**
 * Método que contiene la lógica principal del hilo del panadero.
 * Mientras la variable {@link #enFuncionamiento} sea true, el panadero horneará un nuevo pan
 * y lo colocará en el almacén, simulando un tiempo de horneado.
 * 
 * En caso de que el hilo sea interrumpido durante la espera, se captura la {@link InterruptedException}
 * y se propaga la interrupción al hilo actual.
 */
    

    @Override
    public void run(){
    try {
        while(enFuncionamiento){
            contadorPanes++;
            String tipoPan = "Pan #" + contadorPanes;
            almacen.hornearPan(tipoPan);
            Thread.sleep(500);
        }
        System.out.println("Panadero " + idPanadero + "dejó de hornear");
     }
     catch(InterruptedException e){
     Thread.currentThread().interrupt();
    }
    }
    
    public void detener(){
        this.enFuncionamiento=false;
        }
}


