package com.nikitasutulov.macsro.data.remote

import com.nikitasutulov.macsro.data.remote.api.auth.*
import com.nikitasutulov.macsro.data.remote.api.utils.*
import com.nikitasutulov.macsro.data.remote.api.operations.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val AUTH_BASE_URL = "http://192.168.0.105:5107"
    private const val UTILS_BASE_URL = "http://192.168.0.105:5031"
    private const val OPERATIONS_BASE_URL = "http://192.168.0.105:5160"

    private val authRetrofitClient: Retrofit.Builder by lazy {
        Retrofit.Builder()
            .baseUrl(AUTH_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
    }

    private val utilsRetrofitClient: Retrofit.Builder by lazy {
        Retrofit.Builder()
            .baseUrl(UTILS_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
    }

    private val operationsRetrofitClient: Retrofit.Builder by lazy {
        Retrofit.Builder()
            .baseUrl(OPERATIONS_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
    }

    val authApi: AuthApi by lazy {
        authRetrofitClient
            .build()
            .create(AuthApi::class.java)
    }

    val roleApi: RoleApi by lazy {
        authRetrofitClient
            .build()
            .create(RoleApi::class.java)
    }

    val userApi: UserApi by lazy {
        authRetrofitClient
            .build()
            .create(UserApi::class.java)
    }

    val districtApi: DistrictApi by lazy {
        utilsRetrofitClient
            .build()
            .create(DistrictApi::class.java)
    }

    val measurementUnitApi: MeasurementUnitApi by lazy {
        utilsRetrofitClient
            .build()
            .create(MeasurementUnitApi::class.java)
    }

    val resourceApi: ResourceApi by lazy {
        utilsRetrofitClient
            .build()
            .create(ResourceApi::class.java)
    }

    val resourceMeasurementUnitApi: ResourceMeasurementUnitApi by lazy {
        utilsRetrofitClient
            .build()
            .create(ResourceMeasurementUnitApi::class.java)
    }

    val eventApi: EventApi by lazy {
        operationsRetrofitClient
            .build()
            .create(EventApi::class.java)
    }

    val eventStatusApi: EventStatusApi by lazy {
        operationsRetrofitClient
            .build()
            .create(EventStatusApi::class.java)
    }
    
    val eventTypeApi: EventTypeApi by lazy {
        operationsRetrofitClient
            .build()
            .create(EventTypeApi::class.java)
    }

    val groupApi: GroupApi by lazy {
        operationsRetrofitClient
            .build()
            .create(GroupApi::class.java)
    }

    val operationTaskApi: OperationTaskApi by lazy {
        operationsRetrofitClient
            .build()
            .create(OperationTaskApi::class.java)
    }

    val operationTaskStatusApi: OperationTaskStatusApi by lazy {
        operationsRetrofitClient
            .build()
            .create(OperationTaskStatusApi::class.java)
    }

    val operationWorkerApi: OperationWorkerApi by lazy {
        operationsRetrofitClient
            .build()
            .create(OperationWorkerApi::class.java)
    }

    val resourcesEventApi: ResourcesEventApi by lazy {
        operationsRetrofitClient
            .build()
            .create(ResourcesEventApi::class.java)

    }
}