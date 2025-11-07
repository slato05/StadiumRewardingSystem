package com.itccloud;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.*;

public class SouthStandRandomTest {

    private static final String RUN_URL = "http://localhost:8080/api/reward/run";
    private static final String LIST_URL = "http://localhost:8080/api/reward/results";
    private static final int NUM_RUNS = 500;

    private static final HttpClient client = HttpClient.newHttpClient();
    private static final ObjectMapper mapper = new ObjectMapper();

    // Tracks total wins per fan across ALL runs
    private static Map<Integer, Integer> fanWinCounts = new HashMap<>();

    public static void main(String[] args) throws Exception {

        System.out.println("Starting SOUTH stand randomness test...");
        Thread.sleep(800);

        List<RunResult> results = new ArrayList<>();

        for (int i = 1; i <= NUM_RUNS; i++) {
            System.out.println("Running test #" + i);

            runRewardProcess();
            Thread.sleep(40);

            List<RewardEntry> winners = fetchRewardList();
            RunResult rr = analyzeRun(i, winners);
            results.add(rr);
        }

        saveRunSummaryCSV(results);
        saveFanCountsCSV();
        printSummary(results);
        printFanFrequency();

        System.out.println("\n✅ SOUTH Stand Test Complete!");
        System.out.println("✅ Files Written: south_run_summary.csv, south_fan_repeat_frequency.csv");
    }

    // ---------------------------------------------------------
    // HTTP CALLS
    // ---------------------------------------------------------

    private static void runRewardProcess() throws Exception {
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(RUN_URL))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();
        client.send(req, HttpResponse.BodyHandlers.ofString());
    }

    private static List<RewardEntry> fetchRewardList() throws Exception {
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(LIST_URL))
                .GET()
                .build();
        HttpResponse<String> res = client.send(req, HttpResponse.BodyHandlers.ofString());
        return Arrays.asList(mapper.readValue(res.body(), RewardEntry[].class));
    }

    // ---------------------------------------------------------
    // ANALYSIS (Per Run)
    // ---------------------------------------------------------

    private static RunResult analyzeRun(int runId, List<RewardEntry> rewards) {

        int totalSouthWinners = 0;

        for (RewardEntry r : rewards) {
            if (!r.getSeatId().startsWith("S")) continue;  // Only SOUTH seats

            totalSouthWinners++;

            // Count wins per fan ID
            fanWinCounts.put(
                    r.getFanId(),
                    fanWinCounts.getOrDefault(r.getFanId(), 0) + 1
            );
        }

        return new RunResult(runId, totalSouthWinners);
    }

    // ---------------------------------------------------------
    // SAVE RUN SUMMARY CSV
    // ---------------------------------------------------------

    private static void saveRunSummaryCSV(List<RunResult> results) throws IOException {
        FileWriter fw = new FileWriter("south_run_summary.csv");

        fw.write("run,totalSouthWinners,timestamp\n");

        for (RunResult r : results) {
            fw.write(String.format(
                    "%d,%d,%s\n",
                    r.runId,
                    r.totalWinners,
                    LocalDateTime.now()
            ));
        }

        fw.close();
    }

    // ---------------------------------------------------------
    // SAVE FAN FREQUENCY CSV
    // ---------------------------------------------------------

    private static void saveFanCountsCSV() throws IOException {
        FileWriter fw = new FileWriter("south_fan_repeat_frequency.csv");
        fw.write("fanId,wins\n");

        for (var entry : fanWinCounts.entrySet()) {
            fw.write(entry.getKey() + "," + entry.getValue() + "\n");
        }

        fw.close();
    }

    // ---------------------------------------------------------
    // PRINT SUMMARY
    // ---------------------------------------------------------

    private static void printSummary(List<RunResult> results) {
        double avgWinners = results.stream().mapToInt(r -> r.totalWinners).average().orElse(0);

        System.out.println("\n===== SOUTH RANDOMNESS SUMMARY =====");
        System.out.println("Total Runs: " + results.size());
        System.out.println("Avg SOUTH winners per run: " + avgWinners);
        System.out.println("====================================");
    }

    // ---------------------------------------------------------
    // PRINT FAN WIN FREQUENCY
    // ---------------------------------------------------------

    private static void printFanFrequency() {
        System.out.println("\n===== FAN WIN FREQUENCY (SOUTH) =====");

        fanWinCounts.entrySet().stream()
                .sorted((a, b) -> Integer.compare(b.getValue(), a.getValue()))
                .forEach(e ->
                        System.out.println("Fan " + e.getKey() + " → " + e.getValue() + " wins")
                );

        System.out.println("======================================");
    }

    // ---------------------------------------------------------
    // INNER CLASSES
    // ---------------------------------------------------------

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class RewardEntry {
        public int fanId;
        public String seatId;

        public int getFanId() { return fanId; }
        public String getSeatId() { return seatId; }
    }

    public static class RunResult {
        public int runId;
        public int totalWinners;

        public RunResult(int runId, int totalWinners) {
            this.runId = runId;
            this.totalWinners = totalWinners;
        }
    }
}

