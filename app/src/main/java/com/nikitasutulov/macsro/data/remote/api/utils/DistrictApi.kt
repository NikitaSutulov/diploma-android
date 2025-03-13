package com.nikitasutulov.macsro.data.remote.api.utils

import com.nikitasutulov.macsro.data.dto.utils.district.CreateDistrictDto
import com.nikitasutulov.macsro.data.dto.utils.district.DistrictDto
import com.nikitasutulov.macsro.data.remote.api.CrudApi
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface DistrictApi: CrudApi<DistrictDto, CreateDistrictDto> {
    @GET("/utils/api/District")
    override suspend fun getAll(
        @Query("PageNumber") pageNumber: Int?,
        @Query("PageSize") pageSize: Int?
    ): Response<List<DistrictDto>>

    @POST("/utils/api/District")
    override suspend fun create(@Body createDto: CreateDistrictDto): Response<DistrictDto>

    @PUT("/utils/api/District")
    override suspend fun edit(@Body dto: DistrictDto): Response<DistrictDto>

    @GET("/utils/api/District/{gid}")
    override suspend fun getByGID(@Path("gid") gid: String): Response<DistrictDto>

    @DELETE("/utils/api/District/{gid}")
    override suspend fun deleteByGID(@Path("gid") gid: String): Response<ResponseBody>
}