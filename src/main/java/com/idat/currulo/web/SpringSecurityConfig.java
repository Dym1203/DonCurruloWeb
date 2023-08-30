package com.idat.currulo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.idat.currulo.web.service.UsuarioService;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
		auth.setUserDetailsService(usuarioService);
		auth.setPasswordEncoder(passwordEncoder);
		return auth;
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/", "/index").hasAnyAuthority("Gerente", "Vendedora", "Repartidor")
	
		/*Rutas para el formulario de Registrarse y Recuperar Contraseña*/
		.antMatchers("/register").permitAll()
		.antMatchers("/recoverpassword").permitAll()
		.antMatchers("/insertcode").permitAll()
		.antMatchers("/newpassword").permitAll()
		
		/*Rutas para el CRUD de cada tabla*/
		.antMatchers("/categorias").hasAnyAuthority("Gerente", "Vendedora")
		.antMatchers("/categorias/nuevaCategoria").hasAuthority("Vendedora")
		.antMatchers("/categorias/editar/*").hasAuthority("Vendedora")
		.antMatchers("/categorias/*").hasAuthority("Vendedora")
		
		.antMatchers("/clientes").hasAnyAuthority("Gerente", "Vendedora", "Repartidor")
		.antMatchers("/clientes/nuevoCliente").hasAuthority("Vendedora")
		.antMatchers("/clientes/editar/*").hasAuthority("Vendedora")
		.antMatchers("/clientes/*").hasAuthority("Vendedora")
		.antMatchers("/detallescliente/*").hasAnyAuthority("Gerente", "Vendedora", "Repartidor")
		
		.antMatchers("/empleados").hasAuthority("Gerente")
		.antMatchers("/empleados/nuevoEmpleado").hasAuthority("Gerente")
		.antMatchers("/empleados/editar/*").hasAuthority("Gerente")
		.antMatchers("/empleados/*").hasAuthority("Gerente")
		.antMatchers("/detallesempleado/*").hasAuthority("Gerente")
		
		.antMatchers("/identificaciones").hasAuthority("Gerente")
		.antMatchers("/identificaciones/nuevoTipoIdentificacion").hasAuthority("Gerente")
		.antMatchers("/identificaciones/editar/*").hasAuthority("Gerente")
		.antMatchers("/identificaciones/*").hasAuthority("Gerente")
		
		.antMatchers("/pedidos").hasAnyAuthority("Vendedora", "Repartidor")
		.antMatchers("/pedidos/nuevoPedido").hasAuthority("Vendedora")
		.antMatchers("/pedidos/editar/*").hasAnyAuthority("Vendedora", "Repartidor")
		.antMatchers("/pedidos/*").hasAuthority("Vendedora")
		.antMatchers("/detallespedido/*").hasAnyAuthority("Vendedora", "Repartidor")
		
		.antMatchers("/platos").hasAnyAuthority("Gerente", "Vendedora", "Repartidor")
		.antMatchers("/platos/nuevoPlato").hasAuthority("Vendedora")
		.antMatchers("/platos/editar/*").hasAuthority("Vendedora")
		.antMatchers("/platos/*").hasAuthority("Vendedora")
		.antMatchers("/detallesproducto/*").hasAnyAuthority("Gerente", "Vendedora", "Repartidor")
		
		.antMatchers("/proveedores").hasAuthority("Gerente")
		.antMatchers("/proveedores/nuevoProveedor").hasAuthority("Gerente")
		.antMatchers("/proveedores/editar/*").hasAuthority("Gerente")
		.antMatchers("/proveedores/*").hasAuthority("Gerente")
		
		.antMatchers("/compras").hasAuthority("Gerente")
		.antMatchers("/compras/nuevaCompra").hasAuthority("Gerente")
		.antMatchers("/compras/editar/*").hasAuthority("Gerente")
		.antMatchers("/compras/*").hasAuthority("Gerente")
		.antMatchers("/detallescompra/*").hasAuthority("Gerente")
		
		.antMatchers("/roles").hasAuthority("Gerente")
		.antMatchers("/roles/nuevoRol").hasAuthority("Gerente")
		.antMatchers("/roles/editar/*").hasAuthority("Gerente")
		.antMatchers("/roles/*").hasAuthority("Gerente")
		
		.antMatchers("/usuarios").hasAuthority("Gerente")
		.antMatchers("/usuarios/nuevoUsuario").hasAuthority("Gerente")
		.antMatchers("/usuarios/editar/*").hasAuthority("Gerente")
		.antMatchers("/usuarios/*").hasAuthority("Gerente")
		.antMatchers("/detallesusuario/*").hasAuthority("Gerente")
		
		/*Rutas para exportación en PDF y Excel*/
		.antMatchers("/exportarProductoPDF").hasAuthority("Gerente")
		.antMatchers("/exportarProductoExcel").hasAuthority("Gerente")
		.antMatchers("/exportarProveedorPDF").hasAuthority("Gerente")
		.antMatchers("/exportarProveedorExcel").hasAuthority("Gerente")
		.antMatchers("/exportarEmpleadoPDF").hasAuthority("Gerente")
		.antMatchers("/exportarEmpleadoExcel").hasAuthority("Gerente")
		.antMatchers("/exportarClientePDF").hasAuthority("Gerente")
		.antMatchers("/exportarClienteExcel").hasAuthority("Gerente")
		.antMatchers("/exportarPedidoPDF").hasAuthority("Vendedora")
		.antMatchers("/exportarPedidoExcel").hasAuthority("Vendedora")
		.antMatchers("/exportarCompraPDF").hasAuthority("Gerente")
		.antMatchers("/exportarCompraExcel").hasAuthority("Gerente")
		
		/*Rutas para exportación de Compras y Pedidos en Comprobantes (JasperReports)*/
		.antMatchers("/generarBoletaVenta/*").hasAuthority("Vendedora")
		.antMatchers("/generarFacturaCompra/*").hasAuthority("Gerente")
		
		/*Acceso a las carpetas de static*/
		.antMatchers("/css/**").permitAll()
		.antMatchers("/docs/**").permitAll()
		.antMatchers("/images/**").permitAll()
		.antMatchers("/js/**").permitAll()
		.antMatchers("/uploads/**").permitAll()
		.anyRequest().authenticated()
		.and()
		.formLogin().loginPage("/login")
		.permitAll()
		.and()
		.logout()
		.invalidateHttpSession(true)
		.clearAuthentication(true)
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		.deleteCookies("JSESSIONID")
		.logoutSuccessUrl("/login?logout")
		.permitAll()
		.and()
		.exceptionHandling().accessDeniedPage("/error_403");
	}
	
}