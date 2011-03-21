package jmetervizualiser.visualisation

import org.joda.time.DateTime

class TimeSeriesDataSeriesTests extends GroovyTestCase {

    void testGetAsJSON() {

        DateTime startTime = new DateTime(1299948248832)
        List<Long> data = [123, 53, 456, 567, 52, 5]
        TimeSeriesDataSeries series = new TimeSeriesDataSeries(pointStart: startTime, pointInterval: 1000, data: data)


        assertEquals (["pointStart": '2011-03-12T16:44:08.000Z', "pointInterval": 1000, "data": [123, 53, 456, 567, 52, 5]], series.getAsJSON())
    }

}
