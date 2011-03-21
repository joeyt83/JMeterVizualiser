package jmetervizualiser

import grails.converters.JSON
import org.apache.commons.vfs.FileObject
import jmetervizualiser.visualisation.TimeSeriesDataSeries

class DataController extends GAEVfsAwareController {

    String resultsFileName

	DataService dataService = new DataService()

    static defaultAction = "averageResponseTimeOverTime"

    def beforeInterceptor = {
        resultsFileName = request.resultsFileName
    }

	def averageResponseTimeOverTime = {

        FileObject workingDirectory = getWorkingDirectory()
        FileObject resultsFile = workingDirectory.resolveFile(resultsFileName)
        if(!resultsFile.exists()) {
			return render(text: "File doesn't exist")
		}

		TimeSeriesDataSeries responseTimesSeries = dataService.getAverageResponseTimeOverTime(resultsFile)

		render(responseTimesSeries.getAsJSON() as JSON)

	}
}
