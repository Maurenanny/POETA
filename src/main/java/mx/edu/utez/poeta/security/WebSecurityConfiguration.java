package mx.edu.utez.poeta.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource)
            .usersByUsernameQuery("SELECT username, password, enabled FROM users WHERe username = ?")
            .authoritiesByUsernameQuery("SELECT u.username, r.authority FROM user_role AS ur "
                + "INNER JOIN users AS u ON u.id = ur.user "
                + "INNER JOIN roles AS r ON r.id = ur.role WHERE u.username = ?");
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
        .authorizeRequests()
        .antMatchers("/").permitAll() //Vista de inicio
        .antMatchers("/css/**").permitAll()
        .antMatchers("/js/**").permitAll()
        .anyRequest().authenticated()
        .and()
        .formLogin()
        .defaultSuccessUrl("/") //Poner la que corresponde luego
        .loginPage("/login")
        .permitAll()
        .and()
        .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login").permitAll();
    }
    
}
