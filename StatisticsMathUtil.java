import java.util.Arrays;
import java.util.zip.DeflaterOutputStream;

/**
 * Created by sakther on 9/14/2017.
 */

/**
 * This class provides a set of simple statistical routines. Some of them
 * are present native in Essbase as well and some are not.
 * Contains:
 * <ul>
 * <li>min, max</li>
 * <li>sum, weighted sum</li>
 * <li>product, weighted product</li>
 * <li>average, weighted average</li>
 * <li>geometric mean, weighted geometric mean</li>
 * <li>harmonic mean, weighted harmonic mean</li>
 * <li>variance (var and varp), weighted variance</li>
 * <li>standard deviation (stdev and stdevp), weighted standard deviation</li>
 * <li>covariance, weighted covariance</li>
 * <li>correlation, weighted correlation</li>
 * <li>skewness, weighted skewness</li>
 * <li>kurtosis, weighted kurtosis</li>
 * <li>rank, mode, median, percentile, quartile</li>
 * </ul>
 */
public final class StatisticsMathUtil {

    /**
     * Computes minimum value of given sequence. Missing values are ignored
     *
     * @param data data array
     * @return minimum value in the array
     */
    static double MISSG = Double.MAX_VALUE;

    public static double min(double[] data) {
        int i, n = data.length;

        if (n == 0)
            return MISSG;

        double min = data[0];
        boolean flag = (min == MISSG);

        for (i = 1; i < n; i++) {
            double d = data[i];
            if (d != MISSG) {
                if (flag) {
                    min = d;
                    flag = false;
                } else if (d < min) {
                    min = d;
                }
            }
        }

        return min;
    }

    /**
     * Computes maximum value of given sequence. Missing values are ignored.
     *
     * @param data data array
     * @return maximum value in the array
     */
    public static double max(double[] data) {
        int i, n = data.length;

        if (n == 0)
            return MISSG;

        double max = data[0];
        boolean flag = (max == MISSG);

        for (i = 1; i < n; i++) {
            double d = data[i];
            if (d != MISSG) {
                if (flag) {
                    max = d;
                    flag = false;
                } else if (d > max) {
                    max = d;
                }
            }
        }
        return max;
    }

    /**
     * Computes sum of a given sequence. Missing values are ignored (treated as 0)
     *
     * @param data data array
     * @return sum of the data
     */
    public static double sum(double[] data) {
        int i, n = data.length;

        double sum = 0;
        for (i = 0; i < n; i++) {
            double d = data[i];
            if (d != MISSG) {
                sum += d;
            }
        }
        return sum;
    }

    /**
     * Computes weighted sum of a given sequence.
     * Missing values are ignored (treated as 0)
     *
     * @param data    data array
     * @param weights weights
     * @return weighted sum of the data
     */
    public static double sum(double[] data, double[] weights) {
        int i, n = data.length;

        double sum = MISSG;

        for (i = 0; i < n; i++) {
            double d = data[i], w = weights[i];
            if (d != MISSG && w != MISSG) {
                sum += d * w;
            }
        }
        return sum;
    }

    /**
     * Computes product of a given sequence. Missing values are ignored (treated as 0)
     *
     * @param data data array
     * @return product of the data
     */
    public static double product(double[] data) {
        int i, n = data.length;

        if (n == 0)
            return MISSG;

        double product = 1.;
        boolean flag = false;
        for (i = 0; i < n; i++) {
            double d = data[i];
            if (d != MISSG) {
                flag = true;
                product = product * d;
            }
        }

        if (!flag)
            return MISSG;

        return product;
    }

    /**
     * Computes weighted product of a given sequence.
     * Missing values are ignored (treated as 0)
     *
     * @param data    data array
     * @param weights weights
     * @return weighted product of the data
     */
    public static double product(double[] data, double[] weights) {
        int i, n = data.length;

        if (n == 0)
            return MISSG;

        double product = 1.;
        boolean flag = false;

        for (i = 0; i < n; i++) {
            double d = data[i], w = weights[i];
            if (d != MISSG && w != MISSG) {
                d = Math.pow(d, w);
                if (d != MISSG) {
                    flag = true;
                    product = product * d;
                }
            }
        }
        if (!flag)
            return MISSG;

        return product;
    }

