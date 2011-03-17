package jmetervizualiser

import grails.test.*
import org.apache.commons.vfs.FileObject
import org.gmock.WithGMock
import org.apache.commons.vfs.FileContent

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

        Map<Long, Double> data = [:]
        play {
            data = dataService.getAverageResponseTimeOverTime(file)
        }

        assertEquals([284, 267], data)
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
