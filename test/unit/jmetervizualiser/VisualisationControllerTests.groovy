package jmetervizualiser

import grails.test.*
import javax.servlet.http.Cookie

class VisualisationControllerTests extends ControllerUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testIndexSetsResultsFileCookie() {
        mockParams.resultsfile = 'somefileiwanttolookat'

        controller.index()

        Cookie resultsFileCookie = controller.response.getCookie('resultsfile')

        assertEquals 'somefileiwanttolookat', resultsFileCookie.getValue()
    }
}