    /**
     * Computes count of non-missing values in a given sequence.
     *
     * @param data data array
     * @return count of the non-missing data
     */
    public static int count(double[] data) {
        int i, n = data.length;

        int count = 0;

        for (i = 0; i < n; i++) {
            double d = data[i];
            if (d != MISSG) {
                count++;
            }
        }
        return count;
    }

    /**
     * Computes the average value of a given sequence. Missing values are ignored.
     *
     * @param data data array
     * @return average of the data
     */
    public static double avg(double[] data) {
        int i, n = data.length;

        double sum = 0;
        int count = 0;

        for (i = 0; i < n; i++) {
            double d = data[i];
            sum += d;
            count++;
        }

        if (count == 0)
            return 0;

        return sum / count;
    }


    /**
     * Computes weighted average of a given sequence. Missing values are ignored
     *
     * @param data    data array
     * @param weights weights
     * @return weighted average of the data
     */
    public static double avg(double[] data, double[] weights) {
        int i, n = data.length;

        double sum = 0;
        double weight = 0;

        for (i = 0; i < n; i++) {
            double d = data[i], w = weights[i];
            sum += d * w;
            weight += w;
        }

        return sum / weight;
    }


    /**
     * Computes the geometric average value of a given sequence.
     * Missing values are ignored.
     *
     * @param data data array
     * @return average of the data
     */
    public static double geomean(double[] data) {
        int i, n = data.length;

        if (n == 0)
            return MISSG;

        double product = 1.;
        int count = 0;

        for (i = 0; i < n; i++) {
            double d = data[i];
            if (d != MISSG) {
                product = product * d;
                count++;
            }
        }

        if (count == 0)
            return MISSG;

        return Math.pow(product, 1. / (double) count);
    }

    /**
     * Computes weighted geometric average of a given sequence.
     * Missing values are ignored
     *
     * @param data    data array
     * @param weights weights
     * @return weighted average of the data
     */
    public static double geomean(double[] data, double[] weights) {
        int i, n = data.length;

        double product = 1.;
        double weight = MISSG;

        for (i = 0; i < n; i++) {
            double d = data[i], w = weights[i];
            if (d != MISSG && w != MISSG) {
                product = product * Math.pow(d, w);
                weight += w;
            }
        }

        if (weight == MISSG || weight == 0.)
            return MISSG;

        return Math.pow(product, 1. / weight);
    }

    /**
     * Computes harmonic mean of a given sequence.
     * Missing values are ignored.
     *
     * @param data data array
     * @return harmonic mean of the data
     */
    public static double harmean(double[] data) {
        int i, n = data.length;

        if (n == 0)
            return MISSG;

        double sum = MISSG;
        int count = 0;

        for (i = 0; i < n; i++) {
            double d = data[i];
            if (d != MISSG) {
                if (d == 0.)
                    return MISSG;
                sum = sum + 1. / d;
                count++;
            }
        }

        if (count == 0 || sum == 0.)
            return MISSG;

        return count / sum;
    }

    /**
     * Computes weighted harmonic mean of a given sequence.
     * Missing values are ignored
     *
     * @param data    data array
     * @param weights weights
     * @return weighted harmonic mean of the data
     */
    public static double harmean(double[] data, double[] weights) {
        int i, n = data.length;

        double sum = MISSG;
        double weight = MISSG;

        for (i = 0; i < n; i++) {
            double d = data[i], w = weights[i];
            if (d != MISSG && w != MISSG) {
                if (d == 0.)
                    return MISSG;
                sum += w / d;
                weight += w;
            }
        }

        return weight / sum;
    }

    /**
     * Computes variance of a given sequence. Missing values are ignored
     *
     * @param data data array
     * @return variance of the data
     */
    public static double var(double[] data) {
        int i, n = data.length;

        double d, sum = 0, avg = 0;
        int count = 0;

        for (i = 0; i < n; i++) {
            d = data[i];
            sum += d;
            count++;
        }

        if (count < 2)
            return 0;

        avg = sum / count;
        sum = 0.;
        for (i = 0; i < n; i++) {
            d = data[i];
            d = d - avg;
            d = d * d;
            sum = sum + d;
        }

        return (sum / (count - 1));
    }

