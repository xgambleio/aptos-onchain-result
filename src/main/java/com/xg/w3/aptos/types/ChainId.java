package com.xg.w3.aptos.types;

import com.xg.w3.aptos.novi.bcs.BcsDeserializer;
import com.xg.w3.aptos.novi.bcs.BcsSerializer;
import com.xg.w3.aptos.novi.serde.DeserializationError;
import com.xg.w3.aptos.novi.serde.Deserializer;
import com.xg.w3.aptos.novi.serde.SerializationError;
import com.xg.w3.aptos.novi.serde.Serializer;
import com.xg.w3.aptos.novi.serde.Unsigned;

public final class ChainId {
    public final @Unsigned Byte id;

    public ChainId(@Unsigned Byte id) {
        java.util.Objects.requireNonNull(id, "id must not be null");
        this.id = id;
    }

    public static ChainId deserialize(Deserializer deserializer) throws DeserializationError {
        deserializer.increase_container_depth();
        Builder builder = new Builder();
        builder.id = deserializer.deserialize_u8();
        deserializer.decrease_container_depth();
        return builder.build();
    }

    public static ChainId bcsDeserialize(byte[] input) throws DeserializationError {
        if (input == null) {
            throw new DeserializationError("Cannot deserialize null array");
        }
        Deserializer deserializer = new BcsDeserializer(input);
        ChainId value = deserialize(deserializer);
        if (deserializer.get_buffer_offset() < input.length) {
            throw new DeserializationError("Some input bytes were not read");
        }
        return value;
    }

    public void serialize(Serializer serializer) throws SerializationError {
        serializer.increase_container_depth();
        serializer.serialize_u8(id);
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
        ChainId other = (ChainId) obj;
        return java.util.Objects.equals(this.id, other.id);
    }

    public int hashCode() {
        int value = 7;
        value = 31 * value + this.id.hashCode();
        return value;
    }

    public static final class Builder {
        public @Unsigned Byte id;

        public ChainId build() {
            return new ChainId(
                    id
            );
        }
    }
}