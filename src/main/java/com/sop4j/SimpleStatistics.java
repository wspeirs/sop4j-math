/*
 *    Copyright 2013 William R. Speirs
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.sop4j;

import org.apache.commons.math3.random.MersenneTwister;
import org.apache.commons.math3.stat.Frequency;
import org.apache.commons.math3.stat.StatUtils;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;

import com.google.common.primitives.Doubles;
import com.google.common.primitives.Ints;

/**
 * How to easily compute simple stats with commons-math3
 *
 * @author William R. Speirs <bill.speirs@gmail.com>
 */
public class SimpleStatistics {

    private static final int NUM_VALUES = 10000;
    private static final int MAX_VALUE = 100;

    public static void main(String[] args) {
        final MersenneTwister rng = new MersenneTwister(); // used for RNG... READ THE DOCS!!!
        final int[] values = new int[NUM_VALUES];

        final DescriptiveStatistics descriptiveStats = new DescriptiveStatistics(); // stores values
        final SummaryStatistics summaryStats = new SummaryStatistics(); // doesn't store values
        final Frequency frequency = new Frequency();

        // add numbers into our stats
        for(int i=0; i < NUM_VALUES; ++i) {
            values[i] = rng.nextInt(MAX_VALUE);

            descriptiveStats.addValue(values[i]);
            summaryStats.addValue(values[i]);
            frequency.addValue(values[i]);
        }

        // print out some standard stats
        System.out.println("MIN: " + summaryStats.getMin());
        System.out.println("AVG: " + String.format("%.3f", summaryStats.getMean()));
        System.out.println("MAX: " + summaryStats.getMax());

        // get some more complex stats only offered by DescriptiveStatistics
        System.out.println("90%: " + descriptiveStats.getPercentile(90));
        System.out.println("MEDIAN: " + descriptiveStats.getPercentile(50));
        System.out.println("SKEWNESS: " + String.format("%.4f", descriptiveStats.getSkewness()));
        System.out.println("KURTOSIS: " + String.format("%.4f", descriptiveStats.getKurtosis()));

        // quick and dirty stats (need a little help from Guava to convert from int[] to double[])
        System.out.println("MIN: " + StatUtils.min(Doubles.toArray(Ints.asList(values))));
        System.out.println("AVG: " + String.format("%.4f", StatUtils.mean(Doubles.toArray(Ints.asList(values)))));
        System.out.println("MAX: " + StatUtils.max(Doubles.toArray(Ints.asList(values))));

        // some stats based upon frequencies
        System.out.println("NUM OF 7s: " + frequency.getCount(7));
        System.out.println("CUMULATIVE FREQUENCY OF 7: " + frequency.getCumFreq(7));
        System.out.println("PERCENTAGE OF 7s: " + frequency.getPct(7));
    }
}
