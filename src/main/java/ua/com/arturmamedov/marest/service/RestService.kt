package ua.com.arturmamedov.marest.service

import ua.com.arturmamedov.marest.entities.Request
import ua.com.arturmamedov.marest.util.SimpleRestRequest
import com.intellij.openapi.components.ServiceManager
import ua.com.arturmamedov.marest.entities.Response
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class RestService{


    fun send(request: Request) :Response {
        val qstr = request.queryParams
            .map { entry-> entry.key+"="+URLEncoder.encode(entry.value, StandardCharsets.UTF_8.toString()) }
            .joinToString("&")

        var url = if(qstr.length>0) "${request.url}?$qstr" else request.url


        val restRequest = SimpleRestRequest(
            qstr,
            request.method,
            request.headers,
            request.body.toByteArray()
        )

        return restRequest.send();
    }


    val instance: RestService get(){
            return ServiceManager.getService(RestService::class.java)
    }


}
