package app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "app")
@PropertySource("classpath:application.properties")
public class AppConfiguration {

	@Value("${datasource.driver}")
	private String driver;

	@Value("${datasource.url}")
	private String url;

	@Value("${datasource.username}")
	private String username;

	@Value("${datasource.password}")
	private String password;

	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");

		return viewResolver;
	}

	@Bean
	public SingleConnectionDataSource dataSource() {
		SingleConnectionDataSource dataSource = new SingleConnectionDataSource();
		dataSource.setDriverClassName(driver;);
		dataSource.setUrl(url;);
		dataSource.setUsername(username;);
		dataSource.setPassword(password;);
		return dataSource;
	}

	@Bean
	public JdbcTemplate jdbcTemplate(SingleConnectionDataSource dataSource) {
		JdbcTemplate template = new JdbcTemplate(dataSource);
		return template;
	}

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
}