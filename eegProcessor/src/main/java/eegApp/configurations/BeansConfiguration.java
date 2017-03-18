package eegApp.configurations;


import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import eegApp.helpers.BynaryFileToObjectEegSampleTransformer;
import eegApp.model.*;

@Configuration
public class BeansConfiguration {

	private static final Logger LOGGER = Logger
			.getLogger(BeansConfiguration.class);
	
	 //for swagger: http://localhost:8080/swagger-ui.html
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }


}
