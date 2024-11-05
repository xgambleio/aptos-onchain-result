package com.xg.w3.aptos;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xg.w3.aptos.bean.Event;
import com.xg.w3.aptos.bean.Transaction;
import com.xg.w3.aptos.client.Aptos;
import com.xg.w3.aptos.config.ConfigInfo;
import com.xg.w3.aptos.dto.AccountResDto;
import com.xg.w3.aptos.dto.EventDto;
import com.xg.w3.aptos.dto.ReqCardDto;
import com.xg.w3.aptos.dto.ResultResDto;
import com.xg.w3.aptos.novi.serde.Bytes;
import com.xg.w3.aptos.utils.AccountUtils;
import com.xg.w3.aptos.utils.CardUtils;
import com.xg.w3.aptos.utils.Helpers;
import com.xg.w3.aptos.utils.JSONUtil;
import com.xg.w3.aptos.utils.Utils;
import com.xg.w3.util.LogUtil;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.log4j.Logger;

public class AptosApi {
    static final Logger logger = LogUtil.getLogger(AptosApi.class);
    static final double APT_DEPOSIT = 0.0021;
    static final double MIN_APT_REMAIN = 0.002;
    static final int SHUFFLE_COUNT = 10;
    
    private static final ObjectMapper objectMapper;
    static {
        objectMapper = new ObjectMapper();
    }
    
    private final String rootPrivateKey;
    private final Aptos aptos;
    public AptosApi(String network, String nodeUrl, String rootPrivateKey) {
        aptos = new Aptos(network, nodeUrl, null, null, null);
        this.rootPrivateKey = rootPrivateKey;
    }
    
    public AccountResDto createAccount() {
        AccountUtils accountUtils = new AccountUtils();
        return new AccountResDto(accountUtils.accountAddress(), accountUtils.privateKey());
    }
    
    public BigInteger checkBalance(String accAddress) throws Exception {
        try {
            BigInteger balance = aptos.getAccountBalance(accAddress);
            if (balance.compareTo(BigInteger.valueOf((long) (MIN_APT_REMAIN * ConfigInfo.APT_DECIMAL))) < 0) { 
                transfer(accAddress);
            }
            return balance;
        } catch (IOException ex) {
            logger.error("checkBalance ex: ", ex);
        }
        return null;
    }
    
    public synchronized boolean transfer(String accAddress) {
        AccountUtils privateWallet = AccountUtils.fromPrivateKey(rootPrivateKey);
        String hash = aptos.transfer(privateWallet, accAddress, BigInteger.valueOf((long) (APT_DEPOSIT * ConfigInfo.APT_DECIMAL)));
        return hash.length() > 0;
    }
    
    public static ResultResDto buildResultResDto(String txnHash, List<Integer> result, String md5, String key) {
        return new ResultResDto(txnHash, result, md5, key);
    }

    public static ResultResDto buildResultResDto(String txnHash, long rnd) {
        return new ResultResDto(txnHash, rnd);
    }

    public static ResultResDto buildResultResDto(String txnHash, List<Long> randoms) {
        return new ResultResDto(txnHash, randoms, true);
    }
    
    public ResultResDto getResultSlot(String walletKey, String gameId, String gameCode, String rateId) {
        long st = System.currentTimeMillis();
        try {
            AccountUtils accountUtils = AccountUtils.fromPrivateKey(walletKey);
            String func = ConfigInfo.CONTRACT_GAME + "::slot_machine::play";
            List<Object> args = Arrays.asList(gameId, gameCode, rateId);

            checkBalance(accountUtils.accountAddress());

            List<Bytes> trxArgs = Arrays.asList(
                    Helpers.encodeU64Argument(Long.parseLong(gameId)),
                    Helpers.encodeU64Argument(Long.parseLong(gameCode)),
                    Helpers.encodeU64Argument(Long.parseLong(rateId))
            );
            Transaction transaction = aptos.submitTransaction(accountUtils, func, args, trxArgs);
            String txnHash = transaction.getHash();
            System.out.println("transaction is null: " + txnHash);
            for (Event<Object> event : transaction.getEvents()) {
                System.out.println(event.getType());
                if (!event.getType().equals(ConfigInfo.CONTRACT_GAME + "::slot_machine::SlotMachineEvent")) {
                    continue;
                }
                EventDto.EventData eventData = objectMapper.readValue(objectMapper.writeValueAsString(event.getData()), EventDto.EventData.class);

                return buildResultResDto(txnHash, eventData.getResult(), "", "");
            }
        } catch (Exception ex) {
            logger.error("getResultSlot ex: " + (System.currentTimeMillis() - st), ex);
        }
        return null;
    }

