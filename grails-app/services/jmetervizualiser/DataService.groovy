package jmetervizualiser

import org.apache.commons.vfs.FileObject
import groovy.util.slurpersupport.GPathResult
import java.util.Map.Entry
import jmetervizualiser.visualisation.TimeSeriesDataSeries
import org.joda.time.DateTime

class DataService {

    static transactional = true

    TimeSeriesDataSeries getAverageResponseTimeOverTime(FileObject file) {

		InputStream inputStream = file.getContent().getInputStream()
		GPathResult testResults = new XmlSlurper().parse(inputStream)

		Map<Long, List<Long>> data = getResponseTimeMap(testResults)

        List<Long> averageResponseTimes = []

        data.each { Entry timestamp ->
            Long sumOfResponseTimesForTimestamp = 0
            List responseTimes = timestamp.value as List
            responseTimes.each { Long responseTime ->
                sumOfResponseTimesForTimestamp += responseTime
            }
            long averageResponseTimeForTimestamp = sumOfResponseTimesForTimestamp / responseTimes.size()
            averageResponseTimes.add(averageResponseTimeForTimestamp)
        }

        DateTime startTime = getStartTimeForTest(testResults)

        return new TimeSeriesDataSeries(data: averageResponseTimes, pointInterval: 1000, pointStart: startTime)

    }

    private def getResponseTimeMap(GPathResult testResults) {
        Map<Long, List<Long>> data = [:]

        testResults.httpSample.each { def sample ->
            Long timestampInSecs = Long.parseLong(sample.@ts.text()) / 1000
            Long responseTime = Double.parseDouble(sample.@t.text())
            if (!data[timestampInSecs]) {
                data.put timestampInSecs, [responseTime] as List
            } else {
                List existingResponseTimes = data[timestampInSecs]
                existingResponseTimes.add responseTime
            }
        }

        return data
    }

    DateTime getStartTimeForTest(GPathResult testResults) {
		Long currentEarliestTimeStamp = Long.parseLong(testResults.httpSample[0].@ts.text())

		testResults.httpSample.each { def sample ->
			Long timeStamp = Long.parseLong(sample.@ts.text())
			if(timeStamp < currentEarliestTimeStamp)
			currentEarliestTimeStamp = timeStamp
		}

		return new DateTime(currentEarliestTimeStamp)

	}

	DateTime getEndTimeForTest(GPathResult testResults) {
		Long currentLatestTimeStamp = 0

		testResults.httpSample.each { def sample ->
			Long timeStamp = Long.parseLong(sample.@ts.text())
			if(timeStamp > currentLatestTimeStamp)
			currentLatestTimeStamp = timeStamp
		}

		return new DateTime(currentLatestTimeStamp)
	}
}
