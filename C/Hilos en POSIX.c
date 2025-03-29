#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <stdbool.h>

#define N 1000
#define HILOS 5

int primos[N] = {0}; // Inicializar el arreglo con 0
pthread_t hilos[HILOS];
int conteos[HILOS] = {0};

// Función para verificar si un número es primo
bool es_primo(int num) {
    if (num < 2) return false;
    for (int i = 2; i * i <= num; i++) {
        if (num % i == 0) return false;
    }
    return true;
}

// Función que ejecutarán los hilos
void *evaluar_segmento(void *arg) {
    int id = *(int *)arg;
    int inicio = (N / HILOS) * id;
    int fin;
    
    if (id == HILOS - 1) {
        fin = N;
    } else {
        fin = inicio + (N / HILOS);
    }

    for (int i = inicio; i < fin; i++) {
        if (es_primo(i + 1)) {
            primos[i] = 1;
            conteos[id]++;
        }
    }
    pthread_exit(NULL);
}

int main() {
    int total_primos = 0;
    int ids[HILOS];

    // Crear los hilos
    for (int i = 0; i < HILOS; i++) {
        ids[i] = i;
        pthread_create(&hilos[i], NULL, evaluar_segmento, &ids[i]);
    }

    // Esperar a que los hilos terminen
    for (int i = 0; i < HILOS; i++) {
        pthread_join(hilos[i], NULL);
        total_primos += conteos[i];
    }

    // Imprimir los números primos encontrados
    printf("Números primos en el rango 1-1000:\n");
    for (int i = 0; i < N; i++) {
        if (primos[i]) {
            printf("%d ", i + 1);
        }
    }
    printf("\nTotal de primos: %d\n", total_primos);

    return 0;
}
