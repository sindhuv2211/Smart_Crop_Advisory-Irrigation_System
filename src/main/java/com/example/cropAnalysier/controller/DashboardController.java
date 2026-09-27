package com.example.cropAnalysier.controller;

import com.example.cropAnalysier.model.Irregation;
import com.example.cropAnalysier.model.crop;
import com.example.cropAnalysier.model.farmer;
import com.example.cropAnalysier.service.CropService;
import com.example.cropAnalysier.service.FarmerService;
import com.example.cropAnalysier.service.IrrigationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class DashboardController {

    private final FarmerService farmerService;
    private final CropService cropService;
    private final IrrigationService irrigationService;

    public DashboardController(FarmerService farmerService, CropService cropService, IrrigationService irrigationService) {
        this.farmerService = farmerService;
        this.cropService = cropService;
        this.irrigationService = irrigationService;
    }

    @GetMapping({"/", "/login"})
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("farmer", new farmer());
        return "register";
    }

    @PostMapping("/register")
    public String registerFarmer(@ModelAttribute("farmer") farmer farmerRecord) {
        farmerService.registerNewFarmer(farmerRecord);
        return "redirect:/login";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        List<farmer> farmers = farmerService.getAllFarmers();
        List<crop> crops = cropService.getAllCrops();
        List<Irregation> irrigations = irrigationService.getAllIrrigations();

        model.addAttribute("totalFarmers", farmers.size());
        model.addAttribute("totalCrops", crops.size());
        model.addAttribute("totalIrrigationRecords", irrigations.size());

        if (!irrigations.isEmpty()) {
            Irregation latest = irrigations.get(0);
            model.addAttribute("latestSoilMoisture", latest.getSoilMoisture());
            model.addAttribute("latestStatus", latest.getStatus());
            model.addAttribute("latestIrrigation", latest);
        } else {
            model.addAttribute("latestSoilMoisture", "N/A");
            model.addAttribute("latestStatus", "NO DATA");
            model.addAttribute("latestIrrigation", null);
        }

        model.addAttribute("cropAdvisory", cropService.getCropAdvisorySummary());
        return "dashboard";
    }
}
