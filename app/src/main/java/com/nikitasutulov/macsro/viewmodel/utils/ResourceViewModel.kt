package com.nikitasutulov.macsro.viewmodel.utils

import com.nikitasutulov.macsro.data.dto.utils.resource.CreateResourceDto
import com.nikitasutulov.macsro.data.dto.utils.resource.ResourceDto
import com.nikitasutulov.macsro.repository.ResourceRepository
import com.nikitasutulov.macsro.viewmodel.CrudViewModel

class ResourceViewModel(repository: ResourceRepository) :
    CrudViewModel<ResourceDto, CreateResourceDto>(repository)