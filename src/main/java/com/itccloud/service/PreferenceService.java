package com.itccloud.service;

import com.itccloud.mapper.PreferenceMapper;
import com.itccloud.model.Fan;
import com.itccloud.model.Preference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PreferenceService {
    private final PreferenceMapper mapper;

    public PreferenceService(PreferenceMapper mapper) {
        this.mapper = mapper;
    }

    public void addPreference(Fan fan, String standId, String time) {
        // Validate input fields
        if (fan.getFirstName() == null || fan.getFirstName().isBlank() ||
                fan.getLastName() == null || fan.getLastName().isBlank() ||
                fan.getEmail() == null || fan.getEmail().isBlank() ||
                standId == null || standId.isBlank() ||
                time == null || time.isBlank()) {
            throw new IllegalArgumentException("All fields are required, including reservation time.");
        }

        // Validate time format
        Timestamp timestamp;
        try {
            timestamp = Timestamp.valueOf(time);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid time format. Use yyyy-MM-dd HH:mm:ss.SSS");
        }

        // Check duplicates
        if (mapper.findDuplicateFan(fan.getFirstName(), fan.getLastName()) > 0)
            throw new IllegalArgumentException("Duplicate name found: " + fan.getFirstName() + " " + fan.getLastName());

        // Insert only after passing all validation
        mapper.insertFan(fan);

        Preference pref = new Preference();
        pref.setFanId(fan.getFanId());
        pref.setStandId(standId);
        pref.setReservationTime(timestamp);
        mapper.insertPreference(pref);
    }

    @Transactional
    public void importPreferences(List<Map<String, Object>> importedFans, boolean append) {
        // Map JSON to temporary list of Fan + Preference data
        List<Map<String, String>> normalized = importedFans.stream().map(data -> {
            Map<String, String> map = new HashMap<>();
            map.put("firstName", (String) data.get("firstName"));
            map.put("lastName", (String) data.get("lastName"));
            map.put("email", (String) data.get("email"));
            map.put("phone", (String) data.get("phone"));
            map.put("occupation", (String) data.get("firstOccupation")); // some JSON use "firstOccupation"
            map.put("preferredStand", (String) data.get("preferredStand"));
            map.put("reservationTime", (String) data.get("reservationTime"));
            return map;
        }).collect(Collectors.toList());

        // Check for duplicates *within* the JSON file
        Map<String, Long> counts = normalized.stream()
                .collect(Collectors.groupingBy(
                        d -> (d.get("firstName") + "|" + d.get("lastName")).toLowerCase(),
                        Collectors.counting()
                ));

        List<String> duplicatesInFile = counts.entrySet().stream()
                .filter(e -> e.getValue() > 1)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        if (!duplicatesInFile.isEmpty()) {
            throw new IllegalArgumentException("Duplicate names found in import file: " + duplicatesInFile);
        }

        // Check for duplicates *against database* using mapper.findDuplicateFan()
        List<String> duplicatesInDB = new ArrayList<>();
        for (Map<String, String> record : normalized) {
            String firstName = record.get("firstName");
            String lastName = record.get("lastName");
            if (mapper.findDuplicateFan(firstName, lastName) > 0) {
                duplicatesInDB.add(firstName + " " + lastName);
            }
        }

        if (!duplicatesInDB.isEmpty()) {
            throw new IllegalArgumentException("Duplicate names already exist in database: " + duplicatesInDB);
        }

        // If not appending, clear existing preferences
        if (!append) {
            mapper.clearPreferences();
        }

        // Perform inserts now that validation has passed
        for (Map<String, String> data : normalized) {
            Fan fan = new Fan();
            fan.setFirstName(data.get("firstName"));
            fan.setLastName(data.get("lastName"));
            fan.setEmail(data.get("email"));
            fan.setPhoneNumber(data.get("phone"));
            fan.setOccupation(data.get("occupation"));

            addPreference(fan, data.get("preferredStand"), data.get("reservationTime"));
        }
    }
}


