package com.xg.w3.aptos.types;

import com.xg.w3.aptos.novi.bcs.BcsDeserializer;
import com.xg.w3.aptos.novi.bcs.BcsSerializer;
import com.xg.w3.aptos.novi.serde.*;

public abstract class TypeTag {

    public static TypeTag deserialize(Deserializer deserializer) throws DeserializationError {
        int index = deserializer.deserialize_variant_index();
        switch (index) {
            case 0:
                return Bool.load(deserializer);
            case 1:
                return U8.load(deserializer);
            case 2:
                return U64.load(deserializer);
            case 3:
                return U128.load(deserializer);
            case 4:
                return Address.load(deserializer);
            case 5:
                return Signer.load(deserializer);
            case 6:
                return Vector.load(deserializer);
            case 7:
                return Struct.load(deserializer);
            default:
                throw new DeserializationError("Unknown variant index for TypeTag: " + index);
        }
    }

    public static TypeTag bcsDeserialize(byte[] input) throws DeserializationError {
        if (input == null) {
            throw new DeserializationError("Cannot deserialize null array");
        }
        Deserializer deserializer = new BcsDeserializer(input);
        TypeTag value = deserialize(deserializer);
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

    public static final class Bool extends TypeTag {
        public Bool() {
        }

        static Bool load(Deserializer deserializer) throws DeserializationError {
            deserializer.increase_container_depth();
            Builder builder = new Builder();
            deserializer.decrease_container_depth();
            return builder.build();
        }

        public void serialize(Serializer serializer) throws SerializationError {
            serializer.increase_container_depth();
            serializer.serialize_variant_index(0);
            serializer.decrease_container_depth();
        }

        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            if (getClass() != obj.getClass()) return false;
            Bool other = (Bool) obj;
            return true;
        }

        public int hashCode() {
            int value = 7;
            return value;
        }

        public static final class Builder {
            public Bool build() {
                return new Bool(
                );
            }
        }
    }

    public static final class U8 extends TypeTag {
        public U8() {
        }

        static U8 load(Deserializer deserializer) throws DeserializationError {
            deserializer.increase_container_depth();
            Builder builder = new Builder();
            deserializer.decrease_container_depth();
            return builder.build();
        }

        public void serialize(Serializer serializer) throws SerializationError {
            serializer.increase_container_depth();
            serializer.serialize_variant_index(1);
            serializer.decrease_container_depth();
        }

        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            if (getClass() != obj.getClass()) return false;
            U8 other = (U8) obj;
            return true;
        }

        public int hashCode() {
            int value = 7;
            return value;
        }

