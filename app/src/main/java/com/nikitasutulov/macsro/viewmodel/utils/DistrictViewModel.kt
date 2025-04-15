package com.nikitasutulov.macsro.viewmodel.utils

import com.nikitasutulov.macsro.data.dto.utils.district.CreateDistrictDto
import com.nikitasutulov.macsro.data.dto.utils.district.DistrictDto
import com.nikitasutulov.macsro.repository.DistrictRepository
import com.nikitasutulov.macsro.viewmodel.CrudViewModel

class DistrictViewModel(repository: DistrictRepository) :
    CrudViewModel<DistrictDto, CreateDistrictDto>(repository)