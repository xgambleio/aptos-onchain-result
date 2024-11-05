package com.xg.w3.aptos.types;
import com.xg.w3.aptos.novi.bcs.BcsDeserializer;
import com.xg.w3.aptos.novi.bcs.BcsSerializer;
import com.xg.w3.aptos.novi.serde.*;

public final class AuthenticationKey {
    public static final int LENGTH = 32;
    public static final AuthenticationKey DUMMY_KEY = new AuthenticationKey(Bytes.valueOf(new byte[LENGTH]));

    public final Bytes value;

    public AuthenticationKey(com.xg.w3.aptos.novi.serde.Bytes value) {
        java.util.Objects.requireNonNull(value, "value must not be null");
        this.value = value;
    }

    public static AuthenticationKey deserialize(Deserializer deserializer) throws DeserializationError {
        deserializer.increase_container_depth();
        Builder builder = new Builder();
        builder.value = deserializer.deserialize_bytes();
        deserializer.decrease_container_depth();
        return builder.build();
    }

    public static AuthenticationKey bcsDeserialize(byte[] input) throws DeserializationError {
        if (input == null) {
            throw new DeserializationError("Cannot deserialize null array");
        }
        Deserializer deserializer = new BcsDeserializer(input);
        AuthenticationKey value = deserialize(deserializer);
        if (deserializer.get_buffer_offset() < input.length) {
            throw new DeserializationError("Some input bytes were not read");
        }
        return value;
    }

    public void serialize(Serializer serializer) throws SerializationError {
        serializer.increase_container_depth();
        serializer.serialize_bytes(value);
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
        AuthenticationKey other = (AuthenticationKey) obj;
        return java.util.Objects.equals(this.value, other.value);
    }

    public int hashCode() {
        int value = 7;
        value = 31 * value + this.value.hashCode();
        return value;
    }

    public static final class Builder {
        public Bytes value;

        public AuthenticationKey build() {
            return new AuthenticationKey(
                    value
            );
        }
    }
}
