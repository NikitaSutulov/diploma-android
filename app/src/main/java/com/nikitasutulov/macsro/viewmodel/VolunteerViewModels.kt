package com.nikitasutulov.macsro.viewmodel

import com.nikitasutulov.macsro.data.dto.volunteer.volunteer.CreateVolunteerDto
import com.nikitasutulov.macsro.data.dto.volunteer.volunteer.VolunteerDto
import com.nikitasutulov.macsro.data.dto.volunteer.volunteersdistricts.CreateVolunteersDistrictsDto
import com.nikitasutulov.macsro.data.dto.volunteer.volunteersdistricts.VolunteersDistrictsDto
import com.nikitasutulov.macsro.data.dto.volunteer.volunteersgroups.CreateVolunteersGroupsDto
import com.nikitasutulov.macsro.data.dto.volunteer.volunteersgroups.VolunteersGroupsDto
import com.nikitasutulov.macsro.repository.VolunteerRepository
import com.nikitasutulov.macsro.repository.VolunteersDistrictsRepository
import com.nikitasutulov.macsro.repository.VolunteersGroupsRepository

class VolunteerViewModel(repository: VolunteerRepository) :
    CrudViewModel<VolunteerDto, CreateVolunteerDto>(repository)

class VolunteersDistrictsViewModel(repository: VolunteersDistrictsRepository) :
    CrudViewModel<VolunteersDistrictsDto, CreateVolunteersDistrictsDto>(repository)

class VolunteersGroupsViewModel(repository: VolunteersGroupsRepository) :
    CrudViewModel<VolunteersGroupsDto, CreateVolunteersGroupsDto>(repository)
