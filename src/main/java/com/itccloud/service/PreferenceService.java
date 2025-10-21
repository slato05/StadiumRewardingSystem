package com.itccloud.service;

import com.itccloud.mapper.PreferenceMapper;
import com.itccloud.model.Fan;
import com.itccloud.model.Preference;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Service
public class PreferenceService {
    private final PreferenceMapper mapper;

    public PreferenceService(PreferenceMapper mapper) {
        this.mapper = mapper;
    }

    public void addPreference(Fan fan, String standId, String time) {
        if (mapper.findDuplicateFan(fan.getFirstName(), fan.getLastName()) > 0)
            throw new IllegalArgumentException("Duplicate name found: " + fan.getFirstName() + " " + fan.getLastName());

        mapper.insertFan(fan);

        Preference pref = new Preference();
        pref.setFanId(fan.getFanId());
        pref.setStandId(standId);
        pref.setReservationTime(Timestamp.valueOf(time));
        mapper.insertPreference(pref);
    }

    @Transactional
    public void importPreferences(List<Map<String, Object>> importedFans, boolean append) {
        if (!append) mapper.clearPreferences();



        for (Map<String, Object> data : importedFans) {
            Fan fan = new Fan();
            fan.setFirstName((String) data.get("firstName"));
            fan.setLastName((String) data.get("lastName"));
            fan.setEmail((String) data.get("email"));
            fan.setPhoneNumber((String) data.get("phone"));
            fan.setOccupation((String) data.get("firstOccupation"));
            addPreference(fan, (String) data.get("preferredStand"), (String) data.get("reservationTime"));
        }
    }
}


