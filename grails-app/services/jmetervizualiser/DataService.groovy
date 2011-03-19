package jmetervizualiser

import org.apache.commons.vfs.FileObject
import groovy.util.slurpersupport.GPathResult
import java.util.Map.Entry

class DataService {

    static transactional = true

    List<Long> getAverageResponseTimeOverTime(FileObject file) {

		InputStream inputStream = file.getContent().getInputStream()
		def testResults = new XmlSlurper().parse(inputStream)

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

        return averageResponseTimes

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

    Long getStartTimeForTest(def testResults) {
		Long currentEarliestTimeStamp = testResults.httpSample[0].@ts.text()

		testResults.httpSample.each { def sample ->
			Long timeStamp = Long.parseLong(sample.@ts.text())
			if(timeStamp < currentEarliestTimeStamp)
			currentEarliestTimeStamp = timeStamp
		}

		return currentEarliestTimeStamp

	}

	Long getEndTimeForTest(def testResults) {
		Long currentLatestTimeStamp = 0

		testResults.httpSample.each { def sample ->
			Long timeStamp = Long.parseLong(sample.@ts.text())
			if(timeStamp > currentLatestTimeStamp)
			currentLatestTimeStamp = timeStamp
		}

		return currentLatestTimeStamp
	}
}
