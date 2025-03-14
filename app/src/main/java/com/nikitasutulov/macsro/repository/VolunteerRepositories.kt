package com.nikitasutulov.macsro.repository

import com.nikitasutulov.macsro.data.dto.volunteer.volunteer.CreateVolunteerDto
import com.nikitasutulov.macsro.data.dto.volunteer.volunteer.VolunteerDto
import com.nikitasutulov.macsro.data.dto.volunteer.volunteersdistricts.CreateVolunteersDistrictsDto
import com.nikitasutulov.macsro.data.dto.volunteer.volunteersdistricts.VolunteersDistrictsDto
import com.nikitasutulov.macsro.data.dto.volunteer.volunteersgroups.CreateVolunteersGroupsDto
import com.nikitasutulov.macsro.data.dto.volunteer.volunteersgroups.VolunteersGroupsDto
import com.nikitasutulov.macsro.data.remote.api.volunteer.VolunteerApi
import com.nikitasutulov.macsro.data.remote.api.volunteer.VolunteersDistrictsApi
import com.nikitasutulov.macsro.data.remote.api.volunteer.VolunteersGroupsApi

class VolunteerRepository(api: VolunteerApi): CrudRepository<VolunteerDto, CreateVolunteerDto>(api)
class VolunteersDistrictsRepository(api: VolunteersDistrictsApi): CrudRepository<VolunteersDistrictsDto, CreateVolunteersDistrictsDto>(api)
class VolunteersGroupsRepository(api: VolunteersGroupsApi): CrudRepository<VolunteersGroupsDto, CreateVolunteersGroupsDto>(api)
