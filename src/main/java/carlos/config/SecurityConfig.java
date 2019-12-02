package carlos.config;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
 
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
	@Autowired
    private YAMLConfig myConfig;
	
    @Override
    protected void configure(HttpSecurity http) throws Exception 
    {
        http
         .csrf().disable()
         .authorizeRequests().anyRequest().authenticated()
         .and()
         .httpBasic();
    }
  
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) 
            throws Exception 
    {
    	Map<String, String> credentials = myConfig.getCredentials();
    	
    	for (Map.Entry<String, String> entry : credentials.entrySet()) {
    		auth.inMemoryAuthentication()
            .withUser(entry.getKey())
            .password(entry.getValue())
            .roles("USER");
    	}
        
    }
}