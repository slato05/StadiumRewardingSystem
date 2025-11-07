package com.itccloud.controller.view;

import com.itccloud.model.PreferenceSummary;
import com.itccloud.service.PreferenceSummaryService;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class PreferenceViewController {

    private final PreferenceSummaryService summaryService;

    @Autowired
    public PreferenceViewController(PreferenceSummaryService summaryService) {
        this.summaryService = summaryService;
    }

    @GetMapping("/preferences")
    public String showPreferenceSummary(Model model) {

        // Load live data from JSON + DB
        List<PreferenceSummary> summaries = summaryService.loadPreferenceSummary();

        // Make available to Thymeleaf
        model.addAttribute("summaries", summaryService.loadPreferenceSummary());

        return "preference";   // Loads preferences.html
    }
}
