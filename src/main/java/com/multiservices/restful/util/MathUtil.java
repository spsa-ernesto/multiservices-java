package com.multiservices.restful.util;

import java.util.List;

public class MathUtil {
	
	public static double average (List<Integer> dataList)
	{
	    double result = 0;

	    for (int i = 0; i < dataList.size(); i++) {
	        result += dataList.get(i);
	    }

	    return result / dataList.size();
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

	    return Math.sqrt(averageOfDiffs);
	}
	
}
