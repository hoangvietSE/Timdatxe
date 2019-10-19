package com.example.anothertimdatxe.request

class UserCreatePostRequest {
    var title: String? = ""
    var slug: String? = ""
    var description: String? = ""
    var lat_from: String? = ""
    var lng_from: String? = ""
    var lat_to: String? = ""
    var lng_to: String? = ""
    var waypoints: String? = ""
    var number_seat: Int? = 0
    var duration_time: Int? = 0
    var distance: Double? = 0.0
    var start_point: String? = ""
    var end_point: String? = ""
    var start_time: String? = ""
}