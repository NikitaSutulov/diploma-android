package com.nikitasutulov.macsro.data.remote.api.volunteer

import com.nikitasutulov.macsro.data.dto.volunteer.volunteersgroups.CreateVolunteersGroupsDto
import com.nikitasutulov.macsro.data.dto.volunteer.volunteersgroups.VolunteersGroupsDto
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

interface VolunteersGroupsApi: CrudApi<VolunteersGroupsDto, CreateVolunteersGroupsDto> {
    @GET("/volunteer/api/VolunteersGroups")
    override suspend fun getAll(
        @Query("PageNumber") pageNumber: Int?,
        @Query("PageSize") pageSize: Int?
    ): Response<List<VolunteersGroupsDto>>

    @POST("/volunteer/api/VolunteersGroups")
    override suspend fun create(@Body createDto: CreateVolunteersGroupsDto): Response<VolunteersGroupsDto>

    @PUT("/volunteer/api/VolunteersGroups")
    override suspend fun edit(@Body dto: VolunteersGroupsDto): Response<VolunteersGroupsDto>

    @GET("/volunteer/api/VolunteersGroups/{gid}")
    override suspend fun getByGID(@Path("gid") gid: String): Response<VolunteersGroupsDto>

    @DELETE("/volunteer/api/VolunteersGroups/{gid}")
    override suspend fun deleteByGID(@Path("gid") gid: String): Response<ResponseBody>
}
