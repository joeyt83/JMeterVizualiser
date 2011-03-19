package jmetervizualiser

import grails.test.GrailsUnitTestCase
import jmetervizualiser.util.MathUtils

class MathUtilsTests extends GrailsUnitTestCase {

    void testRoundToTwoDPs() {

            assertEquals 0.24, MathUtils.roundToTwoDPs(0.245)
            assertEquals 0.17, MathUtils.roundToTwoDPs(0.166666666666667)
            assertEquals 0.81, MathUtils.roundToTwoDPs(0.809)
            assertEquals 25.78, MathUtils.roundToTwoDPs(25.78)
            assertEquals 444.00, MathUtils.roundToTwoDPs(444)
            assertEquals 0.00, MathUtils.roundToTwoDPs(0.0)
            assertEquals 1.00, MathUtils.roundToTwoDPs(1.0)

        }
}
