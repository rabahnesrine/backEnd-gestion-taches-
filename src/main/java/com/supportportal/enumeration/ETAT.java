package com.supportportal.enumeration;

public enum ETAT {

    ETAT_ACTIVE,
    ETAT_SUSPENDED,
    ETAT_COMPLETED,
    ETAT_PAUSED,
    ETAT_TESTING,
    ETAT_DELIVERY,
    ETAT_DEVELOPMENT;
    private String [] etat;

    ETAT(String...etat){
        this.etat = etat;
    }

    public String[] getEtat(){
        return etat;
    }

}
