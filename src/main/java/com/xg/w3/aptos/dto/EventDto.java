package com.xg.w3.aptos.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class EventDto {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EventData {
        String game_code;
        String game_id;
        String rate_id;
        String player;
        List<Integer> result;
    }
    
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DragonEventData {
        String game_code;
        String game_id;
        String player;
        List<List<Integer>> result;
    }
    
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BetPlayEventData {
        String game_code;
        String game_id;
        String md5;
        String player;
    }
    
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RandomnessEventData {
        String game_code;
        String game_id;
        String result;
        String player;
        String rewards;
    }
    
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MultiRandomnessEventData {
        String game_code;
        String game_id;
        List<Long> result;
        String player;
        String rewards;
    }
}
