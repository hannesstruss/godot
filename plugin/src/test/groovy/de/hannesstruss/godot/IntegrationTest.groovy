package de.hannesstruss.godot

import de.hannesstruss.godot.reports.Report
import org.apache.commons.io.IOUtils
import org.joda.time.DateTime
import org.junit.Test

class IntegrationTest {
  @Test public void generateFullReport() {
    InputStream dummyInput = getClass().getResource("/testdata/dummy.log").openStream()
    def tempDir = TestDirectories.getTempDir()

    def clock = new FixedClock(new DateTime(2016, 4, 29, 19, 0, 0))
    new Report(clock).generate(new InputStreamReader(dummyInput), tempDir, "Test Project")

    IOUtils.closeQuietly(dummyInput)
  }
}
