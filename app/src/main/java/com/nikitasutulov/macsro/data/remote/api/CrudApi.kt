package com.nikitasutulov.macsro.data.remote.api

import okhttp3.ResponseBody
import retrofit2.Response

interface CrudApi <EntityDto, CreateEntityDto> {
    suspend fun getAll(pageNumber: Int?, pageSize: Int?): Response<List<EntityDto>>
    suspend fun create(createDto: CreateEntityDto): Response<EntityDto>
    suspend fun edit(dto: EntityDto): Response<EntityDto>
    suspend fun getByGID(gid: String): Response<EntityDto>
    suspend fun deleteByGID(gid: String): Response<ResponseBody>
}