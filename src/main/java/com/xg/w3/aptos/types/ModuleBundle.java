package com.xg.w3.aptos.types;

import com.xg.w3.aptos.novi.bcs.BcsDeserializer;
import com.xg.w3.aptos.novi.bcs.BcsSerializer;
import com.xg.w3.aptos.novi.serde.*;

public final class ModuleBundle {
    public final AccountAddress package_address;
    public final java.util.List<Module> modules;
    public final java.util.Optional<EntryFunction> init_script;

    public ModuleBundle(AccountAddress package_address, java.util.List<Module> modules, java.util.Optional<EntryFunction> init_script) {
        java.util.Objects.requireNonNull(package_address, "package_address must not be null");
        java.util.Objects.requireNonNull(modules, "modules must not be null");
        java.util.Objects.requireNonNull(init_script, "init_script must not be null");
        this.package_address = package_address;
        this.modules = modules;
        this.init_script = init_script;
    }

    public static ModuleBundle deserialize(Deserializer deserializer) throws DeserializationError {
        deserializer.increase_container_depth();
        Builder builder = new Builder();
        builder.package_address = AccountAddress.deserialize(deserializer);
        builder.modules = TraitHelpers.deserialize_vector_Module(deserializer);
        builder.init_script = TraitHelpers.deserialize_option_ScriptFunction(deserializer);
        deserializer.decrease_container_depth();
        return builder.build();
    }

    public static ModuleBundle bcsDeserialize(byte[] input) throws DeserializationError {
        if (input == null) {
            throw new DeserializationError("Cannot deserialize null array");
        }
        Deserializer deserializer = new BcsDeserializer(input);
        ModuleBundle value = deserialize(deserializer);
        if (deserializer.get_buffer_offset() < input.length) {
            throw new DeserializationError("Some input bytes were not read");
        }
        return value;
    }

    public void serialize(Serializer serializer) throws SerializationError {
        serializer.increase_container_depth();
        package_address.serialize(serializer);
        TraitHelpers.serialize_vector_Module(modules, serializer);
        TraitHelpers.serialize_option_ScriptFunction(init_script, serializer);
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
        ModuleBundle other = (ModuleBundle) obj;
        if (!java.util.Objects.equals(this.package_address, other.package_address)) {
            return false;
        }
        if (!java.util.Objects.equals(this.modules, other.modules)) {
            return false;
        }
        return java.util.Objects.equals(this.init_script, other.init_script);
    }

    public int hashCode() {
        int value = 7;
        value = 31 * value + this.package_address.hashCode();
        value = 31 * value + this.modules.hashCode();
        value = 31 * value + (this.init_script.isPresent() ? this.init_script.hashCode() : 0);
        return value;
    }

    public static final class Builder {
        public AccountAddress package_address;
        public java.util.List<Module> modules;
        public java.util.Optional<EntryFunction> init_script;

        public ModuleBundle build() {
            return new ModuleBundle(
                    package_address,
                    modules,
                    init_script
            );
        }
    }
}
