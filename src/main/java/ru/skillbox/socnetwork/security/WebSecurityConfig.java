package ru.skillbox.socnetwork.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.skillbox.socnetwork.security.jwt.AuthEntryPointJwt;
import ru.skillbox.socnetwork.security.jwt.AuthTokenFilter;
import ru.skillbox.socnetwork.service.PersonDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
    // securedEnabled = true,
    // jsr250Enabled = true,
    prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private String ConsolePath = "";

  @Autowired
  PersonDetailsServiceImpl personDetailsService;

  @Autowired
  private AuthEntryPointJwt unauthorizedHandler;

  @Bean
  public AuthTokenFilter authenticationJwtTokenFilter() {
    return new AuthTokenFilter();
  }

  @Override
  public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
    authenticationManagerBuilder.userDetailsService(personDetailsService).passwordEncoder(passwordEncoder());
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
          .httpBasic().disable()
//          .cors()
//        .and()
          .csrf().disable()
          .exceptionHandling()
          .authenticationEntryPoint(unauthorizedHandler)
        .and()
          .authorizeRequests()
              .antMatchers("/api/v1/auth/**", "/api/v1/account/register").permitAll()
              .antMatchers("/static/**", "/api/v1/platform/**").permitAll()
              .antMatchers("/*").permitAll()
            .anyRequest().authenticated()
        .and()
            .formLogin()
            .loginProcessingUrl("**/login")
            .defaultSuccessUrl("/", true)
        .and()
            .logout()
            .logoutUrl("/api/v1/auth/logout")
            .logoutSuccessUrl("/")
        .and()
          .sessionManagement()
          .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
          .addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
  }
}
