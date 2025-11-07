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

public class NorthStandWeightedTest {

    private static final String RUN_URL = "http://localhost:8080/api/reward/run";
    private static final String LIST_URL = "http://localhost:8080/api/reward/results";
    private static final int NUM_RUNS = 200;   // <-- you can change this

    private static final HttpClient client = HttpClient.newHttpClient();
    private static final ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws Exception {

        System.out.println("Starting weighted randomness test...");
        Thread.sleep(1000);

        List<RunResult> results = new ArrayList<>();

        for (int i = 1; i <= NUM_RUNS; i++) {
            System.out.println("Running test #" + i);

            runRewardProcess();
            Thread.sleep(50); // small delay to avoid flooding

            List<RewardEntry> winners = fetchRewardList();
            RunResult rr = analyzeRun(i, winners);
            results.add(rr);
        }

        saveResultsToCSV(results);
        printSummary(results);

        System.out.println("\n✅ Test complete! Results saved to weighted_north_test.csv");
    }

    // ------------------------
    // HTTP CALLS
    // ------------------------

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

        HttpResponse<String> response = client.send(req, HttpResponse.BodyHandlers.ofString());
        return Arrays.asList(mapper.readValue(response.body(), RewardEntry[].class));
    }

    // ------------------------
    // ANALYSIS
    // ------------------------

    private static RunResult analyzeRun(int runId, List<RewardEntry> rewards) {
        int mlt = 0;
        int other = 0;

        for (RewardEntry r : rewards) {
            if (!r.getSeatId().startsWith("N"))
                continue; // only North stand

            if ("MLT".equals(r.getOccupation()))
                mlt++;
            else
                other++;
        }
        return new RunResult(runId, mlt, other);
    }

    // ------------------------
    // SAVE CSV
    // ------------------------

    private static void saveResultsToCSV(List<RunResult> results) throws IOException {
        FileWriter fw = new FileWriter("weighted_north_test.csv");

        fw.write("run,mltWins,otherWins,totalNorthWinners,timestamp\n");

        for (RunResult r : results) {
            fw.write(String.format(
                    "%d,%d,%d,%d,%s\n",
                    r.runId,
                    r.mltWins,
                    r.otherWins,
                    (r.mltWins + r.otherWins),
                    LocalDateTime.now()
            ));
        }

        fw.close();
    }

    // ------------------------
    // SUMMARY PRINT
    // ------------------------

    private static void printSummary(List<RunResult> results) {
        double avgMLT = results.stream().mapToInt(r -> r.mltWins).average().orElse(0);
        double avgOther = results.stream().mapToInt(r -> r.otherWins).average().orElse(0);

        long totalMLT = results.stream().mapToInt(r -> r.mltWins).sum();
        long totalOther = results.stream().mapToInt(r -> r.otherWins).sum();

        double pctMLT = totalMLT * 100.0 / (totalMLT + totalOther);

        System.out.println("\n===== SUMMARY =====");
        System.out.println("Runs: " + results.size());
        System.out.println("Avg MLT per run: " + avgMLT);
        System.out.println("Avg Other per run: " + avgOther);
        System.out.println("Total MLT wins: " + totalMLT);
        System.out.println("Total Other wins: " + totalOther);
        System.out.println("MLT proportion: " + pctMLT + "%");
        System.out.println("===================");
    }

    // ------------------------
    // INNER CLASSES
    // ------------------------

    // matches your RewardResult JSON fields
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class RewardEntry {
        public int fanId;
        public String seatId;
        public String occupation;

        public String getSeatId() { return seatId; }
        public int getFanId() { return fanId; }
        public String getOccupation() { return occupation; }
    }

    public static class RunResult {
        public int runId;
        public int mltWins;
        public int otherWins;

        public RunResult(int runId, int mltWins, int otherWins) {
            this.runId = runId;
            this.mltWins = mltWins;
            this.otherWins = otherWins;
        }
    }
}

