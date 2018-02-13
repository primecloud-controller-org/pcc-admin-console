/*
 * Copyright 2018 by PrimeCloud Controller/OSS Community.
 * 
 * This file is part of PrimeCloud Controller(TM).
 * 
 * PrimeCloud Controller(TM) is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 * 
 * PrimeCloud Controller(TM) is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with PrimeCloud Controller(TM). If not, see <http://www.gnu.org/licenses/>.
 */
package org.primecloudcontroller.admin.security;

import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.primecloudcontroller.admin.config.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AppConfig appConfig;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/js/**", "/webjars/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http
                .authorizeRequests();
        registry = registry.antMatchers("/", "/index").permitAll();
        registry = registry.anyRequest().authenticated();

        FormLoginConfigurer<HttpSecurity> loginConfigurer = http.formLogin();
        loginConfigurer = loginConfigurer.loginProcessingUrl("/login");
        loginConfigurer = loginConfigurer.loginPage("/index");
        loginConfigurer = loginConfigurer.failureUrl("/index?message=login_failure");
        loginConfigurer = loginConfigurer.defaultSuccessUrl("/platform");
        loginConfigurer = loginConfigurer.usernameParameter("username");
        loginConfigurer = loginConfigurer.passwordParameter("password");
        loginConfigurer.and();

        LogoutConfigurer<HttpSecurity> logoutConfigurer = http.logout();
        logoutConfigurer = logoutConfigurer.logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
        logoutConfigurer = logoutConfigurer.logoutSuccessUrl("/index");
        logoutConfigurer = logoutConfigurer.deleteCookies("JSESSIONID");
        logoutConfigurer = logoutConfigurer.invalidateHttpSession(true);
        logoutConfigurer.permitAll();

        http.csrf().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                String password = appConfig.getUsers().get(username);

                if (StringUtils.isNotEmpty(password)) {
                    return new User(username, password, new ArrayList<GrantedAuthority>());
                }

                throw new UsernameNotFoundException(username);
            }
        };
    }

}
