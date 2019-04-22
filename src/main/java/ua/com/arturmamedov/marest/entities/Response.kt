package ua.com.arturmamedov.marest.entities

data class Response(
    var body:String,
    var headers: MutableMap<String, MutableList<String>>,
    var status:Int
);
