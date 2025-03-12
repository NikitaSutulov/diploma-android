package com.nikitasutulov.macsro.repository.operations

import com.nikitasutulov.macsro.data.dto.operations.group.CreateGroupDto
import com.nikitasutulov.macsro.data.dto.operations.group.GroupDto
import com.nikitasutulov.macsro.data.remote.RetrofitClient

class GroupRepository {
    private val groupApi = RetrofitClient.groupApi

    suspend fun getAll(pageNumber: Int?, pageSize: Int?) = groupApi.getAllGroups(pageNumber, pageSize)

    suspend fun create(createGroupDto: CreateGroupDto) = groupApi.createGroup(createGroupDto)

    suspend fun edit(groupDto: GroupDto) = groupApi.editGroup(groupDto)

    suspend fun getByGID(gid: String) = groupApi.getGroupByGID(gid)

    suspend fun deleteByGID(gid: String) = groupApi.deleteGroupByGID(gid)
}