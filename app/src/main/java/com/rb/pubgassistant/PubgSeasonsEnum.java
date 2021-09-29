package com.rb.pubgassistant;

public enum PubgSeasonsEnum {
    PC_2018_11("division.bro.official.pc-2018-11"),
    PC_2018_12("division.bro.official.pc-2018-12"),
    PC_2018_13("division.bro.official.pc-2018-13");

    public String seasonId;

    private PubgSeasonsEnum(String seasonId) {
        this.seasonId = seasonId;
    }

    public String getSeasonId() {
        return seasonId;
    }

    @Override
    public String toString() {
        return seasonId;
    }
}
