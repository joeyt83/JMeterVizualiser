package jmetervizualiser.util

import java.text.DecimalFormat

class MathUtils {

    public static double roundToTwoDPs(double d) {
        DecimalFormat twoDForm = new DecimalFormat("#.##")
        return Double.valueOf(twoDForm.format(d));
    }
}
