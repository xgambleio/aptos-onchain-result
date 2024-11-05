package com.xg.w3.aptos.types;

import com.xg.w3.aptos.novi.bcs.BcsDeserializer;
import com.xg.w3.aptos.novi.bcs.BcsSerializer;
import com.xg.w3.aptos.novi.serde.*;

final class TraitHelpers {
    static void serialize_array16_u8_array(java.util.@ArrayLen(length = 16) List<@Unsigned Byte> value, Serializer serializer) throws SerializationError {
        if (value.size() != 16) {
            throw new IllegalArgumentException("Invalid length for fixed-size array: " + value.size() + " instead of " + 16);
        }
        for (@Unsigned Byte item : value) {
            serializer.serialize_u8(item);
        }
    }

    static void serialize_array32_u8_array(java.util.@ArrayLen(length = 32) List<@Unsigned Byte> value, Serializer serializer) throws SerializationError {
        if (value.size() != 32) {
            throw new IllegalArgumentException("Invalid length for fixed-size array: " + value.size() + " instead of " + 32);
        }
        for (@Unsigned Byte item : value) {
            serializer.serialize_u8(item);
        }
    }

    static java.util.@ArrayLen(length = 16) List<@Unsigned Byte> deserialize_array16_u8_array(Deserializer deserializer) throws DeserializationError {
        java.util.List<@Unsigned Byte> obj = new java.util.ArrayList<@Unsigned Byte>(16);
        for (long i = 0; i < 16; i++) {
            obj.add(deserializer.deserialize_u8());
        }
        return obj;
    }

    static java.util.@ArrayLen(length = 32) List<@Unsigned Byte> deserialize_array32_u8_array(Deserializer deserializer) throws DeserializationError {
        java.util.List<@Unsigned Byte> obj = new java.util.ArrayList<@Unsigned Byte>(32);
        for (long i = 0; i < 32; i++) {
            obj.add(deserializer.deserialize_u8());
        }
        return obj;
    }

    static void serialize_option_bytes(java.util.Optional<Bytes> value, Serializer serializer) throws SerializationError {
        if (value.isPresent()) {
            serializer.serialize_option_tag(true);
            serializer.serialize_bytes(value.get());
        } else {
            serializer.serialize_option_tag(false);
        }
    }

    static java.util.Optional<Bytes> deserialize_option_bytes(Deserializer deserializer) throws DeserializationError {
        boolean tag = deserializer.deserialize_option_tag();
        if (!tag) {
            return java.util.Optional.empty();
        } else {
            return java.util.Optional.of(deserializer.deserialize_bytes());
        }
    }

    static void serialize_option_str(java.util.Optional<String> value, Serializer serializer) throws SerializationError {
        if (value.isPresent()) {
            serializer.serialize_option_tag(true);
            serializer.serialize_str(value.get());
        } else {
            serializer.serialize_option_tag(false);
        }
    }

    static java.util.Optional<String> deserialize_option_str(Deserializer deserializer) throws DeserializationError {
        boolean tag = deserializer.deserialize_option_tag();
        if (!tag) {
            return java.util.Optional.empty();
        } else {
            return java.util.Optional.of(deserializer.deserialize_str());
        }
    }

    static void serialize_option_u64(java.util.Optional<@Unsigned Long> value, Serializer serializer) throws SerializationError {
        if (value.isPresent()) {
            serializer.serialize_option_tag(true);
            serializer.serialize_u64(value.get());
        } else {
            serializer.serialize_option_tag(false);
        }
    }

    static java.util.Optional<@Unsigned Long> deserialize_option_u64(Deserializer deserializer) throws DeserializationError {
        boolean tag = deserializer.deserialize_option_tag();
        if (!tag) {
            return java.util.Optional.empty();
        } else {
            return java.util.Optional.of(deserializer.deserialize_u64());
        }
    }


    static void serialize_vector_AccountAddress(java.util.List<AccountAddress> value, Serializer serializer) throws SerializationError {
        serializer.serialize_len(value.size());
        for (AccountAddress item : value) {
            item.serialize(serializer);
        }
    }

    static java.util.List<AccountAddress> deserialize_vector_AccountAddress(Deserializer deserializer) throws DeserializationError {
        long length = deserializer.deserialize_len();
        java.util.List<AccountAddress> obj = new java.util.ArrayList<AccountAddress>((int) length);
        for (long i = 0; i < length; i++) {
            obj.add(AccountAddress.deserialize(deserializer));
        }
        return obj;
    }

