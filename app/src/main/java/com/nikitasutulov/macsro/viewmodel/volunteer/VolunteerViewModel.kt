package com.nikitasutulov.macsro.viewmodel.volunteer

import com.nikitasutulov.macsro.data.dto.volunteer.volunteer.CreateVolunteerDto
import com.nikitasutulov.macsro.data.dto.volunteer.volunteer.VolunteerDto
import com.nikitasutulov.macsro.repository.VolunteerRepository
import com.nikitasutulov.macsro.viewmodel.CrudViewModel

class VolunteerViewModel(repository: VolunteerRepository) :
    CrudViewModel<VolunteerDto, CreateVolunteerDto>(repository)