package main.ejercicio3;
/**
 * 3. Gestión de eventos simples
Crear una aplicación para gestionar eventos. Cada evento tiene un nombre y un estado completado (booleano). La aplicación debe permitir:
1.​ Mostrar lista de eventos (inicialmente estática).
2.​ Añadir eventos mediante enlaces predefinidos /add?nombre=....
3.​ Eliminar eventos por ID con /delete/{id}.
4.​ Cambiar estado completado/no completado mediante /toggle/{id}.
5.​ Diferenciar visualmente eventos completados y pendientes con CSS.
 */
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Ejercicio3Application {

	public static void main(String[] args) {
		SpringApplication.run(Ejercicio3Application.class, args);
	}

}
