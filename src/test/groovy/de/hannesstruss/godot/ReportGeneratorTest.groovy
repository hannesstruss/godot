package de.hannesstruss.godot

import org.joda.time.DateTime
import org.junit.Test

import static org.mockito.Mockito.mock

class ReportGeneratorTest {
  @Test
  public void shouldNotFailWithOnlyVeryOldData() {
    def generator = new ReportGenerator()

    def records = [new LogRecord(new DateTime(2010, 1, 1, 1, 1, 1), ["someTask"], 12d, true)]

    generator.generate(mock(Writer), records, "test")
  }

}
