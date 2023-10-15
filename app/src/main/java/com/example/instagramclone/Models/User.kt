package com.example.instagramclone.Models

class User {
    var mobileEmail: String? = null
    var name: String? = null
    var username: String? = null
    var password: String? = null
    constructor()
    constructor(mobileEmail: String?, name: String?, username: String?, password: String?) {
        this.mobileEmail = mobileEmail
        this.name = name
        this.username = username
        this.password = password
    }

    constructor(mobileEmail: String?, password: String?) {
        this.mobileEmail = mobileEmail
        this.password = password
    }


}