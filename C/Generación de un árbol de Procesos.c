#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/wait.h>

int main() {
    int id = 0;

    id = fork();
    if (id != 0) {
        printf("Soy el proceso A: %d, mi hijo es: %d.\n", getpid(), id);
    } else {
        printf("Soy el proceso B: %d.\n", getpid());

        for (int n = 0; n < 3; n++) {
            id = fork();
            if (id != 0) {
                printf("Soy el proceso B: %d, mi hijo es: %d.\n", getpid(), id);
            } else {
                if (n == 0) {
                    printf("Soy el proceso C: %d.\n", getpid());

                    for (int m = 0; m < 3; m++) {
                        id = fork();
                        if (id != 0) {
                            printf("Soy el proceso C: %d, mi hijo es: %d.\n", getpid(), id);
                        } else {
                            if (m == 0) printf("Soy el proceso F: %d.\n", getpid());
                            if (m == 1) printf("Soy el proceso G: %d.\n", getpid());
                            if (m == 2) printf("Soy el proceso H: %d.\n", getpid());
                            break;
                        }
                    }
                }
                if (n == 1) {
                    printf("Soy el proceso D: %d.\n", getpid());
                }
                if (n == 2) {
                    printf("Soy el proceso E: %d.\n", getpid());

                    id = fork();
                    if (id != 0) {
                        printf("Soy el proceso E: %d, mi hijo es: %d.\n", getpid(), id);
                    } else {
                        printf("Soy el proceso I: %d.\n", getpid());

                        id = fork();
                        if (id != 0) {
                            printf("Soy el proceso I: %d, mi hijo es: %d.\n", getpid(), id);
                        } else {
                            printf("Soy el proceso J: %d.\n", getpid());
                        }
                        break;
                    }
                }
                break;
            }
        }
    }

    if (id > 0) {
        wait(NULL);
    }

    return 0;
}
