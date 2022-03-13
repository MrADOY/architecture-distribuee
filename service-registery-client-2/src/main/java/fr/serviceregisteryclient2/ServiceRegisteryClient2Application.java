package fr.serviceregisteryclient2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@EnableDiscoveryClient
public class ServiceRegisteryClient2Application {

	public static void main(String[] args) {
		SpringApplication.run(ServiceRegisteryClient2Application.class, args);
	}

}

// micro-service-2
@RestController
@RequestMapping("api/v1")
class ServiceInstanceRestController {

	@Value("${spring.application.name}")
	private String applicationName;

	@Value("${server.port}")
	private String serverPort;

	@RequestMapping("/names")
	public String getNames() {
		return String.format("I'm %s listening on port : %s", applicationName, serverPort);
	}
}
