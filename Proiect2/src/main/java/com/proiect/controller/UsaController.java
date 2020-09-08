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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.proiect.model.Camera;
import com.proiect.model.Cladire;
import com.proiect.model.Usa;
import com.proiect.repository.CameraRepository;
import com.proiect.repository.CladireRepository;
import com.proiect.repository.UsaRepository;

@Controller
@RequestMapping("usi")
public class UsaController {

	@Autowired
	UsaRepository usaRepository;
	@Autowired
	CameraRepository cameraRepository;

	@GetMapping("all")
	public String getAllUsi(Model model) {

		List<Usa> usi = usaRepository.findAll();
		List<Camera> camere = cameraRepository.findAll();

		
		// setez camera1_id si camera2_id	
		for (int i = 0; i < usi.size(); i++) {

			if (usi.get(i).getCamera1() != null) {

				usi.get(i).setCamera1_id(usi.get(i).getCamera1().getId());

			}
		}

		for (int i = 0; i < usi.size(); i++) {

			if (usi.get(i).getCamera1() == null) {

				usi.get(i).setCamera1_id(0);

			}
		}

		for (int i = 0; i < usi.size(); i++) {

			if (usi.get(i).getCamera2() != null) {

				usi.get(i).setCamera2_id(usi.get(i).getCamera2().getId());

			}
		}

		for (int i = 0; i < usi.size(); i++) {

			if (usi.get(i).getCamera2() == null) {

				usi.get(i).setCamera2_id(0);

			}
		}

		HashMap<Integer, String> cams = new HashMap<>();

		for (Camera cam : camere) {

			cams.put(cam.getId(), cam.getNume());

		}

		model.addAttribute("usi", usi);
		model.addAttribute("cams", cams);

		return "usi/index";
	}

	@GetMapping("add")
	public String addUsa(Usa usa, Model model) {

		List<Camera> camere = cameraRepository.findAll();
		model.addAttribute("camere", camere);

		return "usi/add";

	}

	@PostMapping("add")
	public String addUsa(@Valid Usa usa, BindingResult bindingResult, RedirectAttributes redirectAttributes,
			Model model) {

		List<Camera> camere = cameraRepository.findAll();

		
		// setez id din obiectul camera
		if (usa.getCamera1_id() != 0) {
			Camera camera;
			camera = cameraRepository.getOne(usa.getCamera1_id());
			usa.setCamera1(camera);
		}

		if (usa.getCamera2_id() != 0) {
			Camera camera;
			camera = cameraRepository.getOne(usa.getCamera2_id());
			usa.setCamera2(camera);
		}

		if (bindingResult.hasErrors()) {
			model.addAttribute("camere", camere);
			return "usi/add";
		}

		
		//tratez mai multe erori
		if (usa.getExterior().equals("exterioara") && usa.getCamera2_id() != 0) {

			redirectAttributes.addFlashAttribute("message", "Usa nu poate avea o a doua camera");
			redirectAttributes.addFlashAttribute("alertClass", "alert-danger");

		} else if (usa.getExterior().equals("exterioara") && usa.getCamera1_id() == 0) {

			redirectAttributes.addFlashAttribute("message", "Usa trebuie sa aibe o camera");
			redirectAttributes.addFlashAttribute("alertClass", "alert-danger");

		}

		else if (usa.getExterior().equals("interioara") && usa.getCamera1_id() == 0
				|| usa.getExterior().equals("interioara") && usa.getCamera2_id() == 0) {

			redirectAttributes.addFlashAttribute("message", "Usa trebuie sa aibe doua camere");
			redirectAttributes.addFlashAttribute("alertClass", "alert-danger");

		} else if (usa.getCamera1_id() == usa.getCamera2_id()) {

			redirectAttributes.addFlashAttribute("message", "Camerele trebuie sa fie diferite");
			redirectAttributes.addFlashAttribute("alertClass", "alert-danger");

		}

		else {

			redirectAttributes.addFlashAttribute("message", "Usa adaugata");
			redirectAttributes.addFlashAttribute("alertClass", "alert-success");
			usaRepository.save(usa);

		}

		return "redirect:/usi/add";

	}

	@GetMapping("/edit/{id}")
	public String editUsa(@PathVariable int id, Model model) {

		List<Camera> camere = cameraRepository.findAll();
		model.addAttribute("camere", camere);

		Usa usa = usaRepository.getOne(id);

		model.addAttribute("usa", usa);

		return "usi/edit";

	}

	@PostMapping("/edit")
	public String editUsa(@Valid Usa usa, BindingResult bindingResult, RedirectAttributes redirectAttributes,
			Model model) {

		List<Camera> camere = cameraRepository.findAll();
		Usa usaCurrent = usaRepository.getOne(usa.getId());

		if (usa.getCamera1_id() != 0) {
			Camera camera;
			camera = cameraRepository.getOne(usa.getCamera1_id());
			usa.setCamera1(camera);
		}

		if (usa.getCamera2_id() != 0) {
			Camera camera;
			camera = cameraRepository.getOne(usa.getCamera2_id());
			usa.setCamera2(camera);
		}

		if (bindingResult.hasErrors()) {
			model.addAttribute("camere", camere);
			model.addAttribute("usaNume", usaCurrent.getNume());
			return "usi/edit";
		}

		if (usa.getExterior().equals("exterioara") && usa.getCamera2_id() != 0) {

			redirectAttributes.addFlashAttribute("message", "Usa nu poate avea o a doua camera");
			redirectAttributes.addFlashAttribute("alertClass", "alert-danger");

		} else if (usa.getExterior().equals("interioara") && usa.getCamera1_id() == 0
				|| usa.getExterior().equals("interioara") && usa.getCamera2_id() == 0) {

			redirectAttributes.addFlashAttribute("message", "Usa trebuie sa aibe doua camere");
			redirectAttributes.addFlashAttribute("alertClass", "alert-danger");

		} else if (usa.getCamera1_id() == usa.getCamera2_id()) {

			redirectAttributes.addFlashAttribute("message", "Camerele trebuie sa fie diferite");
			redirectAttributes.addFlashAttribute("alertClass", "alert-danger");

		}

		else {

			redirectAttributes.addFlashAttribute("message", "Usa adaugata");
			redirectAttributes.addFlashAttribute("alertClass", "alert-success");
			usaRepository.save(usa);

		}

		return "redirect:/usi/edit/" + usa.getId();

	}

	@GetMapping("/delete/{id}")
	public String stergeUsa(@PathVariable int id, RedirectAttributes redirectAttributes) {

		usaRepository.deleteById(id);

		redirectAttributes.addFlashAttribute("message", "Usa a fost stearsa");
		redirectAttributes.addFlashAttribute("alertClass", "alert-success");

		return "redirect:/usi/all";

	}
}
