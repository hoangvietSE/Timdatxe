package com.example.anothertimdatxe.base.network

import com.example.anothertimdatxe.base.constant.MapApiConstant
import com.example.anothertimdatxe.base.constant.RequestParam
import com.example.anothertimdatxe.map.response.GoogleMapDirectionResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface MapApiService {
    @GET(MapApiConstant.MAP_DIRECTION)
    @Headers("Content-type:application/json")
    fun getDirection(
            @Query(RequestParam.ORIGIN) origin: String,
            @Query(RequestParam.DESTINATION) destination: String,
            @Query(RequestParam.KEY) key: String): Single<Response<GoogleMapDirectionResponse>>

    @GET(MapApiConstant.MAP_DIRECTION)
    @Headers("Content-type:application/json")
    fun getDirection(
            @Query(RequestParam.ORIGIN) origin: String,
            @Query(RequestParam.DESTINATION) destination: String,
            @Query(RequestParam.WAYPOINTS) waypoints: String,
            @Query(RequestParam.KEY) key: String): Single<Response<GoogleMapDirectionResponse>>
}