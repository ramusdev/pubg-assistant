package com.rb.pubgassistant;

public enum PubgSeasonsEnum {
    PC_2018_11("division.bro.official.pc-2018-11"),
    PC_2018_12("division.bro.official.pc-2018-12"),
    PC_2018_13("division.bro.official.pc-2018-13"),
    PC_2018_14("division.bro.official.pc-2018-14"),
    PC_2018_15("division.bro.official.pc-2018-15");

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
