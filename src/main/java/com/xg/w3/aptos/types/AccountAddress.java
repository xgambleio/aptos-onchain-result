package com.xg.w3.aptos.types;


import com.xg.w3.aptos.novi.bcs.BcsSerializer;
import com.xg.w3.aptos.novi.serde.DeserializationError;
import com.xg.w3.aptos.novi.serde.Deserializer;
import com.xg.w3.aptos.novi.serde.SerializationError;
import com.xg.w3.aptos.novi.serde.Serializer;
import com.xg.w3.aptos.utils.HexUtils;

public final class AccountAddress {
    public static final int LENGTH = 32;
    public final java.util.@com.xg.w3.aptos.novi.serde.ArrayLen(length = 32) List<@com.xg.w3.aptos.novi.serde.Unsigned Byte> value;

    public AccountAddress(java.util.@com.xg.w3.aptos.novi.serde.ArrayLen(length = 32) List<@com.xg.w3.aptos.novi.serde.Unsigned Byte> value) {
        java.util.Objects.requireNonNull(value, "value must not be null");
        this.value = value;
    }

    public static AccountAddress deserialize(Deserializer deserializer) throws DeserializationError {
        deserializer.increase_container_depth();
        Builder builder = new Builder();
        builder.value = TraitHelpers.deserialize_array32_u8_array(deserializer);
        deserializer.decrease_container_depth();
        return builder.build();
    }

    public static AccountAddress bcsDeserialize(byte[] input) throws DeserializationError {
        if (input == null) {
            throw new DeserializationError("Cannot deserialize null array");
        }
        com.xg.w3.aptos.novi.serde.Deserializer deserializer = new com.xg.w3.aptos.novi.bcs.BcsDeserializer(input);
        AccountAddress value = deserialize(deserializer);
        if (deserializer.get_buffer_offset() < input.length) {
            throw new DeserializationError("Some input bytes were not read");
        }
        return value;
    }

    public static AccountAddress valueOf(byte[] values) {
        if (values.length != LENGTH) {
            throw new IllegalArgumentException("Invalid length for AccountAddress");
        }
        java.util.List<Byte> address = new java.util.ArrayList<>(LENGTH);
        for (int i = 0; i < LENGTH; i++) {
            address.add(values[i]);
        }
        return new AccountAddress(address);
    }

    public void serialize(Serializer serializer) throws SerializationError {
        serializer.increase_container_depth();
        TraitHelpers.serialize_array32_u8_array(value, serializer);
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
        AccountAddress other = (AccountAddress) obj;
        return java.util.Objects.equals(this.value, other.value);
    }

    public int hashCode() {
        int value = 7;
        value = 31 * value + this.value.hashCode();
        return value;
    }

    public byte[] toBytes() {
        byte[] bytes = new byte[LENGTH];
        int i = 0;
        for (Byte item : value) {
            bytes[i++] = item;
        }
        return bytes;
    }

    @Override
    public String toString() {
        return HexUtils.byteListToHexWithPrefix(value);
    }

    public static final class Builder {
        public java.util.@com.xg.w3.aptos.novi.serde.ArrayLen(length = 32) List<@com.xg.w3.aptos.novi.serde.Unsigned Byte> value;

        public AccountAddress build() {
            return new AccountAddress(
                    value
            );
        }
    }
}
