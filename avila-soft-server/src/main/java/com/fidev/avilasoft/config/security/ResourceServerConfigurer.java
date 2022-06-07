package com.fidev.avilasoft.config.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.session.SessionManagementFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableResourceServer
public class ResourceServerConfigurer extends ResourceServerConfigurerAdapter {
    @Value("${application.secured}")
    private boolean securityEnabled;

    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedMethod("*");
        config.addAllowedHeader("*");

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        if (securityEnabled){
            http
                    .addFilterBefore(corsFilter(), SessionManagementFilter.class)
                    .antMatcher("/**").authorizeRequests()
                    .antMatchers(
                            "/oauth/token**",
                            "/oauth/authorize",
                            "/public/**",
                            "/oauth/user/logout",
                            "/v2/api-docs**",
                            "/info"
                    ).permitAll()
                    .anyRequest().authenticated()
                    .and().formLogin().loginPage("/login").failureForwardUrl("/login?error").permitAll()
                    .and().csrf().disable()
                    .httpBasic().disable();
        } else {
            http.antMatcher("/**")
                    .authorizeRequests()
                    .anyRequest()
                    .permitAll()
                    .and()
                    .formLogin()
                    .loginPage("/login")
                    .failureForwardUrl("/login?error")
                    .permitAll()
                    .and()
                    .csrf()
                    .disable()
                    .httpBasic()
                    .disable();
        }
    }
}
