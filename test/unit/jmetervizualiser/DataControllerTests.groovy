package jmetervizualiser

import grails.test.*
import org.gmock.WithGMock
import org.apache.commons.vfs.FileObject

@WithGMock
class DataControllerTests extends ControllerUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void test__AverageResponseTimeOverTime__ForNonExistentFile() {

        mockParams.filename = 'nonsenseFile'

        FileObject nonExistentFile = mock(FileObject)
        nonExistentFile.exists().returns(false)
        mock(controller).getFile('gae://users/anonymous/nonsenseFile').returns(nonExistentFile)

        play {
            controller.averageResponseTimeOverTime()
        }

        assertEquals "File doesn't exist", renderArgs.text

    }

    void test__AverageResponseTimeOverTime__ForFileWhichExists() {

        mockParams.filename = 'nonsenseFile'

        FileObject validFile = mock(FileObject)
        validFile.exists().returns(true)
        mock(controller).getFile('gae://users/anonymous/nonsenseFile').returns(validFile)

        DataService dataService = mock(DataService)
        dataService.getAverageResponseTimeOverTime(validFile).returns([0.3, 0.1, 0.2])
        controller.dataService = dataService

        play {
            controller.averageResponseTimeOverTime()
        }

        assertEquals '{"data":[0.3,0.1,0.2]}', controller.response.contentAsString

    }
}
