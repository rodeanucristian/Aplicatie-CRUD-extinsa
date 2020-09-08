package com.proiect.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.proiect.model.Camera;
import com.proiect.model.Cladire;
import com.proiect.model.Usa;
import com.proiect.repository.CameraRepository;
import com.proiect.repository.CladireRepository;

@Controller
@RequestMapping("camere")
public class CameraController {

	@Autowired
	CameraRepository cameraRepository;

	@Autowired
	CladireRepository cladireRepository;

	@GetMapping("all")
	public String getAllCamere(Model model) {

		List<Camera> camere = cameraRepository.findAll();
		List<Cladire> cladiri = cladireRepository.findAll();

		// iau id din obiectul Cladire si il pun in cladire_id
		for (int i = 0; i < camere.size(); i++) {

			if (camere.get(i).getCladire() != null) {

				camere.get(i).setCladire_id(camere.get(i).getCladire().getId());

			}
		}

		//ma va ajuta la combo-boxuri pentru a gasi numele cladirilor dupa id 
		HashMap<Integer, String> clads = new HashMap<>();

		for (Cladire clad : cladiri) {

			clads.put(clad.getId(), clad.getNume());

		}
		model.addAttribute("camere", camere);
		model.addAttribute("clads", clads);

		return "camere/index";
	}

	@GetMapping("add")
	public String addCamera(Camera camera, Model model) {

		List<Cladire> cladiri = cladireRepository.findAll();
		model.addAttribute("cladiri", cladiri);

		return "camere/add";

	}

	@PostMapping("add")
	public String addCamera(@Valid Camera camera, BindingResult bindingResult, RedirectAttributes redirectAttributes,
			Model model) {

		List<Cladire> cladiri = cladireRepository.findAll();

		
		//iau id din cladire_id si pun in obiectul Cladire - am nevoie din cauza lui @ManyToOne
		if (camera.getCladire_id() != null) {
			Cladire cladire;
			cladire = cladireRepository.getOne(camera.getCladire_id());
			camera.setCladire(cladire);
		}

		if (bindingResult.hasErrors()) {
			model.addAttribute("cladiri", cladiri);
			return "camere/add";
		}

		redirectAttributes.addFlashAttribute("message", "Camera adaugata");
		redirectAttributes.addFlashAttribute("alertClass", "alert-success");
		cameraRepository.save(camera);

		return "redirect:/camere/add";

	}

	@GetMapping("/edit/{id}")
	public String editCamera(@PathVariable int id, Model model) {

		List<Cladire> cladiri = cladireRepository.findAll();
		model.addAttribute("cladiri", cladiri);

		Camera camera = cameraRepository.getOne(id);

		model.addAttribute("camera", camera);

		return "camere/edit";

	}

	@PostMapping("/edit")
	public String editCamera(@Valid Camera camera, BindingResult bindingResult, RedirectAttributes redirectAttributes,
			Model model) {

		List<Cladire> cladiri = cladireRepository.findAll();

		if (camera.getCladire_id() != null) {
			Cladire cladire;
			cladire = cladireRepository.getOne(camera.getCladire_id());
			camera.setCladire(cladire);
		}

		Camera cameraCurrent = cameraRepository.getOne(camera.getId());

		if (bindingResult.hasErrors()) {
			model.addAttribute("cladiri", cladiri);
			model.addAttribute("cameraNume", cameraCurrent.getNume());
			return "camere/edit";
		}

		if (camera.getCladire_id() != 0) {

			redirectAttributes.addFlashAttribute("message", "Camera editata");
			redirectAttributes.addFlashAttribute("alertClass", "alert-success");
			cameraRepository.save(camera);

		} else {
			redirectAttributes.addFlashAttribute("message", "Alege o cladire");
			redirectAttributes.addFlashAttribute("alertClass", "alert-danger");

		}

		return "redirect:/camere/edit/" + camera.getId();

	}

	@GetMapping("/delete/{id}")
	public String stergeCamera(@PathVariable int id, RedirectAttributes redirectAttributes) {

		cameraRepository.deleteById(id);

		redirectAttributes.addFlashAttribute("message", "Camera a fost stearsa");
		redirectAttributes.addFlashAttribute("alertClass", "alert-success");

		return "redirect:/camere/all";

	}

}
