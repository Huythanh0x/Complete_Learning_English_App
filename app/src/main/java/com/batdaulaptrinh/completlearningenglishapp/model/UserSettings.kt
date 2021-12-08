package com.batdaulaptrinh.completlearningenglishapp.model

class UserSettings(val preferAccent: String,val isDarkMode: Boolean){
    override fun toString(): String {
        return "$preferAccent $isDarkMode"
    }
}