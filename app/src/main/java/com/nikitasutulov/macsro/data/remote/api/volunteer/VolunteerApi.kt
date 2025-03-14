package com.nikitasutulov.macsro.data.remote.api.volunteer

import com.nikitasutulov.macsro.data.dto.volunteer.volunteer.CreateVolunteerDto
import com.nikitasutulov.macsro.data.dto.volunteer.volunteer.VolunteerDto
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

interface VolunteerApi: CrudApi<VolunteerDto, CreateVolunteerDto> {
    @GET("/volunteer/api/Volunteer")
    override suspend fun getAll(
        @Query("PageNumber") pageNumber: Int?,
        @Query("PageSize") pageSize: Int?
    ): Response<List<VolunteerDto>>

    @POST("/volunteer/api/Volunteer")
    override suspend fun create(@Body createDto: CreateVolunteerDto): Response<VolunteerDto>

    @PUT("/volunteer/api/Volunteer")
    override suspend fun edit(@Body dto: VolunteerDto): Response<VolunteerDto>

    @GET("/volunteer/api/Volunteer/{gid}")
    override suspend fun getByGID(@Path("gid") gid: String): Response<VolunteerDto>

    @DELETE("/volunteer/api/Volunteer/{gid}")
    override suspend fun deleteByGID(@Path("gid") gid: String): Response<ResponseBody>
}
