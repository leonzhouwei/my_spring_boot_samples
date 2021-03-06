/*
 * Copyright 2012-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package github.com.leonzhouwei.java.microservice;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@EnableAutoConfiguration
@ComponentScan
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SampleMethodSecurityApplication extends WebMvcConfigurerAdapter {
	private static final Logger logger = LoggerFactory.getLogger(SampleMethodSecurityApplication.class);

	public static void main(String[] args) throws Exception {
		SpringApplication.run(SampleMethodSecurityApplication.class);
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/login").setViewName("login");
		registry.addViewController("/access").setViewName("access");
	}

	@Bean
	public ApplicationSecurity applicationSecurity() {
		return new ApplicationSecurity();
	}

	@Order(Ordered.HIGHEST_PRECEDENCE)
	@Configuration
	protected static class AuthenticationSecurity extends
			GlobalAuthenticationConfigurerAdapter {
		@Resource
		private DataSource dataSource;

		@Override
		public void init(AuthenticationManagerBuilder auth) throws Exception {
//			auth.
//				inMemoryAuthentication()
//					.withUser("admin")
//						.password("admin").roles("ADMIN", "USER")
//					.and()
//					.withUser("user")
//						.password("user").roles("USER")
//					.and()
//					.withUser("alice")
//						.password("alice").roles("USER");
			
//		    auth
//		    	.jdbcAuthentication()
//		    		.passwordEncoder(new Md5PasswordEncoder())
//		    		.dataSource(dataSource)
//		    			.usersByUsernameQuery("select username,password,enabled from users where username = ?")
//		    			.authoritiesByUsernameQuery("select username,authority from authorities where username = ?");
			
			JdbcUserDetailsManager userDetailsService = new JdbcUserDetailsManager();
			userDetailsService.setEnableAuthorities(false);
			userDetailsService.setEnableGroups(true);
	        userDetailsService.setDataSource(dataSource);
	        auth.userDetailsService(userDetailsService).passwordEncoder(new Md5PasswordEncoder());
	        auth.jdbcAuthentication().dataSource(dataSource);
		}
	}

	@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
	protected static class ApplicationSecurity extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			// @formatter:off
			http.authorizeRequests().antMatchers("/login").permitAll().anyRequest()
					.fullyAuthenticated().and().formLogin().loginPage("/login")
					.failureUrl("/login?error").and().logout()
					.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).and()
					.exceptionHandling().accessDeniedPage("/access?error");
			// @formatter:on
		}
	}
	
	@Bean
	public Filter compressingFilter() {
	    return new Filter() {

			@Override
			public void destroy() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void doFilter(ServletRequest request, ServletResponse response,
					FilterChain fc) throws IOException, ServletException {
				logger.info(" local: " + request.getLocalName() + ", " + request.getLocalAddr() + ", " +  request.getLocalPort());
				logger.info("remote: " + request.getRemoteHost() + ", " + request.getRemoteAddr() + ", " +  request.getRemotePort());
				fc.doFilter(request, response);
			}

			@Override
			public void init(FilterConfig arg0) throws ServletException {
				// TODO Auto-generated method stub
				
			}};
	}

}

@Configuration
@ImportResource("/appCtx.xml")
class XmlImportingConfiguration {
}