    static void serialize_vector_str(java.util.List<String> value, Serializer serializer) throws SerializationError {
        serializer.serialize_len(value.size());
        for (String item : value) {
            serializer.serialize_str(item);
        }
    }

    static java.util.List<String> deserialize_vector_str(Deserializer deserializer) throws DeserializationError {
        long length = deserializer.deserialize_len();
        java.util.List<String> obj = new java.util.ArrayList<String>((int) length);
        for (long i = 0; i < length; i++) {
            obj.add(deserializer.deserialize_str());
        }
        return obj;
    }

//    static void serialize_option_AuthenticationKey(java.util.Optional<AuthenticationKey> value, Serializer serializer) throws SerializationError {
//        if (value.isPresent()) {
//            serializer.serialize_option_tag(true);
//            value.get().serialize(serializer);
//        } else {
//            serializer.serialize_option_tag(false);
//        }
//    }
//
//    static java.util.Optional<AuthenticationKey> deserialize_option_AuthenticationKey(Deserializer deserializer) throws DeserializationError {
//        boolean tag = deserializer.deserialize_option_tag();
//        if (!tag) {
//            return java.util.Optional.empty();
//        } else {
//            return java.util.Optional.of(AuthenticationKey.deserialize(deserializer));
//        }
//    }

//    static void serialize_option_KeyRotationCapabilityResource(java.util.Optional<KeyRotationCapabilityResource> value, Serializer serializer) throws SerializationError {
//        if (value.isPresent()) {
//            serializer.serialize_option_tag(true);
//            value.get().serialize(serializer);
//        } else {
//            serializer.serialize_option_tag(false);
//        }
//    }
//
//    static java.util.Optional<KeyRotationCapabilityResource> deserialize_option_KeyRotationCapabilityResource(Deserializer deserializer) throws DeserializationError {
//        boolean tag = deserializer.deserialize_option_tag();
//        if (!tag) {
//            return java.util.Optional.empty();
//        } else {
//            return java.util.Optional.of(KeyRotationCapabilityResource.deserialize(deserializer));
//        }
//    }

    static void serialize_option_ScriptFunction(java.util.Optional<EntryFunction> value, Serializer serializer) throws SerializationError {
        if (value.isPresent()) {
            serializer.serialize_option_tag(true);
            value.get().serialize(serializer);
        } else {
            serializer.serialize_option_tag(false);
        }
    }

    static java.util.Optional<EntryFunction> deserialize_option_ScriptFunction(Deserializer deserializer) throws DeserializationError {
        boolean tag = deserializer.deserialize_option_tag();
        if (!tag) {
            return java.util.Optional.empty();
        } else {
            return java.util.Optional.of(EntryFunction.deserialize(deserializer));
        }
    }

//    static void serialize_option_WithdrawCapabilityResource(java.util.Optional<WithdrawCapabilityResource> value, Serializer serializer) throws SerializationError {
//        if (value.isPresent()) {
//            serializer.serialize_option_tag(true);
//            value.get().serialize(serializer);
//        } else {
//            serializer.serialize_option_tag(false);
//        }
//    }
//
//    static java.util.Optional<WithdrawCapabilityResource> deserialize_option_WithdrawCapabilityResource(Deserializer deserializer) throws DeserializationError {
//        boolean tag = deserializer.deserialize_option_tag();
//        if (!tag) {
//            return java.util.Optional.empty();
//        } else {
//            return java.util.Optional.of(WithdrawCapabilityResource.deserialize(deserializer));
//        }
//    }

//    static void serialize_tuple2_AccessPath_WriteOp(Tuple2<AccessPath, WriteOp> value, Serializer serializer) throws SerializationError {
//        value.field0.serialize(serializer);
//        value.field1.serialize(serializer);
//    }
//
//    static Tuple2<AccessPath, WriteOp> deserialize_tuple2_AccessPath_WriteOp(Deserializer deserializer) throws DeserializationError {
//        return new Tuple2<AccessPath, WriteOp>(
//                AccessPath.deserialize(deserializer),
//                WriteOp.deserialize(deserializer)
//        );
//    }

//    static void serialize_vector_ArgumentABI(java.util.List<ArgumentABI> value, Serializer serializer) throws SerializationError {
//        serializer.serialize_len(value.size());
//        for (ArgumentABI item : value) {
//            item.serialize(serializer);
//        }
//    }
//
//    static java.util.List<ArgumentABI> deserialize_vector_ArgumentABI(Deserializer deserializer) throws DeserializationError {
//        long length = deserializer.deserialize_len();
//        java.util.List<ArgumentABI> obj = new java.util.ArrayList<ArgumentABI>((int) length);
//        for (long i = 0; i < length; i++) {
//            obj.add(ArgumentABI.deserialize(deserializer));
//        }
//        return obj;
//    }

