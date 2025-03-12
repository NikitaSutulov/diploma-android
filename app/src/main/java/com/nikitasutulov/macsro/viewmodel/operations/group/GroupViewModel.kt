package com.nikitasutulov.macsro.viewmodel.operations.group

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nikitasutulov.macsro.data.dto.BaseResponse
import com.nikitasutulov.macsro.data.dto.operations.group.CreateGroupDto
import com.nikitasutulov.macsro.data.dto.operations.group.GroupDto
import com.nikitasutulov.macsro.repository.operations.GroupRepository
import com.nikitasutulov.macsro.util.performRequest
import okhttp3.ResponseBody

class GroupViewModel(private val groupRepository: GroupRepository): ViewModel() {
    private val _getAllGroupsResponse = MutableLiveData<BaseResponse<List<GroupDto>>>()
    val getAllGroupsResponse: LiveData<BaseResponse<List<GroupDto>>> = _getAllGroupsResponse

    private val _createGroupResponse = MutableLiveData<BaseResponse<GroupDto>>()
    val createGroupResponse: LiveData<BaseResponse<GroupDto>> = _createGroupResponse

    private val _editGroupResponse = MutableLiveData<BaseResponse<GroupDto>>()
    val editGroupResponse: LiveData<BaseResponse<GroupDto>> = _editGroupResponse

    private val _getGroupByGIDResponse = MutableLiveData<BaseResponse<GroupDto>>()
    val getGroupByGIDResponse: LiveData<BaseResponse<GroupDto>> = _getGroupByGIDResponse

    private val _deleteGroupByGIDResponse = MutableLiveData<BaseResponse<ResponseBody>>()
    val deleteGroupByGIDResponse: LiveData<BaseResponse<ResponseBody>> = _deleteGroupByGIDResponse

    fun getAllGroups(pageNumber: Int?, pageSize: Int?) {
        performRequest(
            request = { groupRepository.getAll(pageNumber, pageSize) },
            responseLiveData = _getAllGroupsResponse
        )
    }

    fun createGroup(createGroupDto: CreateGroupDto) {
        performRequest(
            request = { groupRepository.create(createGroupDto) },
            responseLiveData = _createGroupResponse
        )
    }

    fun editGroup(groupDto: GroupDto) {
        performRequest(
            request = { groupRepository.edit(groupDto) },
            responseLiveData = _editGroupResponse
        )
    }

    fun getGroupByGID(gid: String) {
        performRequest(
            request = { groupRepository.getByGID(gid) },
            responseLiveData = _getGroupByGIDResponse
        )
    }

    fun deleteGroupByGID(gid: String) {
        performRequest(
            request = { groupRepository.deleteByGID(gid) },
            responseLiveData = _deleteGroupByGIDResponse
        )
    }
}