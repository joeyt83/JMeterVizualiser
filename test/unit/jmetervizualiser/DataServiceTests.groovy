package jmetervizualiser

import grails.test.*
import org.apache.commons.vfs.FileObject
import org.gmock.WithGMock
import org.apache.commons.vfs.FileContent
import jmetervizualiser.visualisation.TimeSeriesDataSeries
import org.joda.time.DateTime

@WithGMock
class DataServiceTests extends GrailsUnitTestCase {

    DataService dataService

    static final File SAMPLE_XML = new File('test/data/sample.xml')

    protected void setUp() {
        super.setUp()

        dataService = new DataService()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testGetAverageResponseTimeOverTime() {
        FileObject file = makeMockFileWithContents(SAMPLE_XML)

        TimeSeriesDataSeries dataSeries = []
        play {
            dataSeries = dataService.getAverageResponseTimeOverTime(file)
        }

        assertEquals([279, 267], dataSeries.data)
        assertEquals(1000, dataSeries.pointInterval)
        DateTime expectedStartTime = new DateTime(2011, 03, 12, 16, 44, 8, 343)  // start date for the test data
        assertEquals(expectedStartTime, dataSeries.pointStart)

    }

    private FileObject makeMockFileWithContents(File contents) {
        FileObject file = mock(FileObject)
        FileContent content = mock(FileContent)
        InputStream inputStream = new FileInputStream(contents)
        file.getContent().returns(content)
        content.getInputStream().returns(inputStream)

        return file
    }
}
