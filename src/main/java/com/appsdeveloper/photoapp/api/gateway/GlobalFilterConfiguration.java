package com.appsdeveloper.photoapp.api.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import reactor.core.publisher.Mono;

@Configuration
public class GlobalFilterConfiguration {

    final Logger logger = LoggerFactory.getLogger(GlobalFilterConfiguration.class);

    // after applying order this pre filter will execute filrst but its post filter will excute last
    @Order(1)
    @Bean
    public GlobalFilter secondPreFilter() {
        return (exchange, chain) -> {
            logger.info("My second global pre-filter is executed ... ");

            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                logger.info("My third global post-filter was executed ... ");
            }));
        };
    }
    // after applying order this pre filter will execute second but its post filter will excute second last
    @Order(2)
    @Bean
    public GlobalFilter thirdPreFilter() {
        return (exchange, chain) -> {
            logger.info("My third global pre-filter is executed ... ");

            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                logger.info("My second Post global post-filter was executed ... ");
            }));
        };
    }

    @Order(3)
    @Bean
    public GlobalFilter fourthPreFilter() {
        return (exchange, chain) -> {
            logger.info("My fourth global pre-filter is executed ... ");

            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                logger.info("My First Post filter global post-filter was executed ... ");
            }));
        };
    }
}
