package jmetervizualiser

import org.apache.commons.vfs.FileObject

class DataService {

    static transactional = true

    List getAverageResponseTimeOverTime(FileObject file, int timeInterval) {

		List data = []

		InputStream inputStream = file.getContent().getInputStream()

		def testResults = new XmlSlurper().parse(inputStream)

		testResults.httpSample.each { def sample ->
			//get the correct second
			//update the average response time for that bucket
			data.add(sample.@ts.text())
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
