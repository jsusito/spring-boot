package com.tokioschool.web.security;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
    private TokioUserDetailsService userDetailsService;

    @Autowired
    AuthenticationSuccessHandler authenticationSuccessHandler;
    
    //spring llama a este metodo cuando alguien se autentifica en la web
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	//passwordEncoder -- para codificar la contraseña que traemos de la bbdd
    	auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    //llama a este metodo para implementar las restricciones y autorizaciones de cada usuario
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	//añadimos restricciones a nuestra aplicaion y sobre esto damos permisos a uno o quitamos a otros
    	http
    		.authorizeRequests() //para añadir restricciones
    		.antMatchers("/login").permitAll()
    		.antMatchers("/index").permitAll()
    		.antMatchers("/new-user").permitAll()
    		 //Damos permisos a todo el mundo al contexto raiz
    		.antMatchers("/").permitAll()
    		.antMatchers("/admin/**").hasAuthority(Constant.ADMIN_ROLE) //A esta parte de la aplicacion solo podemos entrar si somos administradores
    		.anyRequest().authenticated() //para cualquier otra cosa tienen que estar autenticados
    		.and().formLogin() //Decimos que nosostros vamos a definir nuestro formulario de Login
    		.loginPage(Constant.LOGIN_URL) //ruta para acceder a nuestro formulario de login    		
    		.successHandler(authenticationSuccessHandler)
    		.failureUrl(Constant.LOGIN_FAILURE_URL) //con esto ya acabamos con el login
    		//Cuando hacemos un logout limpia, invalida la sesion, limpia configuracion de autentificacion, borra los ratros del otro usuario
    		.and()
    		.logout() //se encarga de limpiar todo lo de arriba, y lo configuramos
    		.logoutSuccessUrl(Constant.LOGOUT_SUCCESS_URL)    		
    		.logoutRequestMatcher(new AntPathRequestMatcher(Constant.LOGOUT_URL)); //Spring con este url limpia todo
    		 
    }

   
    
    
    /*Acceso a nuestro contenido statico para que todo el mundo pueda acceder*/
    
    @Override
    public void configure(WebSecurity web) throws Exception {
    	//Con esto le decimos que ignore estos direcctorios en los permisos para que 
    	//todo el mundo pueda acceder a él
    	web.ignoring().antMatchers(
                "/resources/**",
                "/static/**",
                "/templates/**",
                "/css/**",
                "/js/**",
                "/images/**",
                "/fonts/**",
                "/webjars/**",
                "/error/**");
    	
    }
}

