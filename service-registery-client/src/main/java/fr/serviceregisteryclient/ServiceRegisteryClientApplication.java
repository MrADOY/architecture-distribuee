package fr.serviceregisteryclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
public class ServiceRegisteryClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceRegisteryClientApplication.class, args);
	}

}

// micro-service-1
@RestController
@RequestMapping("api/v1")
class ServiceInstanceRestController {
	@Autowired
	private RestTemplate restTemplate;

	@RequestMapping("/sample")
	public String getSample() {
		String response = restTemplate.getForObject("http://micro-service-2/microservice2/api/v1/names", String.class);
		return response;
	}
}

