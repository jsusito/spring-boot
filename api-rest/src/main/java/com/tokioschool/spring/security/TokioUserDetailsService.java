package com.tokioschool.spring.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tokioschool.spring.domain.Role;
import com.tokioschool.spring.domain.User;
import com.tokioschool.spring.service.UserService;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class TokioUserDetailsService implements UserDetailsService {

	private final UserService userService;
	
	//Tenemos que implementar este método para que busque un Usuario a fin de Autentificarse en Spring
	//Spring llama a esta función cuando un usuario se autentifica en la web.
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		//Hacen falta tambien los roles que tienen los permisos, cuando traemos el usuario, traemos los roles, para implementar userDetails,
		// Que estará compuesto por el usuario y sus roles.
		UserDetails userDetails = null;
		
		//Miramos si está el usuario, sino lanzamos una excepcion
		
		User user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Usuario/Contraseña incorrecta"));
		//Traemos la lista de los roles que tiene el usuario.
		List<GrantedAuthority> authorities = getUserAuthority(user.getRoles());
		
		userDetails = buildUserForAuthentication(user, authorities);
		return userDetails;
	}
	
	
	
	//Vamos a devolver una lista de objetos que implementan esta interface GrantedAuthority
	//Esta Implementacion es la encargada de saber los permisos que tiene un usuario
	//Anadimos en cada posicion un objeto que contiene un rol del usuario
	private List<GrantedAuthority> getUserAuthority(Set<Role> userRoles){
		List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
		
		userRoles.forEach(userRole -> roles.add(new SimpleGrantedAuthority(userRole.getName())));
		/*El objeto SimpleGrantedAuthority implementa la interface GrantedAuthority dandole los permisos de los 
		 * usuarios
		 */
		
		return new ArrayList<>(roles);		
	}
	
	//Creamos un metodo para devolver el objeto UserDetails que es el que llama Spring para Comprobar la
	//Autentifiación de los usuarios.
	private UserDetails buildUserForAuthentication(User user, List<GrantedAuthority> authorities) {
		return new org.springframework.security.core.userdetails.User(
				user.getUsername(),
				user.getPassword(),
                user.isActive(),
                true, //cuenta expira
                true, //credenciales no expiran?
                true, //cuenta no bloqueada
                authorities);
		}
}
