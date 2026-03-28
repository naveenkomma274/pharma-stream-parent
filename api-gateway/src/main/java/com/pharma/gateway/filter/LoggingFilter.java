package com.pharma.gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class LoggingFilter implements GlobalFilter {

    private final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // PRE-FILTER LOGIC: Executes when the request enters the gateway
        String path = exchange.getRequest().getPath().toString();
        String method = exchange.getRequest().getMethod().toString();

        logger.info("PHARMA-GATEWAY-AUDIT: Received {} request for path: {}", method, path);

        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            // POST-FILTER LOGIC: Executes when the response is sent back
            logger.info("PHARMA-GATEWAY-AUDIT: Response sent for path: {} with status: {}",
                    path, exchange.getResponse().getStatusCode());
        }));
    }
}