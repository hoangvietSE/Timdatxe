package com.example.anothertimdatxe.request
import com.google.gson.annotations.SerializedName

class UserBookingRequest{
	@SerializedName("note")
	var note: String? = null

	@SerializedName("number_seat")
	var numberSeat: Int? = null

	@SerializedName("driver_id")
	var driverId: Int? = null

	@SerializedName("lat_to")
	var latTo: String? = null

	@SerializedName("distance")
	var distance: Double? = null

	@SerializedName("total_price")
	var totalPrice: String? = null

	@SerializedName("end_point")
	var endPoint: String? = null

	@SerializedName("empty_seat")
	var emptySeat: Int? = null

	@SerializedName("type")
	var type: Int? = null

	@SerializedName("waypoints")
	var waypoints: String? = null

	@SerializedName("lat_from")
	var latFrom: String? = null

	@SerializedName("book_time")
	var bookTime: String? = null

	@SerializedName("full_name")
	var fullName: String? = null

	@SerializedName("phone")
	var phone: String? = null

	@SerializedName("user_id")
	var userId: Int? = null

	@SerializedName("can_book")
	var canBook: Int? = null

	@SerializedName("price")
	var price: String? = null

	@SerializedName("start_point")
	var startPoint: String? = null

	@SerializedName("driver_post_id")
	var driverPostId: Int? = null

	@SerializedName("lng_to")
	var lngTo: String? = null

	@SerializedName("email")
	var email: String? = null

	@SerializedName("lng_from")
	var lngFrom: String? = null
}