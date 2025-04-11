package com.nikitasutulov.macsro.repository

import com.nikitasutulov.macsro.data.dto.volunteer.volunteer.CreateVolunteerDto
import com.nikitasutulov.macsro.data.dto.volunteer.volunteer.VolunteerDto
import com.nikitasutulov.macsro.data.dto.volunteer.volunteersdistricts.CreateVolunteersDistrictsDto
import com.nikitasutulov.macsro.data.dto.volunteer.volunteersdistricts.VolunteersDistrictsDto
import com.nikitasutulov.macsro.data.dto.volunteer.volunteersgroups.CreateVolunteersGroupsDto
import com.nikitasutulov.macsro.data.dto.volunteer.volunteersgroups.VolunteersGroupsDto
import com.nikitasutulov.macsro.data.remote.RetrofitClient

class VolunteerRepository :
    CrudRepository<VolunteerDto, CreateVolunteerDto>(RetrofitClient.volunteerApi)

class VolunteersDistrictsRepository :
    CrudRepository<VolunteersDistrictsDto, CreateVolunteersDistrictsDto>(RetrofitClient.volunteersDistrictsApi)

class VolunteersGroupsRepository :
    CrudRepository<VolunteersGroupsDto, CreateVolunteersGroupsDto>(RetrofitClient.volunteersGroupsApi)
