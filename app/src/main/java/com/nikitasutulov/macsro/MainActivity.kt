package com.nikitasutulov.macsro

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.nikitasutulov.macsro.data.dto.BaseResponse
import com.nikitasutulov.macsro.repository.AuthRepository
import com.nikitasutulov.macsro.repository.RoleRepository
import com.nikitasutulov.macsro.repository.UserRepository
import com.nikitasutulov.macsro.viewmodel.auth.AuthViewModel
import com.nikitasutulov.macsro.viewmodel.auth.AuthViewModelFactory
import com.nikitasutulov.macsro.viewmodel.role.RoleViewModel
import com.nikitasutulov.macsro.viewmodel.role.RoleViewModelFactory
import com.nikitasutulov.macsro.viewmodel.user.UserViewModel
import com.nikitasutulov.macsro.viewmodel.user.UserViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private var token: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val authRepository = AuthRepository()
        val authViewModel = ViewModelProvider(
            this,
            AuthViewModelFactory(authRepository)
        )[AuthViewModel::class.java]

        val roleRepository = RoleRepository()
        val roleViewModel = ViewModelProvider(
            this,
            RoleViewModelFactory(roleRepository)
        )[RoleViewModel::class.java]

        val userRepository = UserRepository()
        val userViewModel = ViewModelProvider(
            this,
            UserViewModelFactory(userRepository)
        )[UserViewModel::class.java]

        CoroutineScope(Dispatchers.IO).launch {
            authViewModel.login(
                username = "Nikitosik",
                password = "*Password1Password"
            )
        }

        authViewModel.loginResponse.observe(this) { response ->
            when (response) {
                is BaseResponse.Success -> {
                    Log.v("Login", "Login successful")
                    val responseData = response.data!!
                    token = responseData.token
                    val expiration = responseData.expiration
                    Log.v("Login", "Token: $token")
                    Log.v("Login", "Expiration: $expiration")
                    CoroutineScope(Dispatchers.IO).launch {
                        roleViewModel.getAllRoles("Bearer $token")
                    }
                }

                is BaseResponse.Error -> {
                    Log.e("Login", "Login failed")
                    Log.e("Login", response.error!!.message)
                }

                is BaseResponse.Loading -> {
                    Log.v("Login", "Pending login...")
                }
            }
        }

        roleViewModel.getAllRolesResponse.observe(this) { response ->
            when (response) {
                is BaseResponse.Success -> {
                    Log.v("Role", "getAllRoles successful")
                    val roles = response.data!!
                    Log.v("Role", "Roles: $roles")
                    CoroutineScope(Dispatchers.IO).launch {
                        userViewModel.getAllUsers("Bearer $token")
                    }
                }

                is BaseResponse.Error -> {
                    Log.e("Role", "getAllRoles failed")
                    Log.e("Role", response.error!!.message)
                }

                is BaseResponse.Loading -> {
                    Log.v("Role", "Pending getAllRoles...")
                }
            }
        }

        userViewModel.getAllUsersResponse.observe(this) { response ->
            when (response) {
                is BaseResponse.Success -> {
                    Log.v("User", "getAllUsers successful")
                    val roles = response.data!!
                    Log.v("User", "Users: $roles")
                }

                is BaseResponse.Error -> {
                    Log.e("User", "getAllUsers failed")
                    Log.e("User", response.error!!.message)
                }

                is BaseResponse.Loading -> {
                    Log.v("User", "Pending getAllUsers...")
                }
            }
        }
    }
}