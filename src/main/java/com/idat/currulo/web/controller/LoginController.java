package com.idat.currulo.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
	
	private int intentosFallidos = 0;
	
	@GetMapping(value = "/login")
	public String login (@RequestParam(name = "error", required = false) String error,
						@RequestParam(name = "logout", required = false) String logout,
						Model model) {
		if (error != null) {
			intentosFallidos++;
			model.addAttribute("errorMessage", "¡Usuario y/o contraseña inválidos!");
			if (intentosFallidos == 3) {
				model.addAttribute("bloqueado", true);
				intentosFallidos = 0;
			}
		}
		else {
			intentosFallidos = 0;
		}
		if (logout != null) {
			model.addAttribute("successMessage", "¡Ha cerrado sesión exitosamente!");
		}
		return "login";
	}
	
}