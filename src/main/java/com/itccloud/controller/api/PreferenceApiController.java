package com.itccloud.controller.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itccloud.model.PreferenceRequest;
import com.itccloud.service.PreferenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/preferences")
public class PreferenceApiController {

    private final PreferenceService service;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public PreferenceApiController(PreferenceService service) {
        this.service = service;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addPreference(@RequestBody PreferenceRequest req) {
        try {
            service.addPreference(req.getFan(), req.getStandId(), req.getReservationTime());
            return ResponseEntity.ok("Preference added successfully!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/import")
    public ResponseEntity<?> importPreferences(@RequestParam("file") MultipartFile file,
                                               @RequestParam(defaultValue = "false") boolean append) {
        try {
            List<Map<String, Object>> data =
                    objectMapper.readValue(file.getInputStream(), new TypeReference<>() {});
            service.importPreferences(data, append);
            return ResponseEntity.ok("Import successful!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Import failed: " + e.getMessage());
        }
    }
}


