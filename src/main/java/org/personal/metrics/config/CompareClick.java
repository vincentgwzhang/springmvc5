package org.personal.metrics.config;

import com.codahale.metrics.RatioGauge;

public class CompareClick extends RatioGauge {

    private double meter1Clicked = 0d;
    private double meter2Clicked = 0d;

    public void markMeter1(double meter1Clicked) {
        this.meter1Clicked = meter1Clicked * 1000;
    }

    public void markMeter2(double meter2Clicked) {
        this.meter2Clicked = meter2Clicked * 1000;
    }

    @Override
    protected Ratio getRatio() {
        return Ratio.of(meter1Clicked, meter2Clicked);
    }
}
