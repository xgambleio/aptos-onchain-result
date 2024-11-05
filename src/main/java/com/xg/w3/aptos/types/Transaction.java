package com.xg.w3.aptos.types;

import com.xg.w3.aptos.novi.bcs.BcsDeserializer;
import com.xg.w3.aptos.novi.bcs.BcsSerializer;
import com.xg.w3.aptos.novi.serde.*;

public abstract class Transaction {

    public static Transaction deserialize(Deserializer deserializer) throws DeserializationError {
        int index = deserializer.deserialize_variant_index();
        switch (index) {
            case 0:
                return UserTransaction.load(deserializer);
//            case 1:
//                return GenesisTransaction.load(deserializer);
//            case 2:
//                return BlockMetadata.load(deserializer);
            default:
                throw new DeserializationError("Unknown variant index for Transaction: " + index);
        }
    }

    public static Transaction bcsDeserialize(byte[] input) throws DeserializationError {
        if (input == null) {
            throw new DeserializationError("Cannot deserialize null array");
        }
        Deserializer deserializer = new BcsDeserializer(input);
        Transaction value = deserialize(deserializer);
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

    public static final class UserTransaction extends Transaction {
        public final SignedUserTransaction value;

        public UserTransaction(SignedUserTransaction value) {
            java.util.Objects.requireNonNull(value, "value must not be null");
            this.value = value;
        }

        static UserTransaction load(Deserializer deserializer) throws DeserializationError {
            deserializer.increase_container_depth();
            Builder builder = new Builder();
            builder.value = SignedUserTransaction.deserialize(deserializer);
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
            UserTransaction other = (UserTransaction) obj;
            return java.util.Objects.equals(this.value, other.value);
        }

        public int hashCode() {
            int value = 7;
            value = 31 * value + this.value.hashCode();
            return value;
        }

        public static final class Builder {
            public SignedUserTransaction value;

            public UserTransaction build() {
                return new UserTransaction(
                        value
                );
            }
        }
    }

//    public static final class BlockMetadata extends Transaction {
//        public final dev.aptos.types.BlockMetadata value;
//
//        public BlockMetadata(dev.aptos.types.BlockMetadata value) {
//            java.util.Objects.requireNonNull(value, "value must not be null");
//            this.value = value;
//        }
//
//        static BlockMetadata load(Deserializer deserializer) throws DeserializationError {
//            deserializer.increase_container_depth();
//            Builder builder = new Builder();
//            builder.value = dev.aptos.types.BlockMetadata.deserialize(deserializer);
//            deserializer.decrease_container_depth();
//            return builder.build();
//        }
//
//        public void serialize(Serializer serializer) throws SerializationError {
//            serializer.increase_container_depth();
//            serializer.serialize_variant_index(2);
//            value.serialize(serializer);
//            serializer.decrease_container_depth();
//        }
//
//        public boolean equals(Object obj) {
//            if (this == obj) return true;
//            if (obj == null) return false;
//            if (getClass() != obj.getClass()) return false;
//            BlockMetadata other = (BlockMetadata) obj;
//            return java.util.Objects.equals(this.value, other.value);
//        }
//
//        public int hashCode() {
//            int value = 7;
//            value = 31 * value + (this.value != null ? this.value.hashCode() : 0);
//            return value;
//        }
//
//        public static final class Builder {
//            public dev.aptos.types.BlockMetadata value;
//
//            public BlockMetadata build() {
//                return new BlockMetadata(
//                        value
//                );
//            }
//        }
//    }

}

