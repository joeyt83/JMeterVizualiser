package jmetervizualiser

import grails.converters.JSON
import org.apache.commons.vfs.FileObject

class DataController extends GAEVfsAwareController {

	DataService dataService = new DataService()

    def index = {
		println "redirecting to next action"
		redirect(action: averageResponseTimeOverTime)
	}

	def averageResponseTimeOverTime = {

		int timeInterval = Integer.parseInt(params.timeinterval)
        FileObject resultsFile = getFile("gae://users/anonymous/${params.filename}")
        if(!resultsFile.exists()) {
			return render(text: "File doesn't exist")
		}
		//return render(text: "${resultsFile.name.baseName}...${resultsFile.getContent().getSize()}.............")

		List<Double> responseTimes = dataService.getAverageResponseTimeOverTime(resultsFile, timeInterval)

		render(responseTimes as JSON)

	}
}
