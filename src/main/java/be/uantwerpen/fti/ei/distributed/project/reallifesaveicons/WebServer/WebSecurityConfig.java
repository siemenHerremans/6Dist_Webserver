package be.uantwerpen.fti.ei.distributed.project.reallifesaveicons.WebServer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth
                .inMemoryAuthentication()
                .withUser("siemen").password(passwordEncoder().encode("admin")).roles("ADMIN")
                    .and()
                .withUser("benny").password(passwordEncoder().encode("user")).roles("USER");
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                .authorizeRequests()
                    .antMatchers(HttpMethod.GET, "/admin/**").hasRole("ADMIN")
                    .antMatchers(HttpMethod.PUT, "/admin/**").hasRole("ADMIN")
                    .antMatchers(HttpMethod.GET,"/user/**").hasAnyRole("ADMIN", "USER")
                    .antMatchers(HttpMethod.PUT,"/user/**").hasAnyRole("ADMIN", "USER")
                    .and()
                    .formLogin()
                    .and()
                    .exceptionHandling().accessDeniedPage("/403")
                    .and()
                    .logout()
                    .logoutSuccessUrl("/");
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
