import java.nio.file.Path
import java.nio.file.Paths
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

/**
 * A proposal to a question raised at
 * https://forum.katalon.com/t/advise-on-how-to-handle-timestamp-difference/68652
 */

// open the web page
Path projectDir = Paths.get(RunConfiguration.getProjectDir())
Path html = projectDir.resolve("page.html")
URL htmlUrl = html.toFile().toURI().toURL()
WebUI.openBrowser(htmlUrl.toExternalForm())

// get the string of timestamp
TestObject tObj = makeTestObject("//*[@id='clock']")
WebUI.verifyElementPresent(tObj, 10)
String ts = WebUI.getText(tObj)
println("displayed value is: " + ts)   // in UTC

// convert the timestamp string into a LocalDateTime object
// see https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html
// see https://www.baeldung.com/java-datetimeformatter
DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d' T 'H:m:s.SSS'Z'")
LocalDateTime parsedTimestamp = LocalDateTime.parse(ts, formatter)

// intentionally insert a delay for debugging
WebUI.delay(3)

// get the current timestamp that this test script recognizes
LocalDateTime currentTimestamp = LocalDateTime.now(ZoneOffset.UTC)
println("current timestamp is: " + currentTimestamp.format(formatter))

// calculrate the difference in the seconds unit between the currentTimestamp and the parsedTimestamp
// see https://www.baeldung.com/java-date-difference
long diff = ChronoUnit.SECONDS.between(parsedTimestamp, currentTimestamp);
println("diff is: " + diff)

// make sure the difference is less than 10 secodns
assert diff < 10

// close the page
WebUI.closeBrowser()

/**
 * function that creates a TestObject using an XPath expression
 */
def makeTestObject(String xpath) {
	TestObject tObj = new TestObject(xpath)
	tObj.addProperty("xpath", ConditionType.EQUALS, xpath)
	return tObj
}

