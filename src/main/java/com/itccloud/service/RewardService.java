package com.itccloud.service;

import com.itccloud.mapper.PreferenceMapper;
import com.itccloud.mapper.RewardMapper;
import com.itccloud.model.PreferenceEntry;
import com.itccloud.model.Reward;
import com.itccloud.model.RewardResult;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RewardService {

    private final PreferenceMapper preferenceMapper;
    private final RewardMapper rewardMapper;

    public RewardService(PreferenceMapper preferenceMapper, RewardMapper rewardMapper) {
        this.preferenceMapper = preferenceMapper;
        this.rewardMapper = rewardMapper;
    }

    private static final List<String> EAST_SEATS = List.of("E001", "E002", "E003");
    private static final List<String> WEST_SEATS = List.of("W501", "W502", "W503");
    private static final List<String> SOUTH_SEATS = List.of("S101", "S102", "S103", "S104", "S105");
    private static final List<String> NORTH_SEATS = List.of("N601", "N602", "N603", "N604", "N605");

    public void runRewardingProcess() {

        // 1. Reset rewards
        rewardMapper.clearRewards();

        // 2. Load all fan+preference entries
        List<PreferenceEntry> prefs = preferenceMapper.findAllPreferences();

        // Group by stand
        Map<String, List<PreferenceEntry>> byStand =
                prefs.stream().collect(Collectors.groupingBy(PreferenceEntry::getStandId));

        // EAST – FCFS
        assignFCFS(byStand.get("EAST"), EAST_SEATS);

        // WEST – FCFS
        assignFCFS(byStand.get("WEST"), WEST_SEATS);

        // SOUTH – Random
        assignRandom(byStand.get("SOUTH"), SOUTH_SEATS);

        // NORTH – Weighted random
        assignNorthWeighted(byStand.get("NORTH"), NORTH_SEATS);
    }

    // First-Come-First-Served
    private void assignFCFS(List<PreferenceEntry> list, List<String> seats) {
        if (list == null || list.isEmpty()) return;

        List<PreferenceEntry> sorted =
                list.stream()
                        .sorted(Comparator.comparing(PreferenceEntry::getReservationTime))
                        .limit(seats.size())
                        .toList();

        for (int i = 0; i < sorted.size(); i++) {
            rewardMapper.insertReward(new Reward(
                    0,
                    sorted.get(i).getFanId(),
                    seats.get(i)
            ));
        }
    }

    // SOUTH: Random
    private void assignRandom(List<PreferenceEntry> list, List<String> seats) {
        if (list == null || list.isEmpty()) return;

        Collections.shuffle(list);

        List<PreferenceEntry> chosen = list.stream().limit(seats.size()).toList();

        for (int i = 0; i < chosen.size(); i++) {
            rewardMapper.insertReward(new Reward(
                    0,
                    chosen.get(i).getFanId(),
                    seats.get(i)
            ));
        }
    }

    // NORTH: Weighted Random
    private void assignNorthWeighted(List<PreferenceEntry> list, List<String> seats) {
        if (list == null || list.isEmpty()) return;

        List<PreferenceEntry> selected = weightedSelection(list, seats.size());

        for (int i = 0; i < selected.size(); i++) {
            rewardMapper.insertReward(new Reward(
                    0,
                    selected.get(i).getFanId(),
                    seats.get(i)
            ));
        }
    }

    // Weighted random with military priority
    private List<PreferenceEntry> weightedSelection(List<PreferenceEntry> list, int count) {

        List<PreferenceEntry> pool = new ArrayList<>();

        for (PreferenceEntry p : list) {
            boolean isMilitary = "MLT".equals(p.getOccupation());

            int slots = isMilitary ? 80 : 20; // 0.8 vs 0.2

            for (int i = 0; i < slots; i++) {
                pool.add(p);
            }
        }

        Collections.shuffle(pool);

        Set<Integer> chosenIds = new HashSet<>();
        List<PreferenceEntry> result = new ArrayList<>();

        for (PreferenceEntry candidate : pool) {
            if (result.size() == count) break;

            if (!chosenIds.contains(candidate.getFanId())) {
                chosenIds.add(candidate.getFanId());
                result.add(candidate);
            }
        }

        return result;
    }

    public List<RewardResult> getRewardResults() {
        return rewardMapper.findRewardResults();
    }
}
