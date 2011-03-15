package jmetervizualiser

import grails.test.*
import org.apache.commons.vfs.FileObject
import org.gmock.WithGMock

@WithGMock
class DataServiceTests extends GrailsUnitTestCase {

    DataService dataService

    static final String SAMPLE_XML = """
    <?xml version="1.0" encoding="UTF-8"?>
    <testResults version="1.2">
<httpSample t="295" lt="289" ts="1299948248367" s="true" lb="HTTP Request" rc="200" rm="OK" tn="Thread Group 1-5" dt="text" by="3639">
  <assertionResult>
    <name>Response Assertion</name>
    <failure>false</failure>
    <error>false</error>
  </assertionResult>
</httpSample>
<httpSample t="272" lt="268" ts="1299948248371" s="true" lb="HTTP Request" rc="200" rm="OK" tn="Thread Group 1-8" dt="text" by="3639">
  <assertionResult>
    <name>Response Assertion</name>
    <failure>false</failure>
    <error>false</error>
  </assertionResult>
</httpSample>
<httpSample t="267" lt="264" ts="1299948248473" s="true" lb="HTTP Request" rc="200" rm="OK" tn="Thread Group 1-6" dt="text" by="3639">
  <assertionResult>
    <name>Response Assertion</name>
    <failure>false</failure>
    <error>false</error>
  </assertionResult>
</httpSample>

</testResults>

    """
    protected void setUp() {
        super.setUp()

        dataService = new DataService()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testGetAverageResponseTimeOverTime() {
        FileObject file = mock(FileObject)
        InputStream stream = mock(InputStream)
        stream.getText().returns(SAMPLE_XML)

        play {
            dataService.getAverageResponseTimeOverTime(file)
        }
    }
}
