package palvelino.fi.KittyTinder;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import palvelino.fi.KittyTinder.domain.Kitty;
import palvelino.fi.KittyTinder.domain.KittyRepository;

@SpringBootApplication
public class KittyTinderApplication {

	public static void main(String[] args) {
		SpringApplication.run(KittyTinderApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner demo(KittyRepository repository) {
	return (args) -> {
		Kitty kisu1 = new Kitty("Kisu", 3);
		Kitty kisu2 = new Kitty("Kisuliini", 4);
		Kitty kisu3 = new Kitty("Kisulianus", 5);
		
		repository.save(kisu1);
		repository.save(kisu2);
		repository.save(kisu3);
		
		
		
		
		
	};
	}

}
