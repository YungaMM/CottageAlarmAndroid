package com.example.cottagealarmandroid.app.model;

public class Energy {

    public static final String NAME_PREFS_ENERGY = "energy";
    private boolean energy;

    public Energy(final boolean energy) {
        this.energy = energy;
    }

    public boolean isEnergy() {
        return energy;
    }

    public void setEnergy(boolean energy) {
        this.energy = energy;
    }
}
