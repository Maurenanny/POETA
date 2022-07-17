package mx.edu.utez.poeta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PoetaApplication extends ServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(PoetaApplication.class, args);
	}

}
