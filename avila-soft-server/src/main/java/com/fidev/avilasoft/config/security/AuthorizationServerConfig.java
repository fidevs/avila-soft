package com.fidev.avilasoft.config.security;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    private static final int ACCESS_TOKEN_VALIDITY = 20; // Seconds

    @Qualifier("authenticationManagerBean")
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final DataSource dataSource;
    private final EnhancedAuthenticationKeyGenerator keyGenerator;

    public AuthorizationServerConfig(
            AuthenticationManager authenticationManager, DataSource dataSource,
            CustomUserDetailsService userDetailsService, EnhancedAuthenticationKeyGenerator keyGenerator
    ) {
        this.authenticationManager = authenticationManager;
        this.dataSource = dataSource;
        this.userDetailsService = userDetailsService;
        this.keyGenerator = keyGenerator;
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer configurer) throws Exception {
        configurer.jdbc(dataSource);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpointsConfigurer) {
        endpointsConfigurer.approvalStoreDisabled()
                .tokenStore(tokenStore())
                .tokenServices(tokenServices())
                .userDetailsService(userDetailsService)
                .authenticationManager(authenticationManager);
    }

    @Bean
    public TokenStore tokenStore() {
        final JdbcTokenStore tokenStore = new JdbcTokenStore(dataSource);
        tokenStore.setAuthenticationKeyGenerator(keyGenerator);
        return tokenStore;
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()")
                .allowFormAuthenticationForClients(); // TODO: Add client details with a Gateway
    }

    @Bean
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setTokenStore(tokenStore());
        tokenServices.setAccessTokenValiditySeconds(ACCESS_TOKEN_VALIDITY);

        return tokenServices;
    }
}
