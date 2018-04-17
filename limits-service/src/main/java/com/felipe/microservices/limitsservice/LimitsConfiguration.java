package com.felipe.microservices.limitsservice;

public class LimitsConfiguration {
    private int minimum;
    private int maximum;

    public LimitsConfiguration() {}

    public LimitsConfiguration(int minimum, int maximum) {
        this.minimum = minimum;
        this.maximum = maximum;
    }

    public int getMinimum() {
        return minimum;
    }

    public int getMaximum() {
        return maximum;
    }
}
