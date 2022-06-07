package com.fidev.avilasoft.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.common.util.OAuth2Utils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeSet;
import java.util.UUID;

@Slf4j
@Component
public class EnhancedAuthenticationKeyGenerator extends DefaultAuthenticationKeyGenerator {
    private static final String CLIENT_ID = "client_id";
    private static final String SCOPE = "scope";
    private static final String USERNAME = "username";

    private static final String INSTANCE_ID = "client_instance_id";

    @Override
    public String extractKey(OAuth2Authentication authentication) {
        Map<String, String> values = new LinkedHashMap<>();
        OAuth2Request authorizationRequest = authentication.getOAuth2Request();

        if (!authentication.isClientOnly()) {
            values.put(USERNAME, authentication.getName());
        }
        values.put(CLIENT_ID, authorizationRequest.getClientId());

        if (authorizationRequest.getScope() != null) {
            values.put(SCOPE, OAuth2Utils.formatParameterList(new TreeSet<>(authorizationRequest.getScope())));
        }

        if (authorizationRequest.getRequestParameters().containsKey(INSTANCE_ID))
            values.put(INSTANCE_ID, authorizationRequest.getRequestParameters().get(INSTANCE_ID));
        else values.put(INSTANCE_ID, UUID.randomUUID().toString());

        return generateKey(values);
    }
}
