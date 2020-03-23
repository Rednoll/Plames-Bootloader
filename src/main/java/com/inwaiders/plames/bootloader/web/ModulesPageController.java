package com.inwaiders.plames.bootloader.web;

import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.inwaiders.plames.api.module.Module;
import com.inwaiders.plames.api.module.ModuleRegistry;

@Controller
public class ModulesPageController {

	@GetMapping("/modules")
	public String modulesPage(Model model) {
		
		Set<Module> modules = ModuleRegistry.getModules();
		
		model.addAttribute("modules", modules);
		
		return "modules";
	}
}
