package com.example.testapp.network;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TestResponse implements Parcelable {
    @SerializedName("RSRP")
    @Expose
    private double RSRP;
    @SerializedName("RSRQ")
    @Expose
    private double RSRQ;
    @SerializedName("SINR")
    @Expose
    private double SINR;

    public TestResponse() {
    }

    public TestResponse(double RSRP, double RSRQ, double SINR) {
        this.RSRP = RSRP;
        this.RSRQ = RSRQ;
        this.SINR = SINR;
    }

    protected TestResponse(Parcel in) {
        RSRP = in.readDouble();
        RSRQ = in.readDouble();
        SINR = in.readDouble();
    }

    public static final Creator<TestResponse> CREATOR = new Creator<TestResponse>() {
        @Override
        public TestResponse createFromParcel(Parcel in) {
            return new TestResponse(in);
        }

        @Override
        public TestResponse[] newArray(int size) {
            return new TestResponse[size];
        }
    };

    public double getRSRP() {
        return RSRP;
    }

    public void setRSRP(double RSRP) {
        this.RSRP = RSRP;
    }

    public double getRSRQ() {
        return RSRQ;
    }

    public void setRSRQ(double RSRQ) {
        this.RSRQ = RSRQ;
    }

    public double getSINR() {
        return SINR;
    }

    public void setSINR(double SINR) {
        this.SINR = SINR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(RSRP);
        dest.writeDouble(RSRQ);
        dest.writeDouble(SINR);
    }
}