    static void serialize_vector_Module(java.util.List<Module> value, Serializer serializer) throws SerializationError {
        serializer.serialize_len(value.size());
        for (Module item : value) {
            item.serialize(serializer);
        }
    }

    static java.util.List<Module> deserialize_vector_Module(Deserializer deserializer) throws DeserializationError {
        long length = deserializer.deserialize_len();
        java.util.List<Module> obj = new java.util.ArrayList<>((int) length);
        for (long i = 0; i < length; i++) {
            obj.add(Module.deserialize(deserializer));
        }
        return obj;
    }

//    static void serialize_vector_TypeArgumentABI(java.util.List<TypeArgumentABI> value, Serializer serializer) throws SerializationError {
//        serializer.serialize_len(value.size());
//        for (TypeArgumentABI item : value) {
//            item.serialize(serializer);
//        }
//    }
//
//    static java.util.List<TypeArgumentABI> deserialize_vector_TypeArgumentABI(Deserializer deserializer) throws DeserializationError {
//        long length = deserializer.deserialize_len();
//        java.util.List<TypeArgumentABI> obj = new java.util.ArrayList<TypeArgumentABI>((int) length);
//        for (long i = 0; i < length; i++) {
//            obj.add(TypeArgumentABI.deserialize(deserializer));
//        }
//        return obj;
//    }

    static void serialize_vector_TypeTag(java.util.List<TypeTag> value, Serializer serializer) throws SerializationError {
        serializer.serialize_len(value.size());
        for (TypeTag item : value) {
            item.serialize(serializer);
        }
    }

    static java.util.List<TypeTag> deserialize_vector_TypeTag(Deserializer deserializer) throws DeserializationError {
        long length = deserializer.deserialize_len();
        java.util.List<TypeTag> obj = new java.util.ArrayList<TypeTag>((int) length);
        for (long i = 0; i < length; i++) {
            obj.add(TypeTag.deserialize(deserializer));
        }
        return obj;
    }

    static void serialize_vector_bytes(java.util.List<Bytes> value, Serializer serializer) throws SerializationError {
        serializer.serialize_len(value.size());
        for (Bytes item : value) {
            serializer.serialize_bytes(item);
        }
    }

    static java.util.List<Bytes> deserialize_vector_bytes(Deserializer deserializer) throws DeserializationError {
        long length = deserializer.deserialize_len();
        java.util.List<Bytes> obj = new java.util.ArrayList<Bytes>((int) length);
        for (long i = 0; i < length; i++) {
            obj.add(deserializer.deserialize_bytes());
        }
        return obj;
    }

//    static void serialize_vector_tuple2_AccessPath_WriteOp(java.util.List<Tuple2<AccessPath, WriteOp>> value, Serializer serializer) throws SerializationError {
//        serializer.serialize_len(value.size());
//        for (Tuple2<AccessPath, WriteOp> item : value) {
//            TraitHelpers.serialize_tuple2_AccessPath_WriteOp(item, serializer);
//        }
//    }
//
//    static java.util.List<Tuple2<AccessPath, WriteOp>> deserialize_vector_tuple2_AccessPath_WriteOp(Deserializer deserializer) throws DeserializationError {
//        long length = deserializer.deserialize_len();
//        java.util.List<Tuple2<AccessPath, WriteOp>> obj = new java.util.ArrayList<Tuple2<AccessPath, WriteOp>>((int) length);
//        for (long i = 0; i < length; i++) {
//            obj.add(TraitHelpers.deserialize_tuple2_AccessPath_WriteOp(deserializer));
//        }
//        return obj;
//    }

    static void serialize_vector_u8(java.util.List<@Unsigned Byte> value, Serializer serializer) throws SerializationError {
        serializer.serialize_len(value.size());
        for (@Unsigned Byte item : value) {
            serializer.serialize_u8(item);
        }
    }

    static java.util.List<@Unsigned Byte> deserialize_vector_u8(Deserializer deserializer) throws DeserializationError {
        long length = deserializer.deserialize_len();
        java.util.List<@Unsigned Byte> obj = new java.util.ArrayList<@Unsigned Byte>((int) length);
        for (long i = 0; i < length; i++) {
            obj.add(deserializer.deserialize_u8());
        }
        return obj;
    }

}

