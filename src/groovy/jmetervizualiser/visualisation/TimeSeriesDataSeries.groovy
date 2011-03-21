package jmetervizualiser.visualisation

import org.joda.time.DateTime

class TimeSeriesDataSeries {

    int pointInterval
    DateTime pointStart
    List<Long> data

    Map getAsJSON() {
        DateTime pointStartRoundedToNearestMillisecond = pointStart.withMillisOfSecond(0)
        return ['pointStart': pointStartRoundedToNearestMillisecond.toString(), 'pointInterval': pointInterval, 'data': data]
    }
}
