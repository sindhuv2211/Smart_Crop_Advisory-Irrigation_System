package com.example.cropAnalysier.controller;

import com.example.cropAnalysier.model.farmer;
import com.example.cropAnalysier.service.FarmerService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/farmers")
public class FarmerController {
    private final FarmerService farmerService;

    public FarmerController(FarmerService farmerService) {
        this.farmerService = farmerService;
    }

    @GetMapping
    public String listFarmers(Model model) {
        model.addAttribute("farmers", farmerService.getAllFarmers());
        return "farmer/list";
    }

    @GetMapping("/add")
    public String addFarmerForm(Model model) {
        model.addAttribute("farmer", new farmer());
        return "farmer/add";
    }

    @PostMapping("/add")
    public String saveFarmer(@Valid @ModelAttribute("farmer") farmer farmerRecord, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "farmer/add";
        }
        farmerService.saveFarmer(farmerRecord);
        return "redirect:/farmers";
    }

    @GetMapping("/edit/{id}")
    public String editFarmerForm(@PathVariable Long id, Model model) {
        farmer farmerRecord = farmerService.getFarmerById(id)
                .orElseThrow(() -> new IllegalArgumentException("Farmer not found with id: " + id));
        model.addAttribute("farmer", farmerRecord);
        return "farmer/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateFarmer(@PathVariable Long id, @Valid @ModelAttribute("farmer") farmer farmerDetails, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "farmer/edit";
        }
        farmerService.updateFarmer(id, farmerDetails);
        return "redirect:/farmers";
    }

    @GetMapping("/delete/{id}")
    public String deleteFarmer(@PathVariable Long id) {
        farmerService.deleteFarmer(id);
        return "redirect:/farmers";
    }
}
