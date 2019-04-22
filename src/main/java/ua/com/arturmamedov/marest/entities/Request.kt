package ua.com.arturmamedov.marest.entities

data class Request(
    var name: String,
    var method:String,
    var url:String,
    var bodyType:HttpBodyType,
    var body: String,
    var headers:Map<String,String>,
    var queryParams:Map<String,String>
);
