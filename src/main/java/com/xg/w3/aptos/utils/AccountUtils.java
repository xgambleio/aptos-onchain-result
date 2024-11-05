package com.xg.w3.aptos.utils;

import com.xg.w3.aptos.tweetnacl.TweetNacl;
import java.util.Arrays;

public class AccountUtils {

    private final TweetNacl.Signature.KeyPair keyPair;
    private final AuthenticationKey authenticationKey;

    public byte[] publicKey;
    public byte[] privateKey;


    private AccountUtils(byte[] privateKey) {
        this.keyPair = TweetNacl.Signature.keyPair_fromSeed(Arrays.copyOfRange(privateKey, 0, 32));
        authenticationKey = AuthenticationKey.fromEd25519PublicKey(keyPair.getPublicKey());
        this.publicKey = keyPair.getPublicKey();
        this.privateKey = keyPair.getSecretKey();
    }

    public AccountUtils() {
        this.keyPair = TweetNacl.Signature.keyPair();
        authenticationKey = AuthenticationKey.fromEd25519PublicKey(keyPair.getPublicKey());
    }

    public static AccountUtils fromPrivateKey(String privateKey) {
        byte[] privateKeyBytes = HexUtils.hexToByteArray(privateKey);
        return new AccountUtils(privateKeyBytes);
    }

    public String accountAddress() {
        return authenticationKey.derivedAddress();
    }

    public String privateKey() {
        return HexUtils.byteArrayToHexWithPrefix(Arrays.copyOfRange(keyPair.getSecretKey(), 0, 32));
    }

    public String authKey() {
        return HexUtils.byteArrayToHexWithPrefix(keyPair.getPublicKey());
    }
}