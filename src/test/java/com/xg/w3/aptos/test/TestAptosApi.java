package com.xg.w3.aptos.test;

import com.xg.w3.aptos.AptosApi;
import com.xg.w3.aptos.dto.AccountResDto;
import com.xg.w3.aptos.dto.ResultResDto;
import java.util.List;
import java.util.stream.Collectors;

public class TestAptosApi {
    static final String ROOT_PRIVATE_KEY = "0x00000000000000000000000000000";
    static AptosApi aptosApi = new AptosApi("TESTNET", "https://fullnode.testnet.aptoslabs.com/v1/", ROOT_PRIVATE_KEY);
    static final String WALLET_KEY = "0xb8f8d9251d59c74f61bc2b158dcf3e09d4ee78c5ebc4bfc4d12709f714e3d258";
    
    public static void main(String[] args) {
//        testCreateAccount();
//        testGetSlotResult();
//        testGetSlotMiniResult();
//        testGetSlotDragonResult();
//        testShuffleCardsResult();
//        testRandomBetResult();
//        testGetRandomResult();
//        testGetRandomsResult();
    }
    
    static <T> String listToString(List<T> ls) {
        return String.join(", ", ls.stream().map(String::valueOf).collect(Collectors.toList()));
    }
    
    static void testCreateAccount() {
        AccountResDto account = aptosApi.createAccount();
        assert account != null;
        System.out.println("-- testCreateAccount: ");
        System.out.println("address: " + account.getAddress());
        System.out.println("private key: " + account.getPrivateKey());
    }
    
    static void testGetSlotResult() {
        ResultResDto res = aptosApi.getResultSlot(WALLET_KEY, "1001", System.currentTimeMillis() + "", "1");
        System.out.println("-- testGetSlotResult:");
        System.out.println("hash: " + res.getTxnHash());
        System.out.println("result: " + listToString(res.getResult()));
    }
    
    static void testGetSlotMiniResult() {
        ResultResDto res = aptosApi.getResultSlotMini(WALLET_KEY, "1001", System.currentTimeMillis() + "");
        System.out.println("-- testGetSlotMiniResult:");
        System.out.println("hash: " + res.getTxnHash());
        System.out.println("result: " + listToString(res.getResult()));
    }
    
    static void testGetSlotDragonResult() {
        ResultResDto res = aptosApi.getResultSlotDragon(WALLET_KEY, "1001", System.currentTimeMillis() + "");
        System.out.println("-- testGetSlotDragonResult:");
        System.out.println("hash: " + res.getTxnHash());
        System.out.println("result: " + listToString(res.getResult()));
    }
    
    static void testShuffleCardsResult() {
        String gameCode = System.currentTimeMillis() + "";
        ResultResDto res = aptosApi.shuffleCards(WALLET_KEY, "1001", gameCode, 1);
        System.out.println("-- testShuffleCardsResult:");
        System.out.println("hash: " + res.getTxnHash());
        System.out.println("md5: " + res.getMd5());
        System.out.println("key: " + res.getKey());
        System.out.println("result: " + listToString(res.getResult()));
        
        res = aptosApi.publicGame(WALLET_KEY, "1001", gameCode, res.getMd5(), res.getResult(), res.getKey());
        System.out.println("-- testShuffleCardsResult publicGame:");
        System.out.println("hash: " + res.getTxnHash());
    }
    
    static void testRandomBetResult() {
        String gameCode = System.currentTimeMillis() + "";
        ResultResDto res = aptosApi.randomBet(WALLET_KEY, "1001", gameCode, 0, 5, 3);
        System.out.println("-- testRandomBetResult:");
        System.out.println("hash: " + res.getTxnHash());
        System.out.println("md5: " + res.getMd5());
        System.out.println("key: " + res.getKey());
        System.out.println("result: " + listToString(res.getResult()));
        
        res = aptosApi.publicGame(WALLET_KEY, "1001", gameCode, res.getMd5(), res.getResult(), res.getKey());
        System.out.println("-- testRandomBetResult publicGame:");
        System.out.println("hash: " + res.getTxnHash());
    }
    
    static void testGetRandomResult() {
        ResultResDto res = aptosApi.getRandom(WALLET_KEY, "1001", System.currentTimeMillis() + "", 1, 1000, "test random");
        System.out.println("-- testGetRandomResult:");
        System.out.println("hash: " + res.getTxnHash());
        System.out.println("result: " + res.getRandom());
    }
    
    static void testGetRandomsResult() {
        ResultResDto res = aptosApi.getRandoms(WALLET_KEY, "1001", System.currentTimeMillis() + "", 1, 1000, 10, "test random");
        System.out.println("-- testGetRandomsResult:");
        System.out.println("hash: " + res.getTxnHash());
        System.out.println("result: " + listToString(res.getRandoms()));
    }
}
