package com.nikitasutulov.macsro.data.remote.api.volunteer

import com.nikitasutulov.macsro.data.dto.volunteer.volunteersdistricts.CreateVolunteersDistrictsDto
import com.nikitasutulov.macsro.data.dto.volunteer.volunteersdistricts.VolunteersDistrictsDto
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

interface VolunteersDistrictsApi: CrudApi<VolunteersDistrictsDto, CreateVolunteersDistrictsDto> {
    @GET("/volunteer/api/VolunteersDistricts")
    override suspend fun getAll(
        @Query("PageNumber") pageNumber: Int?,
        @Query("PageSize") pageSize: Int?
    ): Response<List<VolunteersDistrictsDto>>

    @POST("/volunteer/api/VolunteersDistricts")
    override suspend fun create(@Body createDto: CreateVolunteersDistrictsDto): Response<VolunteersDistrictsDto>

    @PUT("/volunteer/api/VolunteersDistricts")
    override suspend fun edit(@Body dto: VolunteersDistrictsDto): Response<VolunteersDistrictsDto>

    @GET("/volunteer/api/VolunteersDistricts/{gid}")
    override suspend fun getByGID(@Path("gid") gid: String): Response<VolunteersDistrictsDto>

    @DELETE("/volunteer/api/VolunteersDistricts/{gid}")
    override suspend fun deleteByGID(@Path("gid") gid: String): Response<ResponseBody>
}
