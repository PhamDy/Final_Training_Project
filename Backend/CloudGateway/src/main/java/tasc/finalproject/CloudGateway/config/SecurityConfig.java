package tasc.finalproject.CloudGateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity serverHttpSecurity) {
        serverHttpSecurity
                .authorizeExchange(exchanges -> exchanges
                .pathMatchers("/public/**").permitAll()
                .anyExchange().permitAll()
        )
                .oauth2ResourceServer(oAuth2ResourceServerSpec -> oAuth2ResourceServerSpec
                        .jwt(Customizer.withDefaults()));
        serverHttpSecurity.csrf(csrfSpec -> csrfSpec.disable());
        return serverHttpSecurity.build();
    }

//    @Bean
//    public UrlBasedCorsConfigurationSource  corsFilter() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(List.of("*"));
//        configuration.setAllowedHeaders(List.of("*"));
//        configuration.setAllowedMethods(List.of("*"));
//        UrlBasedCorsConfigurationSource source
//                = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }

//    @Bean
//    public CorsWebFilter corsWebFilter() {
//        return new CorsWebFilter(corsConfigurationSource());
//    }

    @Bean
    public CorsWebFilter filter(){
        org.springframework.web.cors.CorsConfiguration config= new CorsConfiguration();
        config.setAllowCredentials(false);
        config.setAllowedOrigins(Arrays.asList("*","*"));
        config.addExposedHeader("Authorization");
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD"));
        config.setAllowedHeaders(Arrays.asList("Origin", "Content-Type",
                "Content-Disposition", "Content-Length",
                "Accept", "Authorization", "Cookie"));
        config.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", config);
        return new CorsWebFilter(source);
    }

}
