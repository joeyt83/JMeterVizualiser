package jmetervizualiser

import grails.converters.JSON
import org.apache.commons.vfs.FileObject

class DataController extends GAEVfsAwareController {

	DataService dataService = new DataService()

    static defaultAction = "averageResponseTimeOverTime"

	def averageResponseTimeOverTime = {

        FileObject resultsFile = getFile("gae://users/anonymous/${params.filename}")
        if(!resultsFile.exists()) {
			return render(text: "File doesn't exist")
		}

		List<Double> responseTimes = dataService.getAverageResponseTimeOverTime(resultsFile)

		render([data: responseTimes] as JSON)

	}
}
