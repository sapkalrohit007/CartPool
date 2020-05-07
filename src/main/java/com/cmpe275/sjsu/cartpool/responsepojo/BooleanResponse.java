package com.cmpe275.sjsu.cartpool.responsepojo;

public class BooleanResponse {
    boolean isPoolLeader;

    public boolean isPoolLeader() {
        return isPoolLeader;
    }

    public void setPoolLeader(boolean poolLeader) {
        isPoolLeader = poolLeader;
    }

    public BooleanResponse(boolean isPoolLeader) {
        this.isPoolLeader = isPoolLeader;
    }

    public BooleanResponse(){

    }
}
