package com.example.cropAnalysier.controller;

import com.example.cropAnalysier.model.Irregation;
import com.example.cropAnalysier.service.CropService;
import com.example.cropAnalysier.service.IrrigationService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/irrigation")
public class IrrigationController {

    private final IrrigationService irrigationService;
    private final CropService cropService;

    public IrrigationController(IrrigationService irrigationService, CropService cropService) {
        this.irrigationService = irrigationService;
        this.cropService = cropService;
    }

    @GetMapping
    public String listIrrigations(Model model) {
        model.addAttribute("irrigations", irrigationService.getAllIrrigations());
        return "irrigation/list";
    }

    @GetMapping("/add")
    public String addIrrigationForm(Model model) {
        model.addAttribute("irrigation", new Irregation());
        model.addAttribute("crops", cropService.getAllCrops());
        return "irrigation/add";
    }

    @PostMapping("/add")
    public String saveIrrigation(@Valid @ModelAttribute("irrigation") Irregation irrigationRecord, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("crops", cropService.getAllCrops());
            return "irrigation/add";
        }
        irrigationService.saveIrrigation(irrigationRecord);
        return "redirect:/irrigation";
    }

    @GetMapping("/edit/{id}")
    public String editIrrigationForm(@PathVariable Long id, Model model) {
        Irregation irrigation = irrigationService.getIrrigationById(id)
                .orElseThrow(() -> new IllegalArgumentException("Irrigation record not found with id: " + id));
        model.addAttribute("irrigation", irrigation);
        model.addAttribute("crops", cropService.getAllCrops());
        return "irrigation/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateIrrigation(@PathVariable Long id, @Valid @ModelAttribute("irrigation") Irregation irrigationDetails, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("crops", cropService.getAllCrops());
            return "irrigation/edit";
        }
        irrigationService.updateIrrigation(id, irrigationDetails);
        return "redirect:/irrigation";
    }

    @GetMapping("/delete/{id}")
    public String deleteIrrigation(@PathVariable Long id) {
        irrigationService.deleteIrrigation(id);
        return "redirect:/irrigation";
    }

    @GetMapping("/dashboard")
    public String irrigationDashboard(Model model) {
        List<Irregation> irrigations = irrigationService.getAllIrrigations();
        model.addAttribute("irrigations", irrigations);
        model.addAttribute("latest", irrigations.isEmpty() ? null : irrigations.get(0));
        return "irrigation/dashboard";
    }
}
