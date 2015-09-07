package com.visionbizsolutions.init;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.CompositeSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionFixationProtectionStrategy;
import org.springframework.security.web.session.ConcurrentSessionFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	@Qualifier("authenticationProvider")
	AuthenticationProvider authenticationProvider;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.authenticationProvider(authenticationProvider);
	}

	@Bean
	public LoginUrlAuthenticationEntryPoint authenticationEntryPoint() {
		return new LoginUrlAuthenticationEntryPoint("/loginPage");
	}

	@Bean
	public SessionRegistry sessionRegistry() {
		return new SessionRegistryImpl();
	}

	@Bean
	public ConcurrentSessionFilter concurrentSessionFilter() {
		return new ConcurrentSessionFilter(sessionRegistry(), "/sessionTimeout");
	}

	@Bean
	public CompositeSessionAuthenticationStrategy compositeSessionAuthenticationStrategy() {
		ConcurrentSessionControlAuthenticationStrategy conSCAStrategy = new ConcurrentSessionControlAuthenticationStrategy(
				sessionRegistry());
		conSCAStrategy.setMaximumSessions(1);
		conSCAStrategy.setExceptionIfMaximumExceeded(true);
		
		SessionFixationProtectionStrategy sfPStrategy = new SessionFixationProtectionStrategy();
		
		RegisterSessionAuthenticationStrategy rsAStrategy = new RegisterSessionAuthenticationStrategy(sessionRegistry());
		
		List<SessionAuthenticationStrategy> strategies = new ArrayList<SessionAuthenticationStrategy>();
		strategies.add(conSCAStrategy);
		strategies.add(sfPStrategy);
		strategies.add(rsAStrategy);
		
		CompositeSessionAuthenticationStrategy strategy = new CompositeSessionAuthenticationStrategy(
				strategies);

		return strategy;
	}
	
	@Bean
	public ProviderManager providerManager () {
		List <AuthenticationProvider> authProviders = new ArrayList<AuthenticationProvider>();
		authProviders.add(authenticationProvider);
		ProviderManager providerManager = new ProviderManager(authProviders);
		
		return providerManager;
	}

	@Bean
	public SavedRequestAwareAuthenticationSuccessHandler customAuthenticationSuccessHandler () {
		SavedRequestAwareAuthenticationSuccessHandler handler = new SavedRequestAwareAuthenticationSuccessHandler();
		handler.setAlwaysUseDefaultTargetUrl(true);
		handler.setDefaultTargetUrl("/welcome");
		return handler;
	}
	
	@Bean
	public SimpleUrlAuthenticationFailureHandler customAuthenticationFailureHandler () {
		SimpleUrlAuthenticationFailureHandler handler = new SimpleUrlAuthenticationFailureHandler();
		handler.setDefaultFailureUrl("/loginPage?error");
		return handler;
	}
	
	@Bean
	public UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter() {
		UsernamePasswordAuthenticationFilter filter = new UsernamePasswordAuthenticationFilter();
		
		//sessionAuthenticationStrategy
		filter.setSessionAuthenticationStrategy(compositeSessionAuthenticationStrategy());
		//AuthenticationManager/ProviderManager
		filter.setAuthenticationManager(providerManager());
		//filterProcessesUrl
		filter.setFilterProcessesUrl("/login");
		//usernameParameter
		filter.setUsernameParameter("username");
		//passwordParameter
		filter.setPasswordParameter("password");
		//authenticationSuccessHandler
		filter.setAuthenticationSuccessHandler(customAuthenticationSuccessHandler());
		//authenticationFailureHandler
		filter.setAuthenticationFailureHandler(customAuthenticationFailureHandler());
		
		return filter;
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web
			.ignoring()
			.antMatchers("/resources/**");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http
		
		// access-denied-page: this is the page users will be
		// redirected to when they try to access protected areas.
		.exceptionHandling()
			.accessDeniedPage("/403")
			.authenticationEntryPoint(authenticationEntryPoint())
		.and()

		.authenticationProvider(authenticationProvider)
		.headers()
		.and()
		
		.addFilter(usernamePasswordAuthenticationFilter())
		.addFilter(concurrentSessionFilter())
		
		.sessionManagement()
			.sessionAuthenticationStrategy(compositeSessionAuthenticationStrategy())
			.enableSessionUrlRewriting(false)
			.invalidSessionUrl("/")
		.and()
		
		.logout()
            .deleteCookies("JSESSIONID")
            .logoutSuccessUrl("/loginPage")
            .invalidateHttpSession(true)
        .and()

		.authorizeRequests()
			.antMatchers("/admin**").access("hasRole('ROLE_ADMIN')")
			.antMatchers("/loginPage").anonymous()
			.antMatchers("/welcome").access("hasRole('ROLE_USER')")
			.antMatchers("/signup").anonymous()
			.antMatchers("/dashboard/updateuser").access("hasRole('ROLE_USER')")
			.antMatchers("/dashboard/changePassword").access("hasRole('ROLE_USER')")
			.antMatchers("/dashboard/updatepassword").access("hasRole('ROLE_USER')")
			.antMatchers("/dashboard/uploadFilePage").access("hasRole('ROLE_USER')")
			.antMatchers("/dashboard/upload").access("hasRole('ROLE_USER')")
			.antMatchers("/dashboard/getFilesLog").access("hasRole('ROLE_USER')")
			.antMatchers("/processforgotpassword").anonymous()
			.antMatchers("/forgotpassword").anonymous()
			.antMatchers("/updatepassword").anonymous()
			.antMatchers("/*").permitAll()
			.and()
		.requiresChannel()
			.anyRequest().requiresSecure()
		.and()
				
		.csrf();
	}
	

}
