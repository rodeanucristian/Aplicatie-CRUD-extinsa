package com.proiect.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.NumberUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.proiect.model.Cladire;
import com.proiect.repository.CladireRepository;

@Controller
@RequestMapping("cladiri")
public class CladireController {

	@Autowired
	CladireRepository cladireRepository;

	@GetMapping("all")
	public String getAllCladiri(Model model) {

		List<Cladire> cladiri = cladireRepository.findAll();

		model.addAttribute("cladiri", cladiri);

		return "cladiri/index";
	}

	@GetMapping("add")
	public String addCladire(@ModelAttribute Cladire cladire) {
		return "cladiri/add";

	}

	@PostMapping("add")
	public String addCladire(@Valid Cladire cladire, BindingResult bindingResult, RedirectAttributes redirectAttributes,
			Model model) {

		if (bindingResult.hasErrors()) {
			return "cladiri/add";
		}

		redirectAttributes.addFlashAttribute("message", "Cladire adaugata");
		redirectAttributes.addFlashAttribute("alertClass", "alert-success");
		cladireRepository.save(cladire);

		return "redirect:/cladiri/add";

	}

	@GetMapping("/edit/{id}")
	public String editCladire(@PathVariable int id, Model model) {

		Cladire cladire = cladireRepository.getOne(id);

		model.addAttribute("cladire", cladire);

		return "cladiri/edit";

	}

	@PostMapping("/edit")
	public String editCladire(@Valid Cladire cladire, BindingResult bindingResult,
			RedirectAttributes redirectAttributes, Model model) {

		Cladire cladireCurrent = cladireRepository.getOne(cladire.getId());

		if (bindingResult.hasErrors()) {
			model.addAttribute("cladireNume", cladireCurrent.getNume());
			return "cladiri/edit";
		}

		redirectAttributes.addFlashAttribute("message", "Cladire editata");
		redirectAttributes.addFlashAttribute("alertClass", "alert-success");
		cladireRepository.save(cladire);

		return "redirect:/cladiri/edit/" + cladire.getId();

	}

	@GetMapping("/delete/{id}")
	public String deleteCladire(@PathVariable int id, RedirectAttributes redirectAttributes) {

		cladireRepository.deleteById(id);

		redirectAttributes.addFlashAttribute("message", "Cladirea a fost stearsa");
		redirectAttributes.addFlashAttribute("alertClass", "alert-success");

		return "redirect:/cladiri/all";

	}

}
