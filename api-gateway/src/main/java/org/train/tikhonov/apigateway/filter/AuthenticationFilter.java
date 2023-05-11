package org.train.tikhonov.apigateway.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.RouteToRequestUrlFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.train.tikhonov.apigateway.exception.JwtTokenException;
import org.train.tikhonov.apigateway.util.JwtUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private RouteValidator routeValidator;

    @Autowired
    private JwtUtil jwtUtil;

    public AuthenticationFilter(RouteValidator routeValidator) {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            if (routeValidator.isSecured.test(exchange.getRequest())) {
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new RuntimeException("Miss authorization header");
                }
                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if (authHeader != null && authHeader.startsWith("Bearer")) {
                    authHeader = authHeader.substring(7);
                }
                if (jwtUtil.isTokenExpired(authHeader)) {
                    throw new JwtTokenException("Token is expired");
                }
                String userId = jwtUtil.extractUserUUIDFromToken(authHeader);
                System.out.println(userId);
                System.out.println("f");
                ServerHttpRequest updatedRequest = null;
                try {
                    updatedRequest = exchange.getRequest().mutate().
                            uri(
                                    new URI(exchange.getRequest().getURI() + "?userId=" + userId)
                            ).
                            build();
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
                exchange = exchange.mutate().request(updatedRequest).build();
            }
            return chain.filter(exchange);
        });
    }

    public static class Config {
    }
}
