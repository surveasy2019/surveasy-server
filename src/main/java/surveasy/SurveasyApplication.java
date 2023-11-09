package surveasy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SurveasyApplication {

	public static void main(String[] args) {
		SpringApplication.run(SurveasyApplication.class, args);
	}

}
