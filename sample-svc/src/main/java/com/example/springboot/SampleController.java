package com.example.springboot;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {
    protected int hotStreak = 0;
    protected Logger logger = LoggerFactory.getLogger(SampleController.class);

    @GetMapping("/")
    public Map<String,Object> index() {
        Random r = new Random();
        int low = 1;
        int high = 6;
        int result = r.nextInt(high-low) + low;

        HashMap<String, Object> map = new HashMap<>();
        map.put("diceroll", result);
        map.put("sides", 6);
        boolean lucky = result == 6;
        map.put("lucky", lucky);

        if (result >= 5) {
            hotStreak++;
        } else {
            hotStreak = 0;
        }

        if (result == 1) {
            logger.info("User took the L");
        } else if (hotStreak > 0) {
            logger.info("User is on a hotStreak! " + hotStreak);
        }
        
        map.put("hotStreak", hotStreak);

        return map;
    }
}