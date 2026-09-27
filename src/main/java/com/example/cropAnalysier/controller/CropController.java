package com.example.cropAnalysier.controller;

import com.example.cropAnalysier.model.crop;
import com.example.cropAnalysier.service.CropService;
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
@RequestMapping("/crops")
public class CropController {
    private final CropService cropService;

    public CropController(CropService cropService) {
        this.cropService = cropService;
    }

    @GetMapping
    public String listCrops(Model model) {
        model.addAttribute("crops", cropService.getAllCrops());
        return "crop/list";
    }

    @GetMapping("/add")
    public String addCropForm(Model model) {
        model.addAttribute("crop", new crop());
        return "crop/add";
    }

    @PostMapping("/add")
    public String saveCrop(@Valid @ModelAttribute("crop") crop cropRecord, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "crop/add";
        }
        cropService.saveCrop(cropRecord);
        return "redirect:/crops";
    }

    @GetMapping("/edit/{id}")
    public String editCropForm(@PathVariable Long id, Model model) {
        crop cropRecord = cropService.getCropById(id)
                .orElseThrow(() -> new IllegalArgumentException("Crop not found with id: " + id));
        model.addAttribute("crop", cropRecord);
        return "crop/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateCrop(@PathVariable Long id, @Valid @ModelAttribute("crop") crop cropDetails, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "crop/edit";
        }
        cropService.updateCrop(id, cropDetails);
        return "redirect:/crops";
    }

    @GetMapping("/delete/{id}")
    public String deleteCrop(@PathVariable Long id) {
        cropService.deleteCrop(id);
        return "redirect:/crops";
    }
}