        public static final class Builder {
            public U8 build() {
                return new U8(
                );
            }
        }
    }

    public static final class U64 extends TypeTag {
        public U64() {
        }

        static U64 load(Deserializer deserializer) throws DeserializationError {
            deserializer.increase_container_depth();
            Builder builder = new Builder();
            deserializer.decrease_container_depth();
            return builder.build();
        }

        public void serialize(Serializer serializer) throws SerializationError {
            serializer.increase_container_depth();
            serializer.serialize_variant_index(2);
            serializer.decrease_container_depth();
        }

        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            if (getClass() != obj.getClass()) return false;
            U64 other = (U64) obj;
            return true;
        }

        public int hashCode() {
            int value = 7;
            return value;
        }

        public static final class Builder {
            public U64 build() {
                return new U64(
                );
            }
        }
    }

    public static final class U128 extends TypeTag {
        public U128() {
        }

        static U128 load(Deserializer deserializer) throws DeserializationError {
            deserializer.increase_container_depth();
            Builder builder = new Builder();
            deserializer.decrease_container_depth();
            return builder.build();
        }

        public void serialize(Serializer serializer) throws SerializationError {
            serializer.increase_container_depth();
            serializer.serialize_variant_index(3);
            serializer.decrease_container_depth();
        }

        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            if (getClass() != obj.getClass()) return false;
            U128 other = (U128) obj;
            return true;
        }

        public int hashCode() {
            int value = 7;
            return value;
        }

        public static final class Builder {
            public U128 build() {
                return new U128(
                );
            }
        }
    }

    public static final class Address extends TypeTag {
        public Address() {
        }

        static Address load(Deserializer deserializer) throws DeserializationError {
            deserializer.increase_container_depth();
            Builder builder = new Builder();
            deserializer.decrease_container_depth();
            return builder.build();
        }

        public void serialize(Serializer serializer) throws SerializationError {
            serializer.increase_container_depth();
            serializer.serialize_variant_index(4);
            serializer.decrease_container_depth();
        }

        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            if (getClass() != obj.getClass()) return false;
            Address other = (Address) obj;
            return true;
        }

        public int hashCode() {
            int value = 7;
            return value;
        }

        public static final class Builder {
            public Address build() {
                return new Address(
                );
            }
        }
    }

    public static final class Signer extends TypeTag {
        public Signer() {
        }

        static Signer load(Deserializer deserializer) throws DeserializationError {
            deserializer.increase_container_depth();
            Builder builder = new Builder();
            deserializer.decrease_container_depth();
            return builder.build();
        }

        public void serialize(Serializer serializer) throws SerializationError {
            serializer.increase_container_depth();
            serializer.serialize_variant_index(5);
            serializer.decrease_container_depth();
        }

        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            if (getClass() != obj.getClass()) return false;
            Signer other = (Signer) obj;
            return true;
        }

        public int hashCode() {
            int value = 7;
            return value;
        }

        public static final class Builder {
            public Signer build() {
                return new Signer(
                );
            }
        }
    }

    public static final class Vector extends TypeTag {
        public final TypeTag value;

        public Vector(TypeTag value) {
            java.util.Objects.requireNonNull(value, "value must not be null");
            this.value = value;
        }

        static Vector load(Deserializer deserializer) throws DeserializationError {
            deserializer.increase_container_depth();
            Builder builder = new Builder();
            builder.value = TypeTag.deserialize(deserializer);
            deserializer.decrease_container_depth();
            return builder.build();
        }

        public void serialize(Serializer serializer) throws SerializationError {
            serializer.increase_container_depth();
            serializer.serialize_variant_index(6);
            value.serialize(serializer);
            serializer.decrease_container_depth();
        }

        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            if (getClass() != obj.getClass()) return false;
            Vector other = (Vector) obj;
            return java.util.Objects.equals(this.value, other.value);
        }

        public int hashCode() {
            int value = 7;
            value = 31 * value + (this.value != null ? this.value.hashCode() : 0);
            return value;
        }

        public static final class Builder {
            public TypeTag value;

            public Vector build() {
                return new Vector(
                        value
                );
            }
        }
    }

    public static final class Struct extends TypeTag {
        public final StructTag value;

        public Struct(StructTag value) {
            java.util.Objects.requireNonNull(value, "value must not be null");
            this.value = value;
        }

        static Struct load(Deserializer deserializer) throws DeserializationError {
            deserializer.increase_container_depth();
            Builder builder = new Builder();
            builder.value = StructTag.deserialize(deserializer);
            deserializer.decrease_container_depth();
            return builder.build();
        }

        public void serialize(Serializer serializer) throws SerializationError {
            serializer.increase_container_depth();
            serializer.serialize_variant_index(7);
            value.serialize(serializer);
            serializer.decrease_container_depth();
        }

        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            if (getClass() != obj.getClass()) return false;
            Struct other = (Struct) obj;
            return java.util.Objects.equals(this.value, other.value);
        }

        public int hashCode() {
            int value = 7;
            value = 31 * value + (this.value != null ? this.value.hashCode() : 0);
            return value;
        }

        public static final class Builder {
            public StructTag value;

            public Struct build() {
                return new Struct(
                        value
                );
            }
        }
    }
}

