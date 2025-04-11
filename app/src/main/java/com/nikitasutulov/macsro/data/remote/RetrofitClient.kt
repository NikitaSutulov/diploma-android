package com.nikitasutulov.macsro.data.remote

import com.nikitasutulov.macsro.data.remote.api.auth.*
import com.nikitasutulov.macsro.data.remote.api.utils.*
import com.nikitasutulov.macsro.data.remote.api.operations.*
import com.nikitasutulov.macsro.data.remote.api.volunteer.VolunteerApi
import com.nikitasutulov.macsro.data.remote.api.volunteer.VolunteersDistrictsApi
import com.nikitasutulov.macsro.data.remote.api.volunteer.VolunteersGroupsApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://192.168.0.105:5065/gateway.integration.api/"

    private val gsonConverterFactory = GsonConverterFactory.create()

    private val retrofitClient: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    val authApi: AuthApi by lazy { retrofitClient.create(AuthApi::class.java) }
    val roleApi: RoleApi by lazy { retrofitClient.create(RoleApi::class.java) }
    val userApi: UserApi by lazy { retrofitClient.create(UserApi::class.java) }

    val districtApi: DistrictApi by lazy { retrofitClient.create(DistrictApi::class.java) }
    val measurementUnitApi: MeasurementUnitApi by lazy { retrofitClient.create(MeasurementUnitApi::class.java) }
    val resourceApi: ResourceApi by lazy { retrofitClient.create(ResourceApi::class.java) }
    val resourceMeasurementUnitApi: ResourceMeasurementUnitApi by lazy { retrofitClient.create(ResourceMeasurementUnitApi::class.java) }

    val eventApi: EventApi by lazy { retrofitClient.create(EventApi::class.java) }
    val eventStatusApi: EventStatusApi by lazy { retrofitClient.create(EventStatusApi::class.java) }
    val eventTypeApi: EventTypeApi by lazy { retrofitClient.create(EventTypeApi::class.java) }
    val groupApi: GroupApi by lazy { retrofitClient.create(GroupApi::class.java) }
    val operationTaskApi: OperationTaskApi by lazy { retrofitClient.create(OperationTaskApi::class.java) }
    val operationTaskStatusApi: OperationTaskStatusApi by lazy { retrofitClient.create(OperationTaskStatusApi::class.java) }
    val operationWorkerApi: OperationWorkerApi by lazy { retrofitClient.create(OperationWorkerApi::class.java) }
    val resourcesEventApi: ResourcesEventApi by lazy { retrofitClient.create(ResourcesEventApi::class.java) }

    val volunteerApi: VolunteerApi by lazy { retrofitClient.create(VolunteerApi::class.java) }
    val volunteersDistrictsApi: VolunteersDistrictsApi by lazy { retrofitClient.create(VolunteersDistrictsApi::class.java) }
    val volunteersGroupsApi: VolunteersGroupsApi by lazy { retrofitClient.create(VolunteersGroupsApi::class.java) }
}
