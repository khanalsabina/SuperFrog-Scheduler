package edu.tcu.cs.frog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .mvcMatchers("/h2-console/**", "/css/**", "/js/**", "/logo.jpg", "/favicon/**", "/signup/**", "/frog/register_success","/","/assets/**").permitAll()
                    .mvcMatchers("/frog/delete/**","/users/edit/**","/users/**").hasAuthority("admin")
                    .anyRequest().authenticated() // anything else must be authenticated
                    .and()
                .formLogin()
                    .loginPage("/login").permitAll() // let anonymous user access login page
                    .defaultSuccessUrl("/home", true)
                    .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .and()
                .exceptionHandling()
                    .accessDeniedPage("/access_denied"); // 403 error

        // for h2 console
        http.csrf().ignoringAntMatchers("/h2-console/**");
        http.headers().frameOptions().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
