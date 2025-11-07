package com.itccloud.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itccloud.mapper.PreferenceMapper;
import com.itccloud.model.PreferenceSummary;
import org.springframework.stereotype.Service;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class PreferenceSummaryService {

    private final PreferenceMapper preferenceMapper;

    public PreferenceSummaryService(PreferenceMapper preferenceMapper) {
        this.preferenceMapper = preferenceMapper;
    }

    public List<PreferenceSummary> loadPreferenceSummary() {
        try {
            // Load configuration JSON from resources
            ObjectMapper mapper = new ObjectMapper();
            InputStream inputStream = getClass()
                    .getClassLoader()
                    .getResourceAsStream("preference-summary.json");

            if (inputStream == null) {
                throw new RuntimeException("preference-summary.json not found in resources");
            }

            Map<String, List<Map<String, Object>>> data = mapper.readValue(inputStream, Map.class);
            List<Map<String, Object>> stands = data.get("stands");

            List<PreferenceSummary> list = new ArrayList<>();

            for (Map<String, Object> s : stands) {

                String standId = (String) s.get("standId");
                String standName = (String) s.get("standName");
                int availableSeats = (Integer) s.get("availableSeats");
                BigDecimal discountPrice =
                        new BigDecimal(s.get("discountPrice").toString());

                // LIVE value from the database
                int preferredCount = preferenceMapper.countPreferencesByStand(standId);

                // Compute earnings dynamically
                BigDecimal estimatedTotalEarnings =
                        discountPrice.multiply(BigDecimal.valueOf(preferredCount));

                // Build the model object
                PreferenceSummary ps = new PreferenceSummary();
                ps.setStandId(standId);
                ps.setStandName(standName);
                ps.setAvailableSeats(availableSeats);
                ps.setDiscountPrice(discountPrice);
                ps.setNumberOfPreferredSeats(preferredCount);
                ps.setEstimatedTotalEarnings(estimatedTotalEarnings);

                list.add(ps);
            }

            return list;

        } catch (Exception e) {
            throw new RuntimeException("Failed to load preference-summary.json", e);
        }
    }
}
