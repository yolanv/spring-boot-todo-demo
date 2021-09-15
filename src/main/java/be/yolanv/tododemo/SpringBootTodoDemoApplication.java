package be.yolanv.tododemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootTodoDemoApplication implements CommandLineRunner {
	private static final Logger LOGGER = LoggerFactory.getLogger(SpringBootTodoDemoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringBootTodoDemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if (System.getenv("DOCKER_ENV") != null) {
			LOGGER.info("{}", System.getenv("DOCKER_ENV"));
		}
	}
}
