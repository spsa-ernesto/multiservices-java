package com.multiservices.restful.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class MathUtil {
	
	public static double average (List<Integer> dataList)
	{
	    double result = 0;

	    for (int i = 0; i < dataList.size(); i++) {
	        result += dataList.get(i);
	    }

	    return round(result / dataList.size(), 2);
	}	

	public static double standardDeviation (List<Integer> dataList)
	{
	    double average = average(dataList);
	    double squrDiffToAverageSum = 0;

	    for (int i = 0; i < dataList.size(); i++)
	    {
	        int value = dataList.get(i);
	        double squrDiffToAverage = Math.pow(value - average, 2);
	        squrDiffToAverageSum += squrDiffToAverage;
	    }

	    double averageOfDiffs = (double) squrDiffToAverageSum / (double) (dataList.size());

	    return round(Math.sqrt(averageOfDiffs), 2);
	}

	public static double round(double value, int places) {
		if (places < 0) throw new IllegalArgumentException();

		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}
}
