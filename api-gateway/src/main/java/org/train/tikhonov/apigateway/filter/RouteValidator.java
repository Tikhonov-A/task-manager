package org.train.tikhonov.apigateway.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {

    private final List<String> openApiEndpoints = List.of(
           "/api/v1/auth/login",
            "/api/v1/auth/validate",
            "/api/v1/registration",
            "/api/v1/registration/confirm"
    );

    public Predicate<ServerHttpRequest> isSecured = (
            request -> openApiEndpoints.stream().noneMatch(uri -> request.getURI().getPath().startsWith(uri))
    );
}
