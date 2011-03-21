package jmetervizualiser

import grails.converters.JSON
import org.apache.commons.vfs.FileObject
import jmetervizualiser.visualisation.TimeSeriesDataSeries

class DataController extends GAEVfsAwareController {

    String resultsFile

	DataService dataService = new DataService()

    static defaultAction = "averageResponseTimeOverTime"

    def beforeInterceptor = {
        resultsFile = request.resultsFile
    }

	def averageResponseTimeOverTime = {

        FileObject resultsFile = getFile("gae://users/anonymous/${resultsFile}")
        if(!resultsFile.exists()) {
			return render(text: "File doesn't exist")
		}

		TimeSeriesDataSeries responseTimesSeries = dataService.getAverageResponseTimeOverTime(resultsFile)

		render(responseTimesSeries.getAsJSON() as JSON)

	}
}
