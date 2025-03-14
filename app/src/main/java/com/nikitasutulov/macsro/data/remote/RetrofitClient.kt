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
    private const val AUTH_BASE_URL = "http://192.168.0.105:5107"
    private const val UTILS_BASE_URL = "http://192.168.0.105:5031"
    private const val OPERATIONS_BASE_URL = "http://192.168.0.105:5160"
    private const val VOLUNTEER_BASE_URL = "http://192.168.0.105:5083"

    private val gsonConverterFactory = GsonConverterFactory.create()

    private fun createRetrofit(baseUrl: String): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(gsonConverterFactory)
            .build()

    private val authRetrofitClient: Retrofit by lazy { createRetrofit(AUTH_BASE_URL) }
    private val utilsRetrofitClient: Retrofit by lazy { createRetrofit(UTILS_BASE_URL) }
    private val operationsRetrofitClient: Retrofit by lazy { createRetrofit(OPERATIONS_BASE_URL) }
    private val volunteerRetrofitClient: Retrofit by lazy { createRetrofit(VOLUNTEER_BASE_URL) }

    val authApi: AuthApi by lazy { authRetrofitClient.create(AuthApi::class.java) }
    val roleApi: RoleApi by lazy { authRetrofitClient.create(RoleApi::class.java) }
    val userApi: UserApi by lazy { authRetrofitClient.create(UserApi::class.java) }

    val districtApi: DistrictApi by lazy { utilsRetrofitClient.create(DistrictApi::class.java) }
    val measurementUnitApi: MeasurementUnitApi by lazy { utilsRetrofitClient.create(MeasurementUnitApi::class.java) }
    val resourceApi: ResourceApi by lazy { utilsRetrofitClient.create(ResourceApi::class.java) }
    val resourceMeasurementUnitApi: ResourceMeasurementUnitApi by lazy { utilsRetrofitClient.create(ResourceMeasurementUnitApi::class.java) }

    val eventApi: EventApi by lazy { operationsRetrofitClient.create(EventApi::class.java) }
    val eventStatusApi: EventStatusApi by lazy { operationsRetrofitClient.create(EventStatusApi::class.java) }
    val eventTypeApi: EventTypeApi by lazy { operationsRetrofitClient.create(EventTypeApi::class.java) }
    val groupApi: GroupApi by lazy { operationsRetrofitClient.create(GroupApi::class.java) }
    val operationTaskApi: OperationTaskApi by lazy { operationsRetrofitClient.create(OperationTaskApi::class.java) }
    val operationTaskStatusApi: OperationTaskStatusApi by lazy { operationsRetrofitClient.create(OperationTaskStatusApi::class.java) }
    val operationWorkerApi: OperationWorkerApi by lazy { operationsRetrofitClient.create(OperationWorkerApi::class.java) }
    val resourcesEventApi: ResourcesEventApi by lazy { operationsRetrofitClient.create(ResourcesEventApi::class.java) }

    val volunteerApi: VolunteerApi by lazy { volunteerRetrofitClient.create(VolunteerApi::class.java) }
    val volunteersDistrictsApi: VolunteersDistrictsApi by lazy { volunteerRetrofitClient.create(VolunteersDistrictsApi::class.java) }
    val volunteersGroupsApi: VolunteersGroupsApi by lazy { volunteerRetrofitClient.create(VolunteersGroupsApi::class.java) }
}
