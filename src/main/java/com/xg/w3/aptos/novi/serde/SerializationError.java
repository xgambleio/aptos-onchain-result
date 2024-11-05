// Copyright (c) Facebook, Inc. and its affiliates
// SPDX-License-Identifier: MIT OR Apache-2.0

package com.xg.w3.aptos.novi.serde;

@SuppressWarnings("serial")
public final class SerializationError extends Exception {
    public SerializationError(String s) {
        super(s);
    }
}
