package mx.unam.aragon;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.sql.DataSource;
import java.sql.Connection;

@EnableScheduling
@SpringBootApplication
public class AbarrotesZorroApplication {

	public static void main(String[] args) {
		SpringApplication.run(AbarrotesZorroApplication.class, args);
	}

}
