package com.xg.w3.aptos.utils;

import org.bouncycastle.crypto.digests.SHA3Digest;

public class AuthenticationKey {
    public static final byte ED25519_SCHEME = 0; // Assuming a byte representation for the scheme

    private final byte[] digest;

    public AuthenticationKey(byte[] digest) {
        this.digest = digest;
    }

    public String derivedAddress() {
        return HexUtils.byteArrayToHexWithPrefix(digest);
    }

    // ... other AuthenticationKey constructors and methods ...

    public static AuthenticationKey fromEd25519PublicKey(byte[] publicKeyBytes) {
        byte[] bytes = new byte[publicKeyBytes.length + 1];
        System.arraycopy(publicKeyBytes, 0, bytes, 0, publicKeyBytes.length);
        bytes[publicKeyBytes.length] = ED25519_SCHEME;

        SHA3Digest sha3 = new SHA3Digest(256); // SHA3-256
        sha3.update(bytes, 0, bytes.length);

        byte[] digest = new byte[sha3.getDigestSize()];
        sha3.doFinal(digest, 0);

        return new AuthenticationKey(digest);
    }
}