    /**
     * Computes standard deviation of a given sequence. Missing values are ignored
     *
     * @param data data array
     * @return stdev of the data
     */
    public static double stdev(double[] data) {
        return Math.sqrt(var(data));
    }


    /**
     * Computes weighted variance of a given sequence. Missing values are ignored
     *
     * @param data    data array
     * @param weights weights
     * @return weighted variance of the data
     */
    public static double var(double[] data, double[] weights) {
        int i, n = data.length;

        double d, sum = MISSG, avg = MISSG;
        double w, weight = MISSG;

        for (i = 0; i < n; i++) {
            d = data[i];
            w = weights[i];
            if (d != MISSG && w != MISSG) {
                sum += d * w;
                weight += w;
            }
        }


        avg = sum / weight;
        sum = 0.;
        for (i = 0; i < n; i++) {
            d = data[i];
            w = weights[i];
            if (d == MISSG || w == MISSG)
                continue;
            d = d - avg;
            d = d * d * w;
            sum = sum + d;
        }
        return (sum / (weight - 1.));
    }

    /**
     * Computes weighted standard deviation of a given sequence.
     * Missing values are ignored
     *
     * @param data    data array
     * @param weights weights
     * @return weighted standard deviation of the data
     * (without taking missing values into account)
     */
    public static double stdev(double[] data, double[] weights) {
        return Math.sqrt(var(data, weights));
    }


    /**
     * Computes covariance between two sequences.
     * If a missing value is encountered in either of the sequences,
     * the corresponding position is skipped in both of them.
     *
     * @param x first array
     * @param y second array
     * @return covariance
     */
    public static double covariance(double[] x, double[] y) {
        int i, n = x.length;

        if (n == 0)
            return MISSG;

        double d1, d2, avg1 = MISSG, avg2 = MISSG;
        int count = 0;

        for (i = 0; i < n; i++) {
            d1 = x[i];
            d2 = y[i];
            if (d1 != MISSG && d2 != MISSG) {
                avg1 += d1;
                avg2 += d2;
                count++;
            }
        }

        if (count < 1)
            return MISSG;

        avg1 = avg1 / count;
        avg2 = avg2 / count;

        double covar = 0.;
        for (i = 0; i < n; i++) {
            d1 = x[i];
            d2 = y[i];
            if (d1 != MISSG && d2 != MISSG) {
                d1 = d1 - avg1;
                d2 = d2 - avg2;
                covar = covar + d1 * d2;
            }
        }
        return covar / count;
    }

    /**
     * Computes weighted covariance between two sequences
     * If a missing value is encountered in either of the sequences,
     * the corresponding position is skipped in both of them.
     *
     * @param x first array
     * @param y second array
     * @return correlation
     */
    public static double covariance(double[] x, double[] y, double[] weights) {
        int i, n = x.length;

        if (n == 0)
            return MISSG;

        double d1, d2, avg1 = MISSG, avg2 = MISSG;
        double w, weight = MISSG;

        for (i = 0; i < n; i++) {
            d1 = x[i];
            d2 = y[i];
            w = weights[i];
            if (d1 != MISSG && d2 != MISSG && w != MISSG) {
                avg1 += d1 * w;
                avg2 += d2 * w;
                weight += w;
            }
        }

        if (avg1 == MISSG || weight == MISSG || weight == 0.)
            return MISSG;

        avg1 = avg1 / weight;
        avg2 = avg2 / weight;

        double covar = 0.;
        for (i = 0; i < n; i++) {
            d1 = x[i];
            d2 = y[i];
            w = weights[i];
            if (d1 != MISSG && d2 != MISSG && w != MISSG) {
                d1 = d1 - avg1;
                d2 = d2 - avg2;
                covar = covar + w * d1 * d2;
            }
        }
        return covar / weight;
    }

