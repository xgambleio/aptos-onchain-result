package com.xg.w3.aptos.types;

import com.xg.w3.aptos.novi.bcs.BcsDeserializer;
import com.xg.w3.aptos.novi.bcs.BcsSerializer;
import com.xg.w3.aptos.novi.serde.*;

public final class ModuleId {
    public final AccountAddress address;
    public final Identifier name;

    public ModuleId(AccountAddress address, Identifier name) {
        java.util.Objects.requireNonNull(address, "address must not be null");
        java.util.Objects.requireNonNull(name, "name must not be null");
        this.address = address;
        this.name = name;
    }

    public static ModuleId deserialize(Deserializer deserializer) throws DeserializationError {
        deserializer.increase_container_depth();
        Builder builder = new Builder();
        builder.address = AccountAddress.deserialize(deserializer);
        builder.name = Identifier.deserialize(deserializer);
        deserializer.decrease_container_depth();
        return builder.build();
    }

    public static ModuleId bcsDeserialize(byte[] input) throws DeserializationError {
        if (input == null) {
            throw new DeserializationError("Cannot deserialize null array");
        }
        Deserializer deserializer = new BcsDeserializer(input);
        ModuleId value = deserialize(deserializer);
        if (deserializer.get_buffer_offset() < input.length) {
            throw new DeserializationError("Some input bytes were not read");
        }
        return value;
    }

    public void serialize(Serializer serializer) throws SerializationError {
        serializer.increase_container_depth();
        address.serialize(serializer);
        name.serialize(serializer);
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
        ModuleId other = (ModuleId) obj;
        if (!java.util.Objects.equals(this.address, other.address)) {
            return false;
        }
        return java.util.Objects.equals(this.name, other.name);
    }

    public int hashCode() {
        int value = 7;
        value = 31 * value + this.address.hashCode();
        value = 31 * value + this.name.hashCode();
        return value;
    }

    @Override
    public String toString() {
        return "ModuleId{" +
                "address=" + address +
                ", name=" + name +
                '}';
    }

    public static final class Builder {
        public AccountAddress address;
        public Identifier name;

        public ModuleId build() {
            return new ModuleId(
                    address,
                    name
            );
        }
    }
}
