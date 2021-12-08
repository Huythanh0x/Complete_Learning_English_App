package com.batdaulaptrinh.completlearningenglishapp.model

class UserInfo(
    val fullName: String,
    val phoneNumber: String,
    val email: String,
    val location: String,
    val joinedDate: String,
){
    override fun toString(): String {
        return "$fullName $phoneNumber $email $location $joinedDate"
    }
}