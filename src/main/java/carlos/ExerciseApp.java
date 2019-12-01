package carlos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import carlos.consumer.RestClient;

@SpringBootApplication
public class ExerciseApp {

	@Autowired
	RestClient restClient;

	public static void main(String[] args) {
		SpringApplication.run(ExerciseApp.class, args);
	}

}