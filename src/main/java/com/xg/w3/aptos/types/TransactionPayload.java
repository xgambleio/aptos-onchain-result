package com.xg.w3.aptos.types;

import com.xg.w3.aptos.novi.bcs.BcsDeserializer;
import com.xg.w3.aptos.novi.bcs.BcsSerializer;
import com.xg.w3.aptos.novi.serde.*;

public abstract class TransactionPayload {
    //    SCRIPT: int = 0
    //    MODULE_BUNDLE: int = 1
    //    SCRIPT_FUNCTION(ENTRY_FUNCTION): int = 2

    public static TransactionPayload deserialize(Deserializer deserializer) throws DeserializationError {
        int index = deserializer.deserialize_variant_index();
        switch (index) {
            case 0:
                return Script.load(deserializer);
            case 1:
                return ModuleBundle.load(deserializer);
            case 2:
                return EntryFunction.load(deserializer);
            default:
                throw new DeserializationError("Unknown variant index for TransactionPayload: " + index);
        }
    }

    public static TransactionPayload bcsDeserialize(byte[] input) throws DeserializationError {
        if (input == null) {
            throw new DeserializationError("Cannot deserialize null array");
        }
        Deserializer deserializer = new BcsDeserializer(input);
        TransactionPayload value = deserialize(deserializer);
        if (deserializer.get_buffer_offset() < input.length) {
            throw new DeserializationError("Some input bytes were not read");
        }
        return value;
    }

    abstract public void serialize(Serializer serializer) throws SerializationError;

    public byte[] bcsSerialize() throws SerializationError {
        Serializer serializer = new BcsSerializer();
        serialize(serializer);
        return serializer.get_bytes();
    }

    public static final class Script extends TransactionPayload {
        public final com.xg.w3.aptos.types.Script value;

        public Script(com.xg.w3.aptos.types.Script value) {
            java.util.Objects.requireNonNull(value, "value must not be null");
            this.value = value;
        }

        static Script load(Deserializer deserializer) throws DeserializationError {
            deserializer.increase_container_depth();
            Builder builder = new Builder();
            builder.value = com.xg.w3.aptos.types.Script.deserialize(deserializer);
            deserializer.decrease_container_depth();
            return builder.build();
        }

        public void serialize(Serializer serializer) throws SerializationError {
            serializer.increase_container_depth();
            serializer.serialize_variant_index(0);
            value.serialize(serializer);
            serializer.decrease_container_depth();
        }

        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            if (getClass() != obj.getClass()) return false;
            Script other = (Script) obj;
            return java.util.Objects.equals(this.value, other.value);
        }

        public int hashCode() {
            int value = 7;
            value = 31 * value + this.value.hashCode();
            return value;
        }

        public static final class Builder {
            public com.xg.w3.aptos.types.Script value;

            public Script build() {
                return new Script(
                        value
                );
            }
        }
    }

    public static final class ModuleBundle extends TransactionPayload {
        public final com.xg.w3.aptos.types.ModuleBundle value;

        public ModuleBundle(com.xg.w3.aptos.types.ModuleBundle value) {
            java.util.Objects.requireNonNull(value, "value must not be null");
            this.value = value;
        }

        static ModuleBundle load(Deserializer deserializer) throws DeserializationError {
            deserializer.increase_container_depth();
            Builder builder = new Builder();
            builder.value = com.xg.w3.aptos.types.ModuleBundle.deserialize(deserializer);
            deserializer.decrease_container_depth();
            return builder.build();
        }

        public void serialize(Serializer serializer) throws SerializationError {
            serializer.increase_container_depth();
            serializer.serialize_variant_index(1);
            value.serialize(serializer);
            serializer.decrease_container_depth();
        }

        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            if (getClass() != obj.getClass()) return false;
            ModuleBundle other = (ModuleBundle) obj;
            return java.util.Objects.equals(this.value, other.value);
        }

        public int hashCode() {
            int value = 7;
            value = 31 * value + this.value.hashCode();
            return value;
        }

        public static final class Builder {
            public com.xg.w3.aptos.types.ModuleBundle value;

            public ModuleBundle build() {
                return new ModuleBundle(
                        value
                );
            }
        }
    }

    public static final class EntryFunction extends TransactionPayload {
        public final com.xg.w3.aptos.types.EntryFunction value;

        public EntryFunction(com.xg.w3.aptos.types.EntryFunction value) {
            java.util.Objects.requireNonNull(value, "value must not be null");
            this.value = value;
        }

        static EntryFunction load(Deserializer deserializer) throws DeserializationError {
            deserializer.increase_container_depth();
            Builder builder = new Builder();
            builder.value = com.xg.w3.aptos.types.EntryFunction.deserialize(deserializer);
            deserializer.decrease_container_depth();
            return builder.build();
        }

        public void serialize(Serializer serializer) throws SerializationError {
            serializer.increase_container_depth();
            serializer.serialize_variant_index(2);
            value.serialize(serializer);
            serializer.decrease_container_depth();
        }

        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            if (getClass() != obj.getClass()) return false;
            EntryFunction other = (EntryFunction) obj;
            return java.util.Objects.equals(this.value, other.value);
        }

        public int hashCode() {
            int value = 7;
            value = 31 * value + this.value.hashCode();
            return value;
        }

        public static final class Builder {
            public com.xg.w3.aptos.types.EntryFunction value;

            public EntryFunction build() {
                return new EntryFunction(
                        value
                );
            }
        }
    }
}

