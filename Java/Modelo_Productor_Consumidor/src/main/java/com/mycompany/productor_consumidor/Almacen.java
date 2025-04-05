/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.productor_consumidor;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import javax.swing.SwingUtilities;

/**
 *
 * @author erick
 */
public class Almacen {

    /**
     * La cola que representa la bandeja donde se almacena el pan.
     */
    private final Queue<String> bandejaDePan;
    /**
     * La capacidad del buffer
     */
    private final int capacidad;
    /**
     * Espacios libres en el buffer
     */
    private final Semaphore espaciosVacios;
    /**
     *Disponibles para tomar
     */
    private final Semaphore panesListos;
    /**
     * Mutex que evita que se acceda a la sección crítica simultaneamente.
     */
    private final Semaphore accesoExclusivo;

    /**
     * Referencia a la interfaz gráfica para poder actualizarla.
     */
    private PanaderoClienteGUI gui;

    /**
     * Constructor de la clase Almacen. Inicializa la capacidad y los semáforos.
     *
     * @param capacidad La capacidad máxima del almacén.
     */
    public Almacen(int capacidad) {
        this(capacidad, null); // Llama al nuevo constructor con null para la GUI
    }

    /**
     * Constructor de la clase Almacen que también recibe una referencia a la GUI.
     * Inicializa la capacidad, los semáforos y la referencia a la GUI.
     *
     * @param capacidad La capacidad máxima del almacén.
     * @param gui       La instancia de la interfaz gráfica PanaderoClienteGUI.
     */
    public Almacen(int capacidad, PanaderoClienteGUI gui) {
        this.capacidad = capacidad;
        this.bandejaDePan = new LinkedList<>();
        this.espaciosVacios = new Semaphore(capacidad);
        this.panesListos = new Semaphore(0);
        this.accesoExclusivo = new Semaphore(1);
        this.gui = gui;
    }

    /**
     * Permite que el panadero hornee un pan y se coloque en la bandeja.
     * Este método debe estar bloqueado si la bandeja está llena.
     *
     * @param pan El tipo de pan a hornear
     * @throws InterruptedException Si el hilo actual es interrumpido mientras espera.
     */
    public void hornearPan(String pan) throws InterruptedException {
        //Primero asegurar el espacio vacío
        espaciosVacios.acquire();
        //Luego proceder a tomar control de la sección crítica y ejecutar el método
        accesoExclusivo.acquire();
        bandejaDePan.offer(pan);
        System.out.println("El panadero horneó : " + pan + "\n" + "Bandeja: \n " + bandejaDePan);

        accesoExclusivo.release();
        panesListos.release();

        if (gui != null) {
            SwingUtilities.invokeLater(() -> gui.actualizarInterfaz());
        }
    }

    /**
     * Permite comprar panes siempre y cuando haya disponibles, de no ser así
     * se bloquea el acceso al método.
     *
     * @return El tipo de pan comprado
     * @throws InterruptedException
     */
    public String comprarPan() throws InterruptedException {
        //Asegurarnos de que hay pan disponible
        panesListos.acquire();
        //Se toma el control de la sección crítica
        accesoExclusivo.acquire();

        String pan = bandejaDePan.poll() ;
        System.out.println("Un cliente compró: " + pan + "\n" + "Bandeja: \n " + bandejaDePan);

        accesoExclusivo.release();
        espaciosVacios.release();

        if (gui != null) {
            SwingUtilities.invokeLater(() -> gui.actualizarInterfaz());
        }
        return pan;
    }

    /**
     * Devuelve la cantidad actual de panes en el almacén.
     *
     * @return El número de panes en la bandeja.
     */
    public int getCantidadPanes() {
        return bandejaDePan.size();
    }

    /**
     * Devuelve una representación en cadena del contenido actual de la bandeja.
     *
     * @return Una cadena con los panes en la bandeja.
     */
    public String getContenidoBandeja() {
        return bandejaDePan.toString();
    }

 
}