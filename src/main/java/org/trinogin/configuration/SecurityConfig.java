package org.trinogin.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.beans.factory.annotation.Value;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Value("${security.rememberme.key:boardgamestat-remember-me-key}")
    private String rememberMeKey;

    @Bean
    @Order(1)
    public SecurityFilterChain apiChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/board-game-stat/api/**")
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/board-game-stat/api/register").permitAll()
                        .requestMatchers("/board-game-stat/api/admin/**").hasAuthority("admin")
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }

    //@Bean
    //@Order(2)
    public SecurityFilterChain webChain(HttpSecurity http, UserDetailsService userDetailsService) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers(HttpMethod.POST, "/register").permitAll()
                                .requestMatchers("/login", "/css/**", "/register").permitAll()
                                .requestMatchers("/board-game-stat/admin/**").hasAuthority("admin")
                                .anyRequest().authenticated()
                )
                .formLogin(form -> form.loginPage("/login")
                        .defaultSuccessUrl("/board-game-stat/home", true)
                        .permitAll())
                .logout(logout -> logout.logoutSuccessUrl("/login?logout").permitAll())
                .rememberMe(rm -> rm
                        .key(rememberMeKey)
                        .rememberMeParameter("remember-me")
                        .tokenValiditySeconds(60 * 60 * 24 * 14) // 14 days
                        .userDetailsService(userDetailsService)
                )
                .exceptionHandling(e -> e.accessDeniedHandler((req, res, ex) -> {
                    String ctx = req.getContextPath();
                    if (ctx == null) ctx = "";
                    res.sendRedirect(ctx + "/board-game-stat/home");
                }));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}