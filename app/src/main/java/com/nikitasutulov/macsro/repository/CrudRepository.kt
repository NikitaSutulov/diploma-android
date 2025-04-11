package com.nikitasutulov.macsro.repository

import com.nikitasutulov.macsro.data.remote.api.CrudApi
import okhttp3.ResponseBody
import retrofit2.Response

abstract class CrudRepository<EntityDto, CreateEntityDto>
    (private val api: CrudApi<EntityDto, CreateEntityDto>) : Repository() {
    suspend fun getAll(token: String, pageNumber: Int?, pageSize: Int?): Response<List<EntityDto>> =
        api.getAll(token, pageNumber, pageSize)

    suspend fun create(token: String, createEntityDto: CreateEntityDto): Response<EntityDto> =
        api.create(token, createEntityDto)

    suspend fun edit(token: String, entityDto: EntityDto): Response<EntityDto> =
        api.edit(token, entityDto)

    suspend fun getByGID(token: String, gid: String): Response<EntityDto> = api.getByGID(token, gid)
    suspend fun deleteByGID(token: String, gid: String): Response<ResponseBody> =
        api.deleteByGID(token, gid)
}
