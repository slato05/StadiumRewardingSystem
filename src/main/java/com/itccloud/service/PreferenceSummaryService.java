package com.itccloud.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itccloud.model.PreferenceSummary;
import org.springframework.stereotype.Service;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class PreferenceSummaryService {

    public List<PreferenceSummary> loadPreferenceSummary() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream inputStream = getClass()
                    .getClassLoader()
                    .getResourceAsStream("preference-summary.json");

            Map<String, List<Map<String, Object>>> data = mapper.readValue(inputStream, Map.class);
            List<Map<String, Object>> stands = data.get("stands");

            return stands.stream().map(s -> {
                PreferenceSummary ps = new PreferenceSummary();
                ps.setStandId((String) s.get("standId"));
                ps.setStandName((String) s.get("standName"));
                ps.setAvailableSeats((Integer) s.get("availableSeats"));
                ps.setNumberOfPreferredSeats((Integer) s.get("numberOfPreferredSeats"));
                ps.setDiscountPrice(new BigDecimal(s.get("discountPrice").toString()));

                // Compute estimatedTotalEarnings = preferredSeats * discountPrice
                BigDecimal earnings = ps.getDiscountPrice()
                        .multiply(BigDecimal.valueOf(ps.getNumberOfPreferredSeats()));
                ps.setEstimatedTotalEarnings(earnings);

                return ps;
            }).toList();
        } catch (Exception e) {
            throw new RuntimeException("Failed to load preference-summary.json", e);
        }
    }
}
