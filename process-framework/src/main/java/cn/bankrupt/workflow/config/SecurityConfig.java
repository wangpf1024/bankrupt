package cn.bankrupt.workflow.config;

import cn.bankrupt.workflow.filter.JwtRequestFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig{

    private final JwtRequestFilter jwtRequestFilter;

    private final UserDetailsService userDetailsService;

    public SecurityConfig(JwtRequestFilter jwtRequestFilter, UserDetailsService userDetailsService) {
        this.jwtRequestFilter = jwtRequestFilter;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csr -> csr.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/authenticate").permitAll()
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/access_token").permitAll()
                        .requestMatchers("/logout").permitAll()
                        .requestMatchers("/actuator/shutdown").permitAll()
                        .requestMatchers("/camunda-api/authenticate").permitAll()
                        .requestMatchers("/camunda-api/login").permitAll()
                        .requestMatchers("/camunda-api/access_token").permitAll()
                        .requestMatchers("/camunda-api/logout").permitAll()
                        .requestMatchers("/camunda-api/actuator/shutdown").permitAll()
                        .requestMatchers(HttpMethod.GET, "/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/**").authenticated()
                        .anyRequest().authenticated()
                ).exceptionHandling((exceptionHandling) ->{
                    exceptionHandling.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
                })
                .sessionManagement((sessionManagement) ->{
                    sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
        ;
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        // 异常处理
        return http.build();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
