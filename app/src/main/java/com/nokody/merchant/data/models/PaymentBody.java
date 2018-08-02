package com.nokody.merchant.data.models;

public class PaymentBody {

    private String PinCode;
    private String FromAccount;
    private String ToAccount;
    private Double Amount;

    public PaymentBody(String pinCode, String fromId, String toId, Double amount) {
        this.PinCode = pinCode;
        this.FromAccount = fromId;
        this.ToAccount = toId;
        this.Amount = amount;
    }

    public String getPinCode() {
        return PinCode;
    }

    public void setPinCode(String pinCode) {
        PinCode = pinCode;
    }

    public String getFromId() {
        return FromAccount;
    }

    public void setFromId(String fromId) {
        this.FromAccount = fromId;
    }

    public String getToId() {
        return ToAccount;
    }

    public void setToId(String toId) {
        this.ToAccount = toId;
    }

    public Double getAmount() {
        return Amount;
    }

    public void setAmount(Double amount) {
        this.Amount = amount;
    }
}
