package com.nikitasutulov.macsro.repository

import com.nikitasutulov.macsro.data.dto.volunteer.volunteer.CreateVolunteerDto
import com.nikitasutulov.macsro.data.dto.volunteer.volunteer.VolunteerDto
import com.nikitasutulov.macsro.data.dto.volunteer.volunteersdistricts.CreateVolunteersDistrictsDto
import com.nikitasutulov.macsro.data.dto.volunteer.volunteersdistricts.VolunteersDistrictsDto
import com.nikitasutulov.macsro.data.dto.volunteer.volunteersgroups.CreateVolunteersGroupsDto
import com.nikitasutulov.macsro.data.dto.volunteer.volunteersgroups.VolunteersGroupsDto
import com.nikitasutulov.macsro.data.remote.RetrofitClient
import okhttp3.ResponseBody
import retrofit2.Response

class VolunteerRepository :
    CrudRepository<VolunteerDto, CreateVolunteerDto>(RetrofitClient.volunteerApi)

class VolunteersDistrictsRepository : Repository() {
    val api = RetrofitClient.volunteersDistrictsApi
    suspend fun getAll(
        token: String,
        pageNumber: Int?,
        pageSize: Int?
    ): Response<List<VolunteersDistrictsDto>> = api.getAll(token, pageNumber, pageSize)

    suspend fun getByVolunteerGID(
        token: String,
        volunteerGID: String
    ): Response<List<VolunteersDistrictsDto>> = api.getByVolunteerGID(token, volunteerGID)

    suspend fun getByDistrictGID(
        token: String,
        districtGID: String
    ): Response<List<VolunteersDistrictsDto>> = api.getByDistrictGID(token, districtGID)

    suspend fun getByGID(token: String, gid: String): Response<VolunteersDistrictsDto> =
        api.getByGID(token, gid)

    suspend fun deleteByGID(token: String, gid: String): Response<ResponseBody> =
        api.deleteByGID(token, gid)

    suspend fun exists(token: String, dto: CreateVolunteersDistrictsDto): Response<ResponseBody> =
        api.exists(token, dto)

    suspend fun create(
        token: String,
        createEntityDto: CreateVolunteersDistrictsDto
    ): Response<VolunteersDistrictsDto> = api.create(token, createEntityDto)

}

class VolunteersGroupsRepository : Repository() {
    val api = RetrofitClient.volunteersGroupsApi
    suspend fun getAll(
        token: String,
        pageNumber: Int?,
        pageSize: Int?
    ): Response<List<VolunteersGroupsDto>> = api.getAll(token, pageNumber, pageSize)

    suspend fun getByVolunteerGID(
        token: String,
        volunteerGID: String
    ): Response<List<VolunteersGroupsDto>> = api.getByVolunteerGID(token, volunteerGID)

    suspend fun getByGroupGID(
        token: String,
        districtGID: String
    ): Response<List<VolunteersGroupsDto>> = api.getByGroupGID(token, districtGID)

    suspend fun getByGID(token: String, gid: String): Response<VolunteersGroupsDto> =
        api.getByGID(token, gid)

    suspend fun deleteByGID(token: String, gid: String): Response<ResponseBody> =
        api.deleteByGID(token, gid)

    suspend fun exists(token: String, dto: CreateVolunteersGroupsDto): Response<ResponseBody> =
        api.exists(token, dto)

    suspend fun create(
        token: String,
        createEntityDto: CreateVolunteersGroupsDto
    ): Response<VolunteersGroupsDto> = api.create(token, createEntityDto)
}
