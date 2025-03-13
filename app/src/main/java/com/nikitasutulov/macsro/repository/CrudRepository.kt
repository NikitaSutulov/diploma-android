package com.nikitasutulov.macsro.repository

import com.nikitasutulov.macsro.data.remote.api.CrudApi
import okhttp3.ResponseBody
import retrofit2.Response

abstract class CrudRepository<EntityDto, CreateEntityDto>(private val api: CrudApi<EntityDto, CreateEntityDto>): Repository() {
    suspend fun getAll(pageNumber: Int?, pageSize: Int?): Response<List<EntityDto>> = api.getAll(pageNumber, pageSize)
    suspend fun create(createEntityDto: CreateEntityDto): Response<EntityDto> = api.create(createEntityDto)
    suspend fun edit(entityDto: EntityDto): Response<EntityDto> = api.edit(entityDto)
    suspend fun getByGID(gid: String): Response<EntityDto> = api.getByGID(gid)
    suspend fun deleteByGID(gid: String): Response<ResponseBody> = api.deleteByGID(gid)
}