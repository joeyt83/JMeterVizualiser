package jmetervizualiser

import grails.test.*
import org.gmock.WithGMock
import org.apache.commons.vfs.FileObject
import jmetervizualiser.visualisation.TimeSeriesDataSeries

@WithGMock
class DataControllerTests extends ControllerUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void test__AverageResponseTimeOverTime__ForNonExistentFile() {

        controller.resultsFileName = 'nonsenseFile'

        FileObject workingDirectory = mock(FileObject)

        FileObject nonExistentFile = mock(FileObject)
        workingDirectory.resolveFile('nonsenseFile').returns(nonExistentFile)

        nonExistentFile.exists().returns(false)

        mock(controller).getWorkingDirectory().returns(workingDirectory)

        play {
            controller.averageResponseTimeOverTime()
        }

        assertEquals "File doesn't exist", renderArgs.text

    }

    void test__AverageResponseTimeOverTime__ForFileWhichExists() {

        controller.resultsFileName = 'validFile'

        FileObject workingDirectory = mock(FileObject)

        FileObject validFile = mock(FileObject)
        workingDirectory.resolveFile('validFile').returns(validFile)

        validFile.exists().returns(true)
        
        mock(controller).getWorkingDirectory().returns(workingDirectory)

        DataService dataService = mock(DataService)
        TimeSeriesDataSeries series = mock(TimeSeriesDataSeries)
        series.getAsJSON().returns(["some": "JSON"])
        dataService.getAverageResponseTimeOverTime(validFile).returns(series)
        controller.dataService = dataService

        play {
            controller.averageResponseTimeOverTime()
        }

        assertEquals '{"some":"JSON"}', controller.response.contentAsString

    }

    void test__BeforeInterceptor__AddsResultsFileToController() {
        controller.request.resultsFileName = 'hello'

        controller.beforeInterceptor()

        assertEquals('hello', controller.resultsFileName)
    }
}