    /**
     * Computes correlation between two sequences
     * If a missing value is encountered in either of the sequences,
     * the corresponding position is skipped in both of them.
     *
     * @param x first array
     * @param y second array
     * @return correlation
     */
    public static double correlation(double[] x, double[] y) {
        int i, n = x.length;

        if (n == 0)
            return MISSG;

        double d1, d2, avg1 = MISSG, avg2 = MISSG;
        int count = 0;

        for (i = 0; i < n; i++) {
            d1 = x[i];
            d2 = y[i];
            if (d1 != MISSG && d2 != MISSG) {
                avg1 += d1;
                avg2 += d2;
                count++;
            }
        }

        if (count < 2)
            return MISSG;

        avg1 = avg1 / count;
        avg2 = avg2 / count;

        double stdev1 = 0.;
        double stdev2 = 0.;
        double covar = 0.;
        for (i = 0; i < n; i++) {
            d1 = x[i];
            d2 = y[i];
            if (d1 != MISSG && d2 != MISSG) {
                d1 = d1 - avg1;
                d2 = d2 - avg2;
                covar = covar + d1 * d2;
                stdev1 = stdev1 + d1 * d1;
                stdev2 = stdev2 + d2 * d2;
            }
        }

        stdev1 = Math.sqrt(stdev1 / (count - 1));
        stdev2 = Math.sqrt(stdev2 / (count - 1));
        covar = covar / count;

        return covar / (stdev1 * stdev2);
    }

    /**
     * Computes weighted correlation between two sequences
     * If a missing value is encountered in either of the sequences,
     * the corresponding position is skipped in both of them.
     *
     * @param x first array
     * @param y second array
     * @return correlation
     */
    public static double correlation(double[] x, double[] y, double[] weights) {
        int i, n = x.length;

        if (n == 0)
            return MISSG;

        double d1, d2, avg1 = MISSG, avg2 = MISSG;
        double w, weight = MISSG;

        for (i = 0; i < n; i++) {
            d1 = x[i];
            d2 = y[i];
            w = weights[i];
            if (d1 != MISSG && d2 != MISSG && w != MISSG) {
                avg1 += d1 * w;
                avg2 += d2 * w;
                weight += w;
            }
        }

        avg1 = avg1 / weight;
        avg2 = avg2 / weight;

        double stdev1 = 0.;
        double stdev2 = 0.;
        double covar = 0.;
        for (i = 0; i < n; i++) {
            d1 = x[i];
            d2 = y[i];
            w = weights[i];
            if (d1 != MISSG && d2 != MISSG && w != MISSG) {
                d1 = d1 - avg1;
                d2 = d2 - avg2;
                covar = covar + w * d1 * d2;
                stdev1 = stdev1 + w * d1 * d1;
                stdev2 = stdev2 + w * d2 * d2;
            }
        }

        stdev1 = Math.sqrt(stdev1 / (weight - 1.));
        stdev2 = Math.sqrt(stdev2 / (weight - 1.));
        covar = covar / weight;

        return covar / (stdev1 * stdev2);
    }

    /**
     * Computes skewness of a sequence. Missing values are skipped
     *
     * @param data data array
     * @return scewness of the sequence
     */
    public static double skew(double[] data) {
        int i, n = data.length;

        if (n == 0)
            return 0;

        double d, avg = 0;
        int count = 0;

        for (i = 0; i < n; i++) {
            d = data[i];
            avg += d;
            count++;

        }
        if (count < 3)
            return 0;

        avg = avg / count;

        double stdev = 0.;
        for (i = 0; i < n; i++) {
            d = data[i];
            d = d - avg;
            stdev = stdev + d * d;
        }
        stdev = Math.sqrt(stdev / (count - 1));

        if (stdev == 0.)
            return 0;

        double skew = 0.;
        for (i = 0; i < n; i++) {
            d = data[i];
            d = d - avg;
            d = d / stdev;
            skew = skew + d * d * d;
        }
        return skew * count / ((count - 1) * (count - 2));
    }

