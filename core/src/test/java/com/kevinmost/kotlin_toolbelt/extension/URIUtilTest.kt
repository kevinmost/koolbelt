package com.kevinmost.kotlin_toolbelt.extension

import org.junit.Assert.assertEquals
import org.junit.Test
import java.net.URI

class URIUtilTest {
  @Test fun `test read to byte array`() {
    val uri = URI.create(
        "https://upload.wikimedia.org/wikipedia/commons/d/d9/Collage_of_Nine_Dogs.jpg")
    val bytes = uri.readToByteArray()
    assertEquals(2286651, bytes.size)
  }
}
