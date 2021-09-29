package com.rb.pubgassistant;

public enum PubgEndpointsEnum {
    PLAYERS("/shards/steam/players"),
    SEASONS("/shards/steam/seasons");

    private String endpoint;
    private static final String DOMAIN = "https://api.pubg.com";

    private PubgEndpointsEnum(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getEndpoint() {
        return DOMAIN + endpoint;
    }
}
