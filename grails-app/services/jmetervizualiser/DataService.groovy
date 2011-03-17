package jmetervizualiser

import org.apache.commons.vfs.FileObject

class DataService {

    static transactional = true

    Map<Long, Double> getAverageResponseTimeOverTime(FileObject file) {

		Map<Long, Double> data = [:]

		InputStream inputStream = file.getContent().getInputStream()
		def testResults = new XmlSlurper().parse(inputStream)

		testResults.httpSample.each { def sample ->
            Long timestampInSecs = Long.parseLong(sample.@ts.text()) / 1000
            Double responseTime = Double.parseDouble(sample.@t.text()) / 1000
			if(!data[timestampInSecs]) {
                data.put timestampInSecs, responseTime
            } else {
                data[timestampInSecs] = (data[timestampInSecs] + responseTime) / 2
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
