package com.sermage.mymoviecollection.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    private val gson = Gson()

    @TypeConverter
    fun listToString(list:List<Int>): String {
        return gson.toJson(list)
    }


    @TypeConverter
    fun stringToList(listAsString: String): List<Int> {
        return gson.fromJson(listAsString, object : TypeToken<List<Int>>() {}.type)
    }
}