    /**
     * Computes weighted skewness of a sequence. Missing values are ignored
     *
     * @param data data array
     * @return skewness of the sequence
     */
    public static double skew(double[] data, double[] weights) {
        int i, n = data.length;

        if (n == 0)
            return MISSG;

        double d, avg = MISSG;
        double w, weight = MISSG;

        for (i = 0; i < n; i++) {
            d = data[i];
            w = weights[i];
            if (d != MISSG && w != MISSG) {
                avg += w * d;
                weight += w;
            }
        }

        avg = avg / weight;

        double stdev = 0.;
        for (i = 0; i < n; i++) {
            d = data[i];
            w = weights[i];
            if (d != MISSG && w != MISSG) {
                d = d - avg;
                stdev = stdev + w * d * d;
            }
        }

        stdev = Math.sqrt(stdev / (weight - 1));

        if (stdev == 0.)
            return MISSG;
        double skew = 0.;
        for (i = 0; i < n; i++) {
            d = data[i];
            w = weights[i];
            if (d != MISSG && w != MISSG) {
                d = d - avg;
                d = d / stdev;
                skew = skew + w * d * d * d;
            }
        }

        return skew * weight / ((weight - 1.) * (weight - 2.));
    }

    /**
     * Computes kurtosis of a sequence. Missing values are skipped
     *
     * @param data data array
     * @return kurtosis of the sequence
     */
    public static double kurt(double[] data) {
        int i, n = data.length;

        if (n == 0)
            return 0;

        double d, avg = 0;
        int count = 0;

        for (i = 0; i < n; i++) {
            d = data[i];
            avg += d;
            count++;
        }

        if (count < 4)
            return 0;

        avg = avg / count;

        double stdev = 0.;
        for (i = 0; i < n; i++) {
            d = data[i];
            d = d - avg;
            stdev = stdev + d * d;
        }

        stdev = Math.sqrt(stdev / (count - 1));

        if (stdev == 0.)
            return 0;

        double kurt = 0.;
        for (i = 0; i < n; i++) {
            d = data[i];
            d = d - avg;
            d = d / stdev;
            kurt = kurt + d * d * d * d;
        }

        kurt = kurt * count * (count + 1) / (count - 1) - 3 * (count - 1) * (count - 1);
        return kurt / ((count - 2) * (count - 3));
    }

    /**
     * Computes weighted kurtosis of a sequence. Missing values are ignored
     *
     * @param data data array
     * @return kurtosis of the sequence
     */
    public static double kurt(double[] data, double[] weights) {
        int i, n = data.length;

        if (n == 0)
            return MISSG;

        double d, avg = MISSG;
        double w, weight = MISSG;

        for (i = 0; i < n; i++) {
            d = data[i];
            w = weights[i];
            if (d != MISSG && w != MISSG) {
                avg += w * d;
                weight += w;
            }
        }

        avg = avg / weight;

        double stdev = 0.;
        for (i = 0; i < n; i++) {
            d = data[i];
            w = weights[i];
            d = d - avg;
            stdev = stdev + w * d * d;
        }

        stdev = Math.sqrt(stdev / (weight - 1));

        double kurt = 0.;
        for (i = 0; i < n; i++) {
            d = data[i];
            w = weights[i];
            d = d - avg;
            d = d / stdev;
            kurt = kurt + w * d * d * d * d;
        }

        kurt = kurt * weight * (weight + 1.) / (weight - 1.) -
                3 * (weight - 1.) * (weight - 1.);
        return kurt / ((weight - 2.) * (weight - 3.));
    }

    /**
     * Computes rank of a value relative to a given sequence.
     * Missing elements in the sequence are ignored. Rank is 1-based.
     * Missing value is not ranked.
     *
     * @param value value to be ranked
     * @param data  array of data
     * @return rank in the sequence as a double
     */
    public static double rank(double value, double[] data) {
        int i = 0, n = data.length;
        double d;
        int rank;

        if (value == MISSG)
            return MISSG;

        double[] ddd = new double[n];

        int j = 0;
        for (i = 0; i < n; i++) {
            d = data[i];
            if (d != MISSG) {
                ddd[j] = d;
                j++;
            }
        }
        n = j;
        if (n == 0)
            return MISSG;

        if (n == 1) {
            if (ddd[0] > value)
                return 2.;
            else
                return 1.;
        }

        Arrays.sort(ddd);

        rank = 1;
        while (ddd[n - rank] > value) {
            rank++;
            if (rank > n)
                break;
        }
        return (double) rank;
    }

