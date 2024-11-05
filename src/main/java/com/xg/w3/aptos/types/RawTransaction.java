package com.xg.w3.aptos.types;

import com.xg.w3.aptos.novi.bcs.BcsDeserializer;
import com.xg.w3.aptos.novi.bcs.BcsSerializer;
import com.xg.w3.aptos.novi.serde.*;

/**
 * RawTransaction is the portion of a transaction that a client signs.
 */
public final class RawTransaction {
    public final AccountAddress sender;
    public final @Unsigned Long sequence_number;
    public final TransactionPayload payload;
    public final @Unsigned Long max_gas_amount;
    public final @Unsigned Long gas_unit_price;
    public final @Unsigned Long expiration_timestamp_secs;
    public final ChainId chain_id;

    public RawTransaction(AccountAddress sender, @Unsigned Long sequence_number,
                          TransactionPayload payload,
                          @Unsigned Long max_gas_amount,
                          @Unsigned Long gas_unit_price,
                          //String gas_token_code,
                          @Unsigned Long expiration_timestamp_secs,
                          ChainId chain_id) {
        java.util.Objects.requireNonNull(sender, "sender must not be null");
        java.util.Objects.requireNonNull(sequence_number, "sequence_number must not be null");
        java.util.Objects.requireNonNull(payload, "payload must not be null");
        java.util.Objects.requireNonNull(max_gas_amount, "max_gas_amount must not be null");
        java.util.Objects.requireNonNull(gas_unit_price, "gas_unit_price must not be null");
        //java.util.Objects.requireNonNull(gas_token_code, "gas_token_code must not be null");
        java.util.Objects.requireNonNull(expiration_timestamp_secs, "expiration_timestamp_secs must not be null");
        java.util.Objects.requireNonNull(chain_id, "chain_id must not be null");
        this.sender = sender;
        this.sequence_number = sequence_number;
        this.payload = payload;
        this.max_gas_amount = max_gas_amount;
        this.gas_unit_price = gas_unit_price;
        //this.gas_token_code = gas_token_code;
        this.expiration_timestamp_secs = expiration_timestamp_secs;
        this.chain_id = chain_id;
    }

    public static RawTransaction deserialize(Deserializer deserializer) throws DeserializationError {
        deserializer.increase_container_depth();
        Builder builder = new Builder();
        builder.sender = AccountAddress.deserialize(deserializer);
        builder.sequence_number = deserializer.deserialize_u64();
        builder.payload = TransactionPayload.deserialize(deserializer);
        builder.max_gas_amount = deserializer.deserialize_u64();
        builder.gas_unit_price = deserializer.deserialize_u64();
        //builder.gas_token_code = deserializer.deserialize_str();
        builder.expiration_timestamp_secs = deserializer.deserialize_u64();
        builder.chain_id = ChainId.deserialize(deserializer);
        deserializer.decrease_container_depth();
        return builder.build();
    }

    public static RawTransaction bcsDeserialize(byte[] input) throws DeserializationError {
        if (input == null) {
            throw new DeserializationError("Cannot deserialize null array");
        }
        Deserializer deserializer = new BcsDeserializer(input);
        RawTransaction value = deserialize(deserializer);
        if (deserializer.get_buffer_offset() < input.length) {
            throw new DeserializationError("Some input bytes were not read");
        }
        return value;
    }

    public void serialize(Serializer serializer) throws SerializationError {
        serializer.increase_container_depth();
        sender.serialize(serializer);
        serializer.serialize_u64(sequence_number);
        payload.serialize(serializer);
        serializer.serialize_u64(max_gas_amount);
        serializer.serialize_u64(gas_unit_price);
        //serializer.serialize_str(gas_token_code);
        serializer.serialize_u64(expiration_timestamp_secs);
        chain_id.serialize(serializer);
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
        RawTransaction other = (RawTransaction) obj;
        if (!java.util.Objects.equals(this.sender, other.sender)) {
            return false;
        }
        if (!java.util.Objects.equals(this.sequence_number, other.sequence_number)) {
            return false;
        }
        if (!java.util.Objects.equals(this.payload, other.payload)) {
            return false;
        }
        if (!java.util.Objects.equals(this.max_gas_amount, other.max_gas_amount)) {
            return false;
        }
        if (!java.util.Objects.equals(this.gas_unit_price, other.gas_unit_price)) {
            return false;
        }
//        if (!java.util.Objects.equals(this.gas_token_code, other.gas_token_code)) {
//            return false;
//        }
        if (!java.util.Objects.equals(this.expiration_timestamp_secs, other.expiration_timestamp_secs)) {
            return false;
        }
        return java.util.Objects.equals(this.chain_id, other.chain_id);
    }

    public int hashCode() {
        int value = 7;
        value = 31 * value + this.sender.hashCode();
        value = 31 * value + this.sequence_number.hashCode();
        value = 31 * value + this.payload.hashCode();
        value = 31 * value + this.max_gas_amount.hashCode();
        value = 31 * value + this.gas_unit_price.hashCode();
        //value = 31 * value + (this.gas_token_code != null ? this.gas_token_code.hashCode() : 0);
        value = 31 * value + this.expiration_timestamp_secs.hashCode();
        value = 31 * value + this.chain_id.hashCode();
        return value;
    }

    public static final class Builder {
        public AccountAddress sender;
        public @Unsigned Long sequence_number;
        public TransactionPayload payload;
        public @Unsigned Long max_gas_amount;
        public @Unsigned Long gas_unit_price;
        //public String gas_token_code;
        public @Unsigned Long expiration_timestamp_secs;
        public ChainId chain_id;

        public RawTransaction build() {
            return new RawTransaction(
                    sender,
                    sequence_number,
                    payload,
                    max_gas_amount,
                    gas_unit_price,
                    //gas_token_code,
                    expiration_timestamp_secs,
                    chain_id
            );
        }
    }
}
