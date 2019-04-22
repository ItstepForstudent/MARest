package ua.com.arturmamedov.marest.util

import ua.com.arturmamedov.marest.entities.Response
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.util.*


class SimpleRestRequest (
    val url: String,
    val method: String,
    val headers: Map<String,String> = HashMap(),
    val requestBody:ByteArray? = null
){
    @Throws(IOException::class)
    fun send(): Response {

        val urlUrl = URL(url)

        val connection = urlUrl.openConnection() as HttpURLConnection

        connection.setRequestMethod(method)

        for (h in headers.entries) {
            connection.setRequestProperty(h.key, h.value)
        }

        if (method != "GET" && requestBody != null) {
            connection.setDoOutput(true)
            connection.getOutputStream().use({ outputStream -> outputStream.write(requestBody) })
        }

        val sc = Scanner(connection.getInputStream())
        val response = StringBuilder()

        while (sc.hasNextLine())
            response.append(sc.nextLine())

        return Response(response.toString(),connection.headerFields,connection.responseCode);
    }
}
