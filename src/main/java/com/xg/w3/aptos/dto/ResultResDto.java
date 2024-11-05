package com.xg.w3.aptos.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@AllArgsConstructor
@NoArgsConstructor
public class ResultResDto {
    private String txnHash;
    private List<Integer> result; // 15 item array and random from 0 to 6
    private String md5;
    private String key;
    private long random;
    private List<Long> randoms;
    
    public ResultResDto(String txnHash, List<Integer> result) {
        this.txnHash = txnHash;
        this.result = result;
    }
    
    public ResultResDto(String txnHash, List<Integer> result, String md5, String key) {
        this.txnHash = txnHash;
        this.result = result;
        this.md5 = md5;
        this.key = key;
    }
    
    public ResultResDto(String txnHash, long random) {
        this.txnHash = txnHash;
        this.random = random;
    }
    
    public ResultResDto(String txnHash, List<Long> randoms, boolean randoness) {
        this.txnHash = txnHash;
        this.randoms = randoms;
    }
}
