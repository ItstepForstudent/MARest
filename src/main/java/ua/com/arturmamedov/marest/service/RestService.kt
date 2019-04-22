package ua.com.arturmamedov.marest.service

import com.intellij.openapi.components.*
import ua.com.arturmamedov.marest.entities.Request
import ua.com.arturmamedov.marest.util.SimpleRestRequest
import ua.com.arturmamedov.marest.entities.Response
import ua.com.arturmamedov.marest.entities.RestRequestList
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.util.*

@State(name = "MARestState", storages = arrayOf(Storage(file=StoragePathMacros.WORKSPACE_FILE)))
class RestService: PersistentStateComponent<String>{

    private var requestList: RestRequestList = RestRequestList();

    override fun getState(): String? {
        println("GET STATE")
        println(requestList.toJSON())
        return requestList.toJSON();
    }

    override fun loadState(state: String) {
        println(state)
        requestList = RestRequestList.fromJson(state);
    }

    fun send(request: Request) :Response {
        val qstr = request.queryParams
            .map { entry-> entry.key+"="+URLEncoder.encode(entry.value, StandardCharsets.UTF_8.toString()) }
            .joinToString("&")

        val url = if(qstr.length>0) "${request.url}?$qstr" else request.url

        val restRequest = SimpleRestRequest(
            url,
            request.method,
            request.headers,
            request.body.toByteArray()
        )
        return restRequest.send();
    }

    fun addRequest(group:String, request:Request){
        if(requestList.requests[group]==null) requestList.requests.put(group,LinkedList());
        requestList.requests[group]!!.removeIf { it.name == request.name }
        requestList.requests[group]!!.add(request);
    }

    fun getGroups():List<String>{
        return requestList.requests.map { it.key }
    }

    fun getRequests():Map<String,List<Request>>{
        return requestList.requests
    }

    companion object {
        val instance: RestService get(){
            return ServiceManager.getService(RestService::class.java)
        }
    }


}
