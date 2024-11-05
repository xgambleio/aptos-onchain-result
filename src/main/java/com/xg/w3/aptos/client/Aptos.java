package com.xg.w3.aptos.client;

import com.xg.w3.aptos.bean.EncodeSubmissionRequest;
import com.xg.w3.aptos.bean.Transaction;
import com.xg.w3.aptos.bean.TransactionPayload;
import com.xg.w3.aptos.novi.serde.Bytes;
import com.xg.w3.aptos.novi.serde.SerializationError;
import com.xg.w3.aptos.types.AccountAddress;
import com.xg.w3.aptos.types.ChainId;
import com.xg.w3.aptos.types.RawTransaction;
import com.xg.w3.aptos.types.SignedUserTransaction;
import com.xg.w3.aptos.types.TypeTag;
import com.xg.w3.aptos.utils.AccountUtils;
import com.xg.w3.aptos.utils.Helpers;
import com.xg.w3.aptos.utils.HexUtils;
import com.xg.w3.aptos.utils.NodeApiClient;
import com.xg.w3.aptos.utils.NodeApiUtils;
import com.xg.w3.aptos.utils.SignatureUtils;
import com.xg.w3.aptos.utils.StructTagUtils;
import com.xg.w3.aptos.utils.TransactionUtils;
import com.xg.w3.util.LogUtil;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import org.apache.log4j.Logger;

public class Aptos {

    static final Logger logger = LogUtil.getLogger(Aptos.class);

    private final String baseUrl;
    private final ChainId chainId;

    private long maxGasAmount = 2000;;
    private long gasUnitPrice = 100L;
    private long expiration_ttl = 600L;

    public Aptos(String currentNetwork, String baseUrl, Long expiration_ttl, Long maxGasAmount, Long gasUnitPrice) {
        this.baseUrl = baseUrl;
        if (Objects.equals(currentNetwork, "TESTNET")) {
            this.chainId = new ChainId((byte) 2);
        } else if (Objects.equals(currentNetwork, "MAINNET")) {
            this.chainId = new ChainId((byte) 1);
        } else {
            throw new IllegalArgumentException("Invalid network: " + currentNetwork);
        }
        if (maxGasAmount != null) {
            this.maxGasAmount = maxGasAmount;
        }
        if (gasUnitPrice != null) {
            this.gasUnitPrice = gasUnitPrice;
        }
        if (expiration_ttl != null) {
            this.expiration_ttl = expiration_ttl;
        }
    }

    //get account balance
    public BigInteger getAccountBalance(String address) throws IOException {
        return new NodeApiClient(this.baseUrl).getAccountBalance(address);
    }

    public String transfer(AccountUtils accountUtils, String receiver, BigInteger amount) {
        String coins = "0x1::aptos_coin::AptosCoin";
        String func = "0x1::aptos_account::transfer_coins";
        List<Object> args = Arrays.asList(receiver, amount.toString());
        List<String> typeArgument = Collections.singletonList(coins);
        List<TypeTag> trxTypeArgument = Collections.singletonList(StructTagUtils.toTypeTag(coins));
        List<Bytes> trxArgs = Arrays.asList(
                Helpers.encodeAccountAddressArgument(AccountAddress.valueOf(HexUtils.hexToByteArray(receiver))),
                Helpers.encodeU64Argument(amount.longValue()));
        Transaction trans = submitTransaction(accountUtils, func, args, trxArgs, typeArgument, trxTypeArgument);
        if(trans == null || !trans.getSuccess()) {
            return "";
        }
        return trans.getHash();
        
    }

    Random r = new Random(System.currentTimeMillis());
    public Transaction submitTransaction(AccountUtils sender, String function, List<Object> arguments, List<Bytes> argumentsBytes) {
        try {
            long start = System.currentTimeMillis();
            TransactionPayload transactionPayload = new TransactionPayload();
            transactionPayload.setType(TransactionPayload.TYPE_ENTRY_FUNCTION_PAYLOAD);
            transactionPayload.setFunction(function);
            transactionPayload.setArguments(arguments);

            long expirationTimestampSecs = System.currentTimeMillis() / 1000L + this.expiration_ttl;
            EncodeSubmissionRequest encodeSubmissionRequest = NodeApiUtils.newEncodeSubmissionRequest(this.baseUrl,
                    sender.accountAddress(),
                    expirationTimestampSecs,
                    transactionPayload,
                    this.maxGasAmount,
                    null, String.valueOf(this.gasUnitPrice));

            String toSign = NodeApiUtils.encodeSubmission(baseUrl, encodeSubmissionRequest);
            FunctionObj functionObj = new FunctionObj(function);

            List<TypeTag> txnTypeArgs = Collections.emptyList();

            RawTransaction rawTransaction = TransactionUtils.newRawTransaction(
                    chainId,
                    sender.accountAddress(),
                    encodeSubmissionRequest.getSequenceNumber(),
                    encodeSubmissionRequest.getMaxGasAmount(),
                    encodeSubmissionRequest.getGasUnitPrice(),
                    encodeSubmissionRequest.getExpirationTimestampSecs(),
                    functionObj.moduleAddress, functionObj.moduleName, functionObj.functionName,
                    txnTypeArgs, argumentsBytes);
            byte[] signature = SignatureUtils.ed25519Sign(HexUtils.hexToByteArray(sender.privateKey()), HexUtils.hexToByteArray(toSign));
            SignedUserTransaction signedTransaction = TransactionUtils.newSignedUserTransaction(rawTransaction, HexUtils.hexToByteArray(sender.authKey()), signature);
            Transaction submitTransactionResult = NodeApiUtils.submitBcsTransaction(baseUrl, signedTransaction);

            Transaction trans = NodeApiUtils.waitForTransaction(baseUrl, submitTransactionResult.getHash());
            long end = System.currentTimeMillis();
            logger.info("submitTransaction time: " + sender.accountAddress() + " " + (end - start));
            return trans;
//            return submitTransactionResult.getHash();
        } catch (SerializationError | IOException e) {
            logger.error(e.getMessage());
            //throw new RuntimeException(e);
        }
        return null;
    }

