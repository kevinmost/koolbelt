package com.kevinmost.koolbelt.extension

import com.kevinmost.koolbelt.extension.ReadURIResult.Failure
import com.kevinmost.koolbelt.extension.ReadURIResult.Success
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Test
import java.net.URI

class URIUtilTest {
  @Test fun `test read to byte array`() {
    val uri = URI.create(
        "https://upload.wikimedia.org/wikipedia/commons/d/d9/Collage_of_Nine_Dogs.jpg")

    val result = uri.readToByteArray()
    when (result) {
      is Success -> {
        assertEquals(2286651, result.bytes.size)
      }
      is Failure -> {
        Assert.fail("Error reading URI ${result.uri}to byte array. Failure details: ${result.err}")
      }
    }
  }
}
