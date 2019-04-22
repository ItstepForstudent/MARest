package ua.com.arturmamedov.marest.entities

import com.google.gson.Gson


data class RestRequestList(
    var requests:MutableMap<String,MutableList<Request>> = HashMap()
){
    public fun toJSON(): String? {
        val gson = Gson();
        return gson.toJson(this);
    }
    companion object {
        public fun fromJson(json:String):RestRequestList{
            val gson = Gson();
            return gson.fromJson(json,RestRequestList::class.java)
        }
    }
}
