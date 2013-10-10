package com.alorma.megsjc.data.model;

/**
 * Created by Bernat on 7/10/13.
 */
public class Agrupament {

    private String name;
    private String addr;
    private String cp;
    private String poblacio;
    private String email;
    private String web;
    private String demarcacio;
    private String sots;
    private String logo;
    private double lat;
    private double lon;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getPoblacio() {
        return poblacio;
    }

    public void setPoblacio(String poblacio) {
        this.poblacio = poblacio;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getDemarcacio() {
        return demarcacio;
    }

    public void setDemarcacio(String demarcacio) {
        this.demarcacio = demarcacio;
    }

    public String getSots() {
        return sots;
    }

    public void setSots(String sots) {
        this.sots = sots;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    @Override
    public String toString() {
        return "Agrupament{" +
                "name='" + name + '\'' +
                ", addr='" + addr + '\'' +
                ", cp='" + cp + '\'' +
                ", poblacio='" + poblacio + '\'' +
                ", email='" + email + '\'' +
                ", web='" + web + '\'' +
                ", demarcacio='" + demarcacio + '\'' +
                ", sots='" + sots + '\'' +
                ", logo='" + logo + '\'' +
                ", lat=" + lat +
                ", lon=" + lon +
                '}';
    }
}
