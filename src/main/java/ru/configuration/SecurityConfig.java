package ru.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.services.ClientService;


/**
 * Класс, отвечающий за безопасность и авторизацию
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * Сервис работы с пользователем.
     */
    @Autowired
    private ClientService clientService;

    /**
     * Объект отвечающий за кодирование пароля.
     */
    @Autowired
    private BCryptPasswordEncoder encoder;

    /**
     * Метод настройки безопасности и информации о пользователях.
     * @param http Объект для настройки веб-безопасности для определенных HTTP-запросов.
     * @throws Exception Класс, отвечающий за обнаружение ошибок.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().cors().disable()
                .authorizeRequests().antMatchers("/login", "logout", "/registration", "/css/**", "/js/**").
                permitAll().anyRequest().authenticated()
                .and().formLogin()
                .loginPage("/login").permitAll()
                .defaultSuccessUrl("/home")
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .logoutSuccessUrl("/login");
    }

    /**
     * Методов для аутентификации пользователя.
     * @param auth Объект для настройки веб-безопасности для определенных HTTP-запросов.
     * @throws Exception -  Класс, отвечающий за обнаружение ошибок.
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(clientService).passwordEncoder(encoder);
    }
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return  new BCryptPasswordEncoder();
    }
}