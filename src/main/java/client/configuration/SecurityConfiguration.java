package client.configuration;

import client.data.model.enums.UserRole;
import client.mvc.UserSignUpMvcController;
import client.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final Logger log = LoggerFactory.getLogger(SecurityConfiguration.class);
    private static final String LOGIN_URL = "/login";
    private final UserService userService;

    public SecurityConfiguration(UserService userService) {
        this.userService = userService;
        createAdminOnStartup();
    }

    private void createAdminOnStartup() {
        final String admin = "admin";
        if (userService.findByLogin(admin) == null) {
            log.info("Admin user successfully created");
            userService.createUser(admin, "12345678", admin, admin, "+77777777777", UserRole.ADMIN);
        }
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().sameOrigin().and()
                .cors().and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(UserSignUpMvcController.SIGNUP_URL).permitAll()
                .antMatchers(HttpMethod.GET, LOGIN_URL).permitAll()
                .antMatchers("/api/**").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/images/**").permitAll()
                .antMatchers("/swagger-ui/**").permitAll()
                .antMatchers("/v3/api-docs").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage(LOGIN_URL).permitAll()
                .and()
                .logout().permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers("/css/**")
                .antMatchers("/js/**")
                .antMatchers("/templates/**")
                .antMatchers("/webjars/**");
    }
}

