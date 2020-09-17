package com.sermage.mymoviecollection.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    private val gson = Gson()

    @TypeConverter
    fun intListToString(list:List<Int>): String {
        return gson.toJson(list)
    }


    @TypeConverter
    fun stringToIntList(listAsString: String): List<Int> {
        return gson.fromJson(listAsString, object : TypeToken<List<Int>>() {}.type)
    }

    @TypeConverter
    fun stringListToString(list:List<String>): String {
        return gson.toJson(list)
    }


    @TypeConverter
    fun stringToStringList(listAsString: String): List<String> {
        return gson.fromJson(listAsString, object : TypeToken<List<String>>() {}.type)
    }
}
