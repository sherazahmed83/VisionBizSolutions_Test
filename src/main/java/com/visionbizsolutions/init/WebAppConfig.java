package com.visionbizsolutions.init;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.mvc.support.ControllerClassNameHandlerMapping;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;

@Configuration
@EnableWebMvc
@ComponentScan({"com.visionbizsolutions.orm.jpa.dao", "com.visionbizsolutions.orm.jpa.service", "com.visionbizsolutions.mvc.controllers", "com.visionbizsolutions.security.authentication.dao.handler"})
@PropertySources(value = {@PropertySource("classpath:persistence-mysql.properties")})
@Import({DataBaseConfig.class, WebSecurityConfig.class})
public class WebAppConfig extends WebMvcConfigurerAdapter {
	
	@Resource
	private Environment env;
	
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/theme/");
	}
	
	@Bean
	public ReloadableResourceBundleMessageSource messageSource() {
		ReloadableResourceBundleMessageSource source = new ReloadableResourceBundleMessageSource();
		source.setBasename(env.getRequiredProperty("message.source.basename"));
		source.setDefaultEncoding(env.getRequiredProperty("message.defaultEncoding"));
//		source.setUseCodeAsDefaultMessage(true);
		return source;
	}
	
	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		configurer.favorPathExtension(true)
			.useJaf(false)
			.defaultContentType(MediaType.APPLICATION_JSON)
			.mediaType("html", MediaType.TEXT_HTML)
			.mediaType("json", MediaType.APPLICATION_JSON);
	}
	
	@Bean 
	public BeanNameViewResolver beanNameViewResolver(){
		BeanNameViewResolver resolver = new BeanNameViewResolver();
		return resolver;
	}
	
	@Bean
	public TilesViewResolver tilesViewResolver() {
		TilesViewResolver resolver = new TilesViewResolver();
		return resolver;
	}
	
	@Bean
	public TilesConfigurer tilesConfigurer() {
		TilesConfigurer configurer = new TilesConfigurer();
		
		String defination1 = env.getRequiredProperty("tiles.configurer.definations.path1");
		String defination2 = env.getRequiredProperty("tiles.configurer.definations.path2");
		configurer.setDefinitions(defination1, defination2);
		
		return configurer;
	}
	
	@Bean
	public InternalResourceViewResolver internalResourceViewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/pages/");
		resolver.setSuffix(".jsp");
		resolver.setViewClass(JstlView.class);
		
		return resolver;
	}
	
	@Bean
	public ViewResolver contentNegotiatingViewResolver(
			ContentNegotiationManager manager) {
		
		List<ViewResolver> resolvers = new ArrayList<ViewResolver>();
		resolvers.add(beanNameViewResolver());
		resolvers.add(tilesViewResolver());
		resolvers.add(internalResourceViewResolver());
		
//		JsonViewResolver r2 = new JsonViewResolver();
//		resolvers.add(r2);
		
		ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
		resolver.setViewResolvers(resolvers);
		resolver.setContentNegotiationManager(manager);
	    return resolver;
	    
	}
	
	/**
	* View resolver for returning JSON in a view-based system. Always returns a
	* {@link MappingJacksonJsonView}.
	*/
//	public class JsonViewResolver implements ViewResolver {
//		public View resolveViewName(String viewName, Locale locale)
//				throws Exception {
//				MappingJacksonJsonView view = new MappingJacksonJsonView();
//				view.setPrettyPrint(true);
//				return view;
//		}
//	}
	
	@Bean
	public CookieLocaleResolver cookieLocaleResolver() {
		CookieLocaleResolver resolver = new CookieLocaleResolver();
		resolver.setDefaultLocale(Locale.ENGLISH);
		resolver.setCookieName(env.getRequiredProperty("cookieLocaleResolver.cookieName"));
		resolver.setCookieMaxAge(env.getProperty("cookieLocaleResolver.cookieMaxAge", Integer.class));
		return resolver;
	}
		
	@Override
    public void addInterceptors(InterceptorRegistry registry) {
		LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
		interceptor.setParamName(env.getRequiredProperty("localeChangeInterceptor.paramName"));
        registry.addInterceptor(interceptor);
    }
	
	@Bean
	public CommonsMultipartResolver commonsMultipartResolver() {
		CommonsMultipartResolver resolver = new CommonsMultipartResolver();
		return resolver;
	}
	
	@Bean 
	public ControllerClassNameHandlerMapping controllerClassNameHandlerMapping() {
		ControllerClassNameHandlerMapping mapping = new ControllerClassNameHandlerMapping();
		return mapping;
	}
	
	
}
