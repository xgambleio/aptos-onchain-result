package com.xg.w3.aptos.types;

import com.xg.w3.aptos.novi.bcs.BcsDeserializer;
import com.xg.w3.aptos.novi.bcs.BcsSerializer;
import com.xg.w3.aptos.novi.serde.*;

public final class Script {
    public final Bytes code;
    public final java.util.List<TypeTag> ty_args;
    public final java.util.List<Bytes> args;

    public Script(Bytes code, java.util.List<TypeTag> ty_args, java.util.List<Bytes> args) {
        java.util.Objects.requireNonNull(code, "code must not be null");
        java.util.Objects.requireNonNull(ty_args, "ty_args must not be null");
        java.util.Objects.requireNonNull(args, "args must not be null");
        this.code = code;
        this.ty_args = ty_args;
        this.args = args;
    }

    public static Script deserialize(Deserializer deserializer) throws DeserializationError {
        deserializer.increase_container_depth();
        Builder builder = new Builder();
        builder.code = deserializer.deserialize_bytes();
        builder.ty_args = TraitHelpers.deserialize_vector_TypeTag(deserializer);
        builder.args = TraitHelpers.deserialize_vector_bytes(deserializer);
        deserializer.decrease_container_depth();
        return builder.build();
    }

    public static Script bcsDeserialize(byte[] input) throws DeserializationError {
        if (input == null) {
            throw new DeserializationError("Cannot deserialize null array");
        }
        Deserializer deserializer = new BcsDeserializer(input);
        Script value = deserialize(deserializer);
        if (deserializer.get_buffer_offset() < input.length) {
            throw new DeserializationError("Some input bytes were not read");
        }
        return value;
    }

    public void serialize(Serializer serializer) throws SerializationError {
        serializer.increase_container_depth();
        serializer.serialize_bytes(code);
        TraitHelpers.serialize_vector_TypeTag(ty_args, serializer);
        TraitHelpers.serialize_vector_bytes(args, serializer);
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
        Script other = (Script) obj;
        if (!java.util.Objects.equals(this.code, other.code)) {
            return false;
        }
        if (!java.util.Objects.equals(this.ty_args, other.ty_args)) {
            return false;
        }
        return java.util.Objects.equals(this.args, other.args);
    }

    public int hashCode() {
        int value = 7;
        value = 31 * value + this.code.hashCode();
        value = 31 * value + this.ty_args.hashCode();
        value = 31 * value + this.args.hashCode();
        return value;
    }

    public static final class Builder {
        public Bytes code;
        public java.util.List<TypeTag> ty_args;
        public java.util.List<Bytes> args;

        public Script build() {
            return new Script(
                    code,
                    ty_args,
                    args
            );
        }
    }
}
