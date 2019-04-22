package ua.com.arturmamedov.marest.service

import org.junit.Test
import ua.com.arturmamedov.marest.entities.HttpBodyType
import ua.com.arturmamedov.marest.entities.Request


internal class RestServiceTest {



    @Test
    fun send() {
        val restService  = RestService();

        val request = Request(
            "trulala",
            "GET",
            "https://google.com",
            HttpBodyType.PLAIN,
            ByteArray (0),
            HashMap(),
            HashMap()
        );


        val resp = restService.send(request);

        println(resp)

    }
}
