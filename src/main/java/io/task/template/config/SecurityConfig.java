package io.task.template.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain auth0FilterChain(final HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(requests -> requests
                .requestMatchers("/api/v1/**").permitAll()
                //.hasRole("sample-role")
                //.requestMatchers("/h2/**").permitAll()
                .anyRequest().authenticated());

        http.cors(Customizer.withDefaults());


        http.csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable);
//                .formLogin(AbstractHttpConfigurer::disable);

        http.sessionManagement(httpSecuritySessionManagementConfigurer -> {
            httpSecuritySessionManagementConfigurer
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        });

        return http.build();
    }


}
