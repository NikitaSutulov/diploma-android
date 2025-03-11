package com.nikitasutulov.macsro.data.remote.api.utils

import com.nikitasutulov.macsro.data.dto.utils.district.CreateDistrictDto
import com.nikitasutulov.macsro.data.dto.utils.district.DistrictDto
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface DistrictApi {
    @GET("/utils/api/District")
    suspend fun getAllDistricts(
        @Query("PageNumber") pageNumber: Int?,
        @Query("PageSize") pageSize: Int?
    ): Response<List<DistrictDto>>

    @POST("/utils/api/District")
    suspend fun createDistrict(@Body createDistrictDto: CreateDistrictDto): Response<DistrictDto>

    @PUT("/utils/api/District")
    suspend fun editDistrict(@Body districtDto: DistrictDto): Response<DistrictDto>

    @GET("/utils/api/District/{gid}")
    suspend fun getDistrictByGID(@Path("gid") gid: String): Response<DistrictDto>

    @DELETE("/utils/api/District/{gid}")
    suspend fun deleteDistrictByGID(@Path("gid") gid: String): Response<ResponseBody>
}