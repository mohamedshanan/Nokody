package com.nokody.merchant.data.models;

import org.parceler.Parcel;

@Parcel
public class TokenUpdateBody {

    private Integer Id;
    private String DeviceId;

    public TokenUpdateBody(Integer Id, String DeviceId) {
        this.Id = Id;
        this.DeviceId = DeviceId;
    }
}
