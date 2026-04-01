package fr.miage.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route( r-> r.path("/coursews/**")
                        .filters(f->f.rewritePath("/coursews/(?<remains>.*)","/${remains}")
                                .preserveHostHeader()
                        )
                        .uri("lb://coursews")
                        .id("coursews")
                )
                .route( r-> r.path("/communityws/**")
                        .filters(f->f.rewritePath("/communityws/(?<remains>.*)","/${remains}")
                                .preserveHostHeader()
                        )
                        .uri("lb://communityws")
                        .id("communityws")
                )
                .route(r -> r.path("/authws/**")
                        .filters(f -> f.rewritePath("/authws/(?<remains>.*)", "/${remains}")                                )
                        .uri("lb://authws")
                        .id("authws"))

                .build();
    }

}