    public ResultResDto getResultSlotMini(String walletKey, String gameId, String gameCode) {
        long st = System.currentTimeMillis();
        try {
            AccountUtils accountUtils = AccountUtils.fromPrivateKey(walletKey);
            String func = ConfigInfo.CONTRACT_GAME + "::slot_mini::play";
            List<Object> args = Arrays.asList(gameId, gameCode);
            
            checkBalance(accountUtils.accountAddress());

            List<Bytes> trxArgs = Arrays.asList(
                    Helpers.encodeU64Argument(Long.parseLong(gameId)),
                    Helpers.encodeU64Argument(Long.parseLong(gameCode))
            );
            Transaction transaction = aptos.submitTransaction(accountUtils, func, args, trxArgs);
            logger.info("api getResultSlotMini end: " + (System.currentTimeMillis() - st));

            String txnHash = transaction.getHash();
            for (Event<Object> event : transaction.getEvents()) {
                if (!event.getType().equals(ConfigInfo.CONTRACT_GAME + "::slot_mini::SlotMiniEvent")) {
                    continue;
                }
                EventDto.EventData eventData = objectMapper.readValue(objectMapper.writeValueAsString(event.getData()), EventDto.EventData.class);

                return buildResultResDto(txnHash, eventData.getResult(), "", "");
            }
        } catch (Exception ex) {
            logger.error("api getResultSlotMini ex: " + (System.currentTimeMillis() - st), ex);
        }
        return null;
    }

    public ResultResDto getResultSlotDragon(String walletKey, String gameId, String gameCode) {
        long st = System.currentTimeMillis();
        try {
            AccountUtils accountUtils = AccountUtils.fromPrivateKey(walletKey);
            String func = ConfigInfo.CONTRACT_GAME + "::dragon::play";
            List<Object> args = Arrays.asList(gameId, gameCode);

            checkBalance(accountUtils.accountAddress());

            List<Bytes> trxArgs = Arrays.asList(
                    Helpers.encodeU64Argument(Long.parseLong(gameId)),
                    Helpers.encodeU64Argument(Long.parseLong(gameCode))
            );
            Transaction transaction = aptos.submitTransaction(accountUtils, func, args, trxArgs);
            logger.info("api getResultSlotDragon end : " + (System.currentTimeMillis() - st));

            String txnHash = transaction.getHash();
            Integer[] result = new Integer[15];
            for (Event<Object> event : transaction.getEvents()) {
                if (!event.getType().equals(ConfigInfo.CONTRACT_GAME + "::dragon::DragonEvent")) {
                    continue;
                }
                EventDto.DragonEventData dragonEventData = objectMapper.readValue(objectMapper.writeValueAsString(event.getData()), EventDto.DragonEventData.class);
                int rowIdx = 0;
                for (int row = 0; row < dragonEventData.getResult().size(); ++row) {
                    List<Integer> rows = dragonEventData.getResult().get(row);
                    if (rows.isEmpty()) {
                        continue;
                    }
                    for (int col = 0; col < rows.size(); ++col) {
                        result[rowIdx + col * 5] = rows.get(col);
                    }
                    rowIdx++;
                }
            }

            return buildResultResDto(txnHash, Arrays.asList(result), "", "");
        } catch (Exception ex) {
            logger.info("api getResultSlotDragon ex: " +  (System.currentTimeMillis() - st), ex);
        }
        return null;
    }

