package com.nikitasutulov.macsro.data.remote.api

import okhttp3.ResponseBody
import retrofit2.Response

interface CrudApi <EntityDto, CreateEntityDto> {
    suspend fun getAll(token: String, pageNumber: Int?, pageSize: Int?): Response<List<EntityDto>>
    suspend fun create(token: String, createDto: CreateEntityDto): Response<EntityDto>
    suspend fun edit(token: String, dto: EntityDto): Response<EntityDto>
    suspend fun getByGID(token: String, gid: String): Response<EntityDto>
    suspend fun deleteByGID(token: String, gid: String): Response<ResponseBody>
}