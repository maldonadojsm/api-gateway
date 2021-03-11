package com.microservices.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
API Gateway, as the name suggests, centralizes access to all of the application's endpoints via a single port.
Each API endpoint is accessed by the application name listed within Spring Eureka Naming Server
Ex: localhost:<Gateway Port>/<APPLICATION_NAME>/<Endpoint Mapping>

Gateways allows you to implement:
1. Security features (user authentication)
2. Logging & Metrics
 */
@SpringBootApplication
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

}