    /**
     * Computes mode of a sequence. Missing values are ignored
     *
     * @param data array of data
     * @return mode of the sequence
     */
    public static double mode(double[] data) {
        int i, j, n = data.length, maxFreq, freq;
        double d, mode;
        double[] ddd = new double[n];

        j = 0;
        for (i = 0; i < n; i++) {
            if (data[i] != MISSG) {
                ddd[j] = data[i];
                j++;
            }
        }
        n = j;
        if (n == 0)
            return MISSG;

        if (n == 1)
            return ddd[0];

        Arrays.sort(ddd, 0, n - 1);

        mode = ddd[0];
        maxFreq = 1;
        while (i < n - 1) {
            freq = 1;
            d = ddd[i];
            i++;
            while (ddd[i] == d) {
                freq++;
                i++;
                if (i >= n)
                    break;
            }
            if (freq > maxFreq) {
                maxFreq = freq;
                mode = d;
            }
        }
        return mode;
    }

    /**
     * Computes median of a sequence. Missing values are ignored
     *
     * @param data data array
     * @result median of the sequence
     */
    public static double median(double[] data) {
        int i, j, n = data.length;
        int midIndex;
        double median;
        double[] ddd = new double[n];

        j = 0;
        for (i = 0; i < n; i++) {
            ddd[j] = data[i];
            j++;
        }
        n = j;

        if (n == 0)
            return 0;

        Arrays.sort(ddd, 0, n - 1);

        midIndex = n / 2;
        if (n % 2 == 0) {
        /* Average of the two middle numbers */
            median = (ddd[midIndex] + ddd[midIndex - 1]) / 2;
        } else {
            median = ddd[midIndex];
        }
        return median;
    }

    /**
     * Computes percentile of a sequence. Missing values are ignored
     *
     * @param percent percent value
     * @param data    double array
     * @result percentile of the sequence
     */
    public static double percentile(double percent, double[] data) {
        int i, j, n = data.length;
        int midIndex;
        double median, temp;
        double[] ddd = new double[n];

        j = 0;
        for (i = 0; i < n; i++) {
            ddd[j] = data[i];
            j++;
        }
        n = j;

        if (n == 0)
            return 0;

        Arrays.sort(ddd, 0, n - 1);

        if (percent == 0.)
            return ddd[0];

        if (percent == 1.)
            return ddd[n - 1];

        temp = percent * (double) n;
        median = Math.floor(temp);
        midIndex = (int) median;

        if (median != temp) {
            temp -= median;
            median = ddd[midIndex - 1];
            median += (ddd[midIndex] - median) * temp;
        } else {
            median = ddd[midIndex];
        }
        return median;
    }

    /**
     * Computes percentile of a part of a sequence. Missing values are ignored
     *
     * @param percent percent value
     * @param size    size to use
     * @param data    data array
     * @result percentile of the subsequence
     */
    public static double percentile(double percent, int size, double[] data) {
        int i, j, n = data.length;
        if (n > size)
            n = size;
        int midIndex;
        double median, temp;
        double[] ddd = new double[n];

        j = 0;
        for (i = 0; i < n; i++) {
            if (data[i] != MISSG) {
                ddd[j] = data[i];
                j++;
            }
        }

        n = j;

        if (n == 0)
            return MISSG;

        Arrays.sort(ddd, 0, n - 1);

        if (percent == 0.)
            return ddd[0];

        if (percent == 1.)
            return ddd[n - 1];

        temp = percent * (double) n;
        median = Math.floor(temp);
        midIndex = (int) median;

        if (median != temp) {
            temp -= median;
            median = ddd[midIndex - 1];
            median += (ddd[midIndex] - median) * temp;
        } else {
            median = ddd[midIndex];
        }
        return median;
    }

    /**
     * Computes quartile of a sequence. Missing values are ignored
     *
     * @param quart indicates which value to return
     *              Possible values are:
     *              <ul>
     *              <li>0 - return minimum</li>
     *              <li>1 - return 25% percentile</li>
     *              <li>2 - return median</li>
     *              <li>3 - return 75% percentile</li>
     *              <li>4 - return maximum</li>
     *              </ul>
     * @param data  double array
     * @result quartile of the sequence
     */
    public static double quartile(int quart, double[] data) {
        switch (quart) {
            case 0:
                return min(data);
            case 1:
                return percentile(0.25, data);
            case 2:
                return median(data);
            case 3:
                return percentile(0.75, data);
            case 4:
                return max(data);
            default:
                return MISSG;
        }
    }

}