    public Transaction submitTransaction(
            AccountUtils sender,
            String function,
            List<Object> arguments,
            List<Bytes> argumentsBytes,
            List<String> typeArguments,
            List<TypeTag> typeArgumentsTypeTag
    ) {
        try {
            long start = System.currentTimeMillis();
            TransactionPayload transactionPayload = new TransactionPayload();
            transactionPayload.setType(TransactionPayload.TYPE_ENTRY_FUNCTION_PAYLOAD);
            transactionPayload.setFunction(function);
            transactionPayload.setArguments(arguments);
            transactionPayload.setTypeArguments(typeArguments);

            long expirationTimestampSecs = System.currentTimeMillis() / 1000L + this.expiration_ttl;
            EncodeSubmissionRequest encodeSubmissionRequest = NodeApiUtils.newEncodeSubmissionRequest(this.baseUrl,
                    sender.accountAddress(),
                    expirationTimestampSecs,
                    transactionPayload,
                    this.maxGasAmount,
                    null, String.valueOf(this.gasUnitPrice));

            String toSign = NodeApiUtils.encodeSubmission(baseUrl, encodeSubmissionRequest);
            FunctionObj functionObj = new FunctionObj(function);

            RawTransaction rawTransaction = TransactionUtils.newRawTransaction(
                    chainId,
                    sender.accountAddress(),
                    encodeSubmissionRequest.getSequenceNumber(),
                    encodeSubmissionRequest.getMaxGasAmount(),
                    encodeSubmissionRequest.getGasUnitPrice(),
                    encodeSubmissionRequest.getExpirationTimestampSecs(),
                    functionObj.moduleAddress, functionObj.moduleName, functionObj.functionName,
                    typeArgumentsTypeTag, argumentsBytes);
            byte[] signature = SignatureUtils.ed25519Sign(HexUtils.hexToByteArray(sender.privateKey()), HexUtils.hexToByteArray(toSign));
            SignedUserTransaction signedTransaction = TransactionUtils.newSignedUserTransaction(rawTransaction, HexUtils.hexToByteArray(sender.authKey()), signature);
            Transaction submitTransactionResult = NodeApiUtils.submitBcsTransaction(baseUrl, signedTransaction);

            long end = System.currentTimeMillis();
            Transaction trans = NodeApiUtils.waitForTransaction(baseUrl, submitTransactionResult.getHash());
            logger.info("submitTransaction 1 time: " + (end - start));
            return trans;
//            return submitTransactionResult.getHash();
        } catch (SerializationError | IOException e) {
            //throw new RuntimeException(e);
            logger.error("submitTransaction ex", e);
        }
        return null;
    }

//    public String transferCoins(AccountUtils accountUtils, String receiver, BigInteger amount, String coins) {
//        String func = "0x1::aptos_account::transfer_coins";
//        List<Object> args = Arrays.asList(receiver, amount.toString());
//        List<String> typeArgument = Collections.singletonList(coins);
//        List<TypeTag> trxTypeArgument = Collections.singletonList(StructTagUtils.toTypeTag(coins));
//        List<Bytes> trxArgs = Arrays.asList(
//                encodeAccountAddressArgument(AccountAddress.valueOf(HexUtils.hexToByteArray(receiver))),
//                encodeU64Argument(amount.longValue()));
//        return submitTransaction(accountUtils, func, args, trxArgs, typeArgument, trxTypeArgument).getHash();
//    }
//
//    public Integer getDecimal(String coins) throws IOException {
//        String address = coins.split("::")[0];
//        String resourceType = "0x1::coin::CoinInfo<" + coins + ">";
//        return new NodeApiClient(this.baseUrl).getAccountResource(address, resourceType, CoinInfo.class, null).getData().getDecimals();
//    }
//
    public static class FunctionObj {

        private final String moduleAddress;
        private final String moduleName;
        private final String functionName;

        public FunctionObj(String function) {
            String[] functionArr = function.split("::");
            String moduleAddress = functionArr[0];
            String moduleName = functionArr[1];
            String functionName = functionArr[2];
            if (Objects.equals(moduleAddress, "0x1")) {
                moduleAddress = "0x0000000000000000000000000000000000000000000000000000000000000001";
            }
            this.moduleAddress = moduleAddress;
            this.moduleName = moduleName;
            this.functionName = functionName;
        }
    }
//
//    //get transaction status
//    public Transaction getTransactionByHash(String hash) throws IOException {
//        return NodeApiUtils.getTransactionByHash(this.baseUrl, hash);
//    }
//
//    //get transaction result
//    public static void getTransactionResult() {
//        //todo
//    }
//
//    // wait for transaction
//    public static void waitForTransaction() {
//        //todo
//    }

}