    public ResultResDto shuffleCards(String walletKey, String gameId, String gameCode, int packNum) {
        try {
            List<ReqCardDto> reqs = new ArrayList<>();
            List<String> md5s = new ArrayList<>();
            for (int i = 0; i < SHUFFLE_COUNT; ++i) {
                ReqCardDto dto = new ReqCardDto();
                dto.setResult(Utils.getShuffleCards(packNum));
                dto.setKey(Utils.randomString(12));

                List<String> cards = CardUtils.convertCards(dto.getResult());
                String hash = JSONUtil.serialize(cards) + dto.getKey();

                dto.setMd5(Utils.toMD5(hash));
                md5s.add(dto.getMd5());
                reqs.add(dto);
            }
            ResultResDto res = playBetGame(walletKey, gameId, gameCode, md5s);
            if (res != null) {
                for (ReqCardDto req : reqs) {
                    if (req.getMd5().equals(res.getMd5())) {
                        res.setResult(req.getResult());
                        res.setKey(req.getKey());
                        break;
                    }
                }

                return res;
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
        return null;
    }

    public ResultResDto randomBet(String walletKey, String gameId, String gameCode, int from, int to, int count) {
        List<ReqCardDto> reqs = new ArrayList<>();
        List<String> md5s = new ArrayList<>();
        for (int i = 0; i < SHUFFLE_COUNT; ++i) {
            ReqCardDto dto = new ReqCardDto();
            dto.setResult(Utils.getRandomBet(from, to, count));
            dto.setKey(Utils.randomString(12));
            dto.setMd5(Utils.toMD5(JSONUtil.serialize(dto.getResult()) + dto.getKey()));
            md5s.add(dto.getMd5());
            reqs.add(dto);
        }
        ResultResDto res = playBetGame(walletKey, gameId, gameCode, md5s);

        if (res != null) {
            for (ReqCardDto req : reqs) {
                if (req.getMd5().equals(res.getMd5())) {
                    res.setResult(req.getResult());
                    res.setKey(req.getKey());
                    break;
                }
            }
        }
        return res;
    }
    
    private ResultResDto playBetGame(String privateKey, String gameId, String gameCode, List<String> md5s) {
        long st = System.currentTimeMillis();
        String walletAddress = "";
        try {
            AccountUtils accountUtils = AccountUtils.fromPrivateKey(privateKey);
            walletAddress = accountUtils.accountAddress();
            logger.info("api playBetGame start: " + accountUtils.accountAddress());
            String func = ConfigInfo.CONTRACT_GAME + "::bet_game::play";
            List<Object> args = Arrays.asList(gameId, gameCode, md5s);

            checkBalance(accountUtils.accountAddress());

            List<Bytes> trxArgs = Arrays.asList(
                    Helpers.encodeU64Argument(Long.parseLong(gameId)),
                    Helpers.encodeU64Argument(Long.parseLong(gameCode)),
                    Bytes.valueOf(Utils.convertStringListToByteArray(md5s)));

            Transaction transaction = aptos.submitTransaction(accountUtils, func, args, trxArgs);
            logger.info("api playBetGame end: " + accountUtils.accountAddress() + "|" + (System.currentTimeMillis() - st));

            String txnHash = "";
            String md5 = "";
            if (transaction != null) {
                txnHash = transaction.getHash();
                for (Event<Object> event : transaction.getEvents()) {
                    if (!event.getType().equals(ConfigInfo.CONTRACT_GAME + "::bet_game::BetEvent")) {
                        continue;
                    }
                    EventDto.BetPlayEventData eventData = objectMapper.readValue(objectMapper.writeValueAsString(event.getData()), EventDto.BetPlayEventData.class);
                    md5 = eventData.getMd5();
                    break;
                }
            }

            return buildResultResDto(txnHash, null, md5, "");
        } catch (Exception ex) {
            logger.error("api playBetGame ex: " + walletAddress + "|" + (System.currentTimeMillis() - st), ex);
        }
        return null;
    }

    public ResultResDto publicGame(String walletKey, String gameId, String gameCode, String md5, List<Integer> result, String key) {
        long st = System.currentTimeMillis();
        try {
            AccountUtils accountUtils = AccountUtils.fromPrivateKey(walletKey);
            String func = ConfigInfo.CONTRACT_GAME + "::bet_game::public_game";
            String res = JSONUtil.serialize(result) + key;
            
            List<Object> args = Arrays.asList(gameId, gameCode, md5, res);

            checkBalance(accountUtils.accountAddress());

            List<Bytes> trxArgs = Arrays.asList(
                    Helpers.encodeU64Argument(Long.parseLong(gameId)),
                    Helpers.encodeU64Argument(Long.parseLong(gameCode)),
                    Helpers.encodeU8VectorArgument(Bytes.valueOf(md5.getBytes(StandardCharsets.UTF_8))),
                    Helpers.encodeU8VectorArgument(Bytes.valueOf(res.getBytes(StandardCharsets.UTF_8))));

            Transaction transaction = aptos.submitTransaction(accountUtils, func, args, trxArgs);
            logger.info("api publicGame end: " + (System.currentTimeMillis() - st));

            if (transaction != null) {
                String txnHash = transaction.getHash();

                return buildResultResDto(txnHash, null, "", "");
            }
        } catch (Exception ex) {
            logger.error("api publicGame ex: " + (System.currentTimeMillis() - st), ex);
        }
        return null;
    }
    
    public ResultResDto getRandom(String walletKey, String gameId, String gameCode, long from, long to, String rewards) {
        long st = System.currentTimeMillis();
        try {
            AccountUtils accountUtils = AccountUtils.fromPrivateKey(walletKey);

            checkBalance(accountUtils.accountAddress());

            String func = ConfigInfo.CONTRACT_XGAMBLE + "::XGamble::getRandom";
            List<Object> args = Arrays.asList(gameId, gameCode, from + "", to + "", rewards);

            List<Bytes> trxArgs = Arrays.asList(
                    Helpers.encodeU64Argument(Long.parseLong(gameId)),
                    Helpers.encodeU64Argument(Long.parseLong(gameCode)),
                    Helpers.encodeU64Argument(from),
                    Helpers.encodeU64Argument(to),
                    Helpers.encodeU8VectorArgument(Bytes.valueOf(rewards.getBytes(StandardCharsets.UTF_8)))
            );
            Transaction transaction = aptos.submitTransaction(accountUtils, func, args, trxArgs);
            logger.info("api getRandom end: " + (System.currentTimeMillis() - st));

            if (transaction == null) {
                return null;
            }

            String txnHash = transaction.getHash();
            long rnd = -1;
            for (Event<Object> event : transaction.getEvents()) {
                if (!event.getType().equals(ConfigInfo.CONTRACT_XGAMBLE + "::XGamble::RandomnessEvent")) {
                    continue;
                }
                EventDto.RandomnessEventData eventData = objectMapper.readValue(objectMapper.writeValueAsString(event.getData()), EventDto.RandomnessEventData.class);
                rnd = Long.parseLong(eventData.getResult());
            }
            if (rnd >= 0) {
                ResultResDto rs = buildResultResDto(txnHash, rnd);
                return rs;
            }
        } catch (Exception ex) {
            logger.error("api getRandom ex: " + (System.currentTimeMillis() - st), ex);
        }
        return null;
    }

    public ResultResDto getRandoms(String walletKey, String gameId, String gameCode, long from, long to, int count, String rewards) {
        long st = System.currentTimeMillis();
        try {
            AccountUtils accountUtils = AccountUtils.fromPrivateKey(walletKey);

            checkBalance(accountUtils.accountAddress());

            String func = ConfigInfo.CONTRACT_XGAMBLE + "::XGamble::getMultiRandom";
            List<Object> args = Arrays.asList(gameId, gameCode, from + "", to + "", count, rewards);

            List<Bytes> trxArgs = Arrays.asList(
                    Helpers.encodeU64Argument(Long.parseLong(gameId)),
                    Helpers.encodeU64Argument(Long.parseLong(gameCode)),
                    Helpers.encodeU64Argument(from),
                    Helpers.encodeU64Argument(to),
                    Helpers.encodeU8Argument((byte) count),
                    Helpers.encodeU8VectorArgument(Bytes.valueOf(rewards.getBytes(StandardCharsets.UTF_8)))
            );
            Transaction transaction = aptos.submitTransaction(accountUtils, func, args, trxArgs);
            logger.info("api getRandoms end: " + (System.currentTimeMillis() - st));

            if (transaction == null) {
                return null;
            }

            String txnHash = transaction.getHash();
            List<Long> result = null;
            for (Event<Object> event : transaction.getEvents()) {
                if (!event.getType().equals(ConfigInfo.CONTRACT_XGAMBLE + "::XGamble::MultiRandomnessEvent")) {
                    continue;
                }
                EventDto.MultiRandomnessEventData eventData = objectMapper.readValue(objectMapper.writeValueAsString(event.getData()), EventDto.MultiRandomnessEventData.class);
                result = (eventData.getResult());
            }
            if (result != null && result.size() == count) {
                return buildResultResDto(txnHash, result);
            }
        } catch (Exception ex) {
            logger.error("api getRandoms ex: " + (System.currentTimeMillis() - st), ex);
        }
        return null;
    }
}
