package com.rb.pubgassistant;

public class PubgPlayerModel {
    public StatsEntity duo;
    public StatsEntity duoFpp;
    public StatsEntity solo;
    public StatsEntity soloFpp;
    public StatsEntity squad;
    public StatsEntity squadFpp;

    public StatsEntity getDuo() {
        return duo;
    }

    public void setDuo(StatsEntity duo) {
        this.duo = duo;
    }

    public StatsEntity getDuoFpp() {
        return duoFpp;
    }

    public void setDuoFpp(StatsEntity duoFpp) {
        this.duoFpp = duoFpp;
    }

    public StatsEntity getSolo() {
        return solo;
    }

    public void setSolo(StatsEntity solo) {
        this.solo = solo;
    }

    public StatsEntity getSoloFpp() {
        return soloFpp;
    }

    public void setSoloFpp(StatsEntity soloFpp) {
        this.soloFpp = soloFpp;
    }

    public StatsEntity getSquad() {
        return squad;
    }

    public void setSquad(StatsEntity squad) {
        this.squad = squad;
    }

    public StatsEntity getSquadFpp() {
        return squadFpp;
    }

    public void setSquadFpp(StatsEntity squadFpp) {
        this.squadFpp = squadFpp;
    }
}



