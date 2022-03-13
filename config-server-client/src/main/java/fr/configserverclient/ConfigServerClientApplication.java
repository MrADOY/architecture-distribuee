package fr.configserverclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class ConfigServerClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfigServerClientApplication.class, args);
	}

	@RestController
	@RequestMapping("api/v1")
	@RefreshScope
	class ServiceInstanceRestController {

		@Value("${attribute.test}")
		private String valueFromConfig;

		@RequestMapping("/sample")
		public String getSample() {
			return valueFromConfig;
		}
	}

}
