package com.itccloud.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RewardViewController {

    @GetMapping("/reward")
    public String rewardPage() {
        return "reward";
    }
}

