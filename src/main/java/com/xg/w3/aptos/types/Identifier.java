package com.xg.w3.aptos.types;

import com.xg.w3.aptos.novi.bcs.BcsDeserializer;
import com.xg.w3.aptos.novi.bcs.BcsSerializer;
import com.xg.w3.aptos.novi.serde.*;

public final class Identifier {
    public final String value;

    public Identifier(String value) {
        java.util.Objects.requireNonNull(value, "value must not be null");
        this.value = value;
    }

    public static Identifier deserialize(Deserializer deserializer) throws DeserializationError {
        deserializer.increase_container_depth();
        Builder builder = new Builder();
        builder.value = deserializer.deserialize_str();
        deserializer.decrease_container_depth();
        return builder.build();
    }

    public static Identifier bcsDeserialize(byte[] input) throws DeserializationError {
        if (input == null) {
            throw new DeserializationError("Cannot deserialize null array");
        }
        Deserializer deserializer = new BcsDeserializer(input);
        Identifier value = deserialize(deserializer);
        if (deserializer.get_buffer_offset() < input.length) {
            throw new DeserializationError("Some input bytes were not read");
        }
        return value;
    }

    public void serialize(Serializer serializer) throws SerializationError {
        serializer.increase_container_depth();
        serializer.serialize_str(value);
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
        Identifier other = (Identifier) obj;
        return java.util.Objects.equals(this.value, other.value);
    }

    public int hashCode() {
        int value = 7;
        value = 31 * value + this.value.hashCode();
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    public static final class Builder {
        public String value;

        public Identifier build() {
            return new Identifier(
                    value
            );
        }
    }
}
