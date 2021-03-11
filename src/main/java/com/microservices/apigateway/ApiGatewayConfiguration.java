package com.microservices.apigateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/*
Configure API Gateway's rerouting.
 */
@Configuration
public class ApiGatewayConfiguration {
    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder builder){

        // Use lambda function to return route for given URI. Useful for custom routes and to add authentication headers.
        return builder.routes()
                .route(p -> p.path("/get")
                        .filters(f -> f.addRequestHeader("MyHeader", "MyURI")
                        .addRequestParameter("Param", "MyValue"))
                        .uri("http://httpbin.org"))
                /*
                Reroute to naming server + load balancer when you encounter HTTP endpoint mapping that starts
                with following pattern. ** symbolizes rest of endpoint mapping.
                 */
                .route(p -> p.path("/currency-exchange/**")
                        // Reroute to currency-exchange/conversion load balancer
                        .uri("lb://currency-exchange"))
                .route(p -> p.path("/currency-conversion-feign/**")
                        // Reroute to currency-conersion load balancer
                        .uri("lb://currency-conversion"))

                // Reroute following non existent HTTP endpoint mapping to currency-conversion service
                .route(p -> p.path("/currency-conversion-new/**")
                        .filters(f -> f.rewritePath(
                                //find expression with mapping beginning with and capture remaining mapping
                                "/currency-conversion-new/(?<segment>.*)",
                                //rewrite mapping while including remaining part of original mapping
                                "/currency-conversion-feign/${segment}"
                        ))
                        .uri("lb://currency-conversion"))
                .build();
    }
}
