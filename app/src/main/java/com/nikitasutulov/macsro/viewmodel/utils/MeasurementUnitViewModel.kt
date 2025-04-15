package com.nikitasutulov.macsro.viewmodel.utils

import com.nikitasutulov.macsro.data.dto.utils.measurementunit.CreateMeasurementUnitDto
import com.nikitasutulov.macsro.data.dto.utils.measurementunit.MeasurementUnitDto
import com.nikitasutulov.macsro.repository.MeasurementUnitRepository
import com.nikitasutulov.macsro.viewmodel.CrudViewModel

class MeasurementUnitViewModel(repository: MeasurementUnitRepository) :
    CrudViewModel<MeasurementUnitDto, CreateMeasurementUnitDto>(repository)