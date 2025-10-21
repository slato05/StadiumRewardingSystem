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

    private final PreferenceSummaryService service;

    @Autowired
    public PreferenceViewController(PreferenceSummaryService service) {
        this.service = service;
    }

    @GetMapping("/preferences")
    public String showPreferenceSummary(Model model) {
        List<PreferenceSummary> summaries = service.loadPreferenceSummary();
        model.addAttribute("summaries", summaries);
        return "preference";
    }
}
