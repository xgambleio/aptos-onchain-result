package com.xg.w3.aptos.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class SubmitTransactionRequestBase {
    @JsonProperty("sender")//: f"{sender.address()}",
    private String sender;
    @JsonProperty("sequence_number")//: str(self.account_sequence_number(sender.address())),
    private String sequenceNumber;
    @JsonProperty("max_gas_amount")//: "2000",
    private String maxGasAmount;
    @JsonProperty("gas_unit_price")//: "1",
    private String gasUnitPrice;
    @JsonProperty("expiration_timestamp_secs")//: str(int(time.time()) + 600),
    private String expirationTimestampSecs;
    @JsonProperty("payload")//: payload,
    private TransactionPayload payload;
    
//    @JsonProperty("with_fee_payer")//: payload,
//    private boolean withFeePayer;
    
    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(String sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public String getMaxGasAmount() {
        return maxGasAmount;
    }

    public void setMaxGasAmount(String maxGasAmount) {
        this.maxGasAmount = maxGasAmount;
    }

    public String getGasUnitPrice() {
        return gasUnitPrice;
    }

    public void setGasUnitPrice(String gasUnitPrice) {
        this.gasUnitPrice = gasUnitPrice;
    }

    public String getExpirationTimestampSecs() {
        return expirationTimestampSecs;
    }

    public void setExpirationTimestampSecs(String expirationTimestampSecs) {
        this.expirationTimestampSecs = expirationTimestampSecs;
    }

    public TransactionPayload getPayload() {
        return payload;
    }

    public void setPayload(TransactionPayload payload) {
        this.payload = payload;
    }

//    public boolean isWithFeePayer() {
//        return withFeePayer;
//    }
//
//    public void setWithFeePayer(boolean withFeePayer) {
//        this.withFeePayer = withFeePayer;
//    }
    
    @Override
    public String toString() {
        return "SubmitTransactionRequestBase{" +
                "sender='" + sender + '\'' +
                ", sequenceNumber='" + sequenceNumber + '\'' +
                ", maxGasAmount='" + maxGasAmount + '\'' +
                ", gasUnitPrice='" + gasUnitPrice + '\'' +
                ", expirationTimestampSecs='" + expirationTimestampSecs + '\'' +
                ", payload=" + payload +
//                ", withFeePayer=" + withFeePayer +
                '}';
    }
}
