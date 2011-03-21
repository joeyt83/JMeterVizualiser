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

        controller.resultsFile = 'nonsenseFile'

        FileObject nonExistentFile = mock(FileObject)
        nonExistentFile.exists().returns(false)
        mock(controller).getFile('gae://users/anonymous/nonsenseFile').returns(nonExistentFile)

        play {
            controller.averageResponseTimeOverTime()
        }

        assertEquals "File doesn't exist", renderArgs.text

    }

    void test__AverageResponseTimeOverTime__ForFileWhichExists() {

        controller.resultsFile = 'validFile'

        FileObject validFile = mock(FileObject)
        validFile.exists().returns(true)
        mock(controller).getFile('gae://users/anonymous/validFile').returns(validFile)

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
        controller.request.resultsFile = 'hello'

        controller.beforeInterceptor()

        assertEquals('hello', controller.resultsFile)
    }
}
