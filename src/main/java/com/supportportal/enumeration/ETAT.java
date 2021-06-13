package com.supportportal.enumeration;

public enum ETAT {

    ETAT_ACTIVE,
    ETAT_SUSPENDED,
    ETAT_WAITING,
    ETAT_PAUSED;

    private String [] etat;

    ETAT(String...etat){
        this.etat = etat;
    }

    public String[] getEtat(){
        return etat;
    }

}
