package com.multiservices.restful.customer;

public class KpiData {

    private Number averageAge;
    private Number standardDeviation;
    
    public KpiData(Number averageAge, Number standardDeviation) {
    	this.averageAge = averageAge;
    	this.standardDeviation = standardDeviation;
    }

    public Number getAverageAge() {
        return averageAge;
    }

    public Number getStandardDeviation() {
        return standardDeviation;
    }
    
}