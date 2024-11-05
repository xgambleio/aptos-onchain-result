package com.xg.w3.aptos.types;

import com.xg.w3.aptos.novi.bcs.BcsDeserializer;
import com.xg.w3.aptos.novi.bcs.BcsSerializer;
import com.xg.w3.aptos.novi.serde.*;

public final class SignedUserTransaction {
    public final RawTransaction raw_txn;
    public final TransactionAuthenticator authenticator;

    public SignedUserTransaction(RawTransaction raw_txn, TransactionAuthenticator authenticator) {
        java.util.Objects.requireNonNull(raw_txn, "raw_txn must not be null");
        java.util.Objects.requireNonNull(authenticator, "authenticator must not be null");
        this.raw_txn = raw_txn;
        this.authenticator = authenticator;
    }

    public static SignedUserTransaction deserialize(Deserializer deserializer) throws DeserializationError {
        deserializer.increase_container_depth();
        Builder builder = new Builder();
        builder.raw_txn = RawTransaction.deserialize(deserializer);
        builder.authenticator = TransactionAuthenticator.deserialize(deserializer);
        deserializer.decrease_container_depth();
        return builder.build();
    }

    public static SignedUserTransaction bcsDeserialize(byte[] input) throws DeserializationError {
        if (input == null) {
            throw new DeserializationError("Cannot deserialize null array");
        }
        Deserializer deserializer = new BcsDeserializer(input);
        SignedUserTransaction value = deserialize(deserializer);
        if (deserializer.get_buffer_offset() < input.length) {
            throw new DeserializationError("Some input bytes were not read");
        }
        return value;
    }

    public void serialize(Serializer serializer) throws SerializationError {
        serializer.increase_container_depth();
        raw_txn.serialize(serializer);
        authenticator.serialize(serializer);
        serializer.decrease_container_depth();
    }

    public byte[] bcsSerialize() throws SerializationError {
        Serializer serializer = new BcsSerializer();
        serialize(serializer);
        return serializer.get_bytes();
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        SignedUserTransaction other = (SignedUserTransaction) obj;
        if (!java.util.Objects.equals(this.raw_txn, other.raw_txn)) {
            return false;
        }
        return java.util.Objects.equals(this.authenticator, other.authenticator);
    }

    public int hashCode() {
        int value = 7;
        value = 31 * value + this.raw_txn.hashCode();
        value = 31 * value + this.authenticator.hashCode();
        return value;
    }

    public static final class Builder {
        public RawTransaction raw_txn;
        public TransactionAuthenticator authenticator;

        public SignedUserTransaction build() {
            return new SignedUserTransaction(
                    raw_txn,
                    authenticator
            );
        }
    }
}
