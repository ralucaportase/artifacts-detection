package eegApp;

import java.io.IOException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
@ImportResource("/application-context.xml")
public class Main {

	public static void main(String[] args) throws IOException {

		System.out.println("before run ");
		SpringApplication.run(Main.class, args);
		System.out.println("done");

	}

}
