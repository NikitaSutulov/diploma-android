package com.nikitasutulov.macsro.viewmodel.operations

import com.nikitasutulov.macsro.data.dto.operations.group.CreateGroupDto
import com.nikitasutulov.macsro.data.dto.operations.group.GroupDto
import com.nikitasutulov.macsro.repository.GroupRepository
import com.nikitasutulov.macsro.viewmodel.CrudViewModel

class GroupViewModel(repository: GroupRepository) :
    CrudViewModel<GroupDto, CreateGroupDto>(repository)