#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <semaphore.h>
#include <unistd.h>

#define MAX 10    
#define FIN -1    
#define MAX_VALORES 50  


//Declaracion de variables de control
int buffer[MAX];
int indice_escritura = 0;
int indice_lectura = 0;
int valores_producidos = 0;  
//Declaracion de semaforos
sem_t espacios_vacios;  
sem_t espacios_llenos;  
pthread_mutex_t mutex;  

//Valores para los datos
int generar_valor() {
    return rand() % 100;  
}


//Codigo para el hilo productor
void* productor(void* arg) {
    while (valores_producidos < MAX_VALORES) {
        int valor = generar_valor();
        sem_wait(&espacios_vacios);
        pthread_mutex_lock(&mutex);

        buffer[indice_escritura] = valor;
        printf("[Productor] Valor producido: %d | Posición: %d\n", valor, indice_escritura);
        indice_escritura = (indice_escritura + 1) % MAX;

        valores_producidos++;
        //Libera la seccion crítica
        pthread_mutex_unlock(&mutex);
        //Libera un cupo en el buffer
        sem_post(&espacios_llenos);

        usleep(100000);
    }
    sem_post(&espacios_llenos);  
    return NULL;
}

//codigo para el hilo consumidor
void* consumidor(void* arg) {
    while (1) {
        //Espera la disponibilidad de un valor 
        sem_wait(&espacios_llenos);
        //Bloquea la seccion critica
        pthread_mutex_lock(&mutex);

        int valor = buffer[indice_lectura];
        if (valor == FIN || valores_producidos >= MAX_VALORES) {
            printf("[Consumidor] Valor FIN encontrado o producción terminada.\n");
            //Libera la seccion crítica
            pthread_mutex_unlock(&mutex);
            break;
        }

        printf("[Consumidor] Valor consumido: %d | Posición: %d\n", valor, indice_lectura);
        buffer[indice_lectura] = 0;
        indice_lectura = (indice_lectura + 1) % MAX;
        //Libera la seccion crítica
        pthread_mutex_unlock(&mutex);
        //Libera un cupo en el buffer
        sem_post(&espacios_vacios);

        usleep(150000);
    }
    return NULL;
}

int main() {
    srand(time(NULL));  
    //Inicializa con 0 porque no hay valores en el buffer
    sem_init(&espacios_vacios, 0, MAX);   
    sem_init(&espacios_llenos, 0, 0);     
    pthread_mutex_init(&mutex, NULL); 

    //Creacion de hilos

    pthread_t t_productor, t_consumidor;

    //Creacion de hilos
    pthread_create(&t_productor, NULL, productor, NULL);
    pthread_create(&t_consumidor, NULL, consumidor, NULL);

    //Espera a que los hilos terminen
    pthread_join(t_productor, NULL);
    pthread_join(t_consumidor, NULL);

    //Destruye los semaforos usados
    sem_destroy(&espacios_vacios);
    sem_destroy(&espacios_llenos);
    pthread_mutex_destroy(&mutex);

    return 0;
}
