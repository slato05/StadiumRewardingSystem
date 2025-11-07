package com.itccloud.controller.api;

import com.itccloud.model.Reward;
import com.itccloud.model.RewardResult;
import com.itccloud.service.RewardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reward")
public class RewardApiController {

    private final RewardService rewardService;

    public RewardApiController(RewardService rewardService) {
        this.rewardService = rewardService;
    }

    @PostMapping("/run")
    public ResponseEntity<Map<String,Object>> runRewardingProcess() {
        rewardService.runRewardingProcess();
        Map<String,Object> resp = new HashMap<>();
        resp.put("success", true);
        resp.put("message", "Rewarding process completed");
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/results")
    public List<RewardResult> getRewardResults() {
        return rewardService.getRewardResults();
    }
}

