package com.gymapp.main.di

//import androidx.room.Room
import com.gymapp.features.gymdetail.domain.GymDetailViewModel
import com.gymapp.main.data.repository.gyms.GymsRepository
import com.gymapp.main.data.repository.gyms.GymsRepositoryInterface
import com.gymapp.features.homepage.domain.HomepageViewModel
import com.gymapp.features.mapview.domain.MapViewViewModel
import com.gymapp.features.onboarding.auth.data.AuthRepository
import com.gymapp.features.onboarding.auth.data.AuthRepositoryInterface
import com.gymapp.features.onboarding.auth.domain.AuthInteractor
import com.gymapp.features.onboarding.auth.domain.AuthInteractorInterface
import com.gymapp.features.onboarding.auth.domain.AuthViewModel
import com.gymapp.features.profile.main.data.ProfileRepository
import com.gymapp.features.profile.main.data.ProfileRepositoryInterface
import com.gymapp.features.profile.main.domain.ProfileViewModel
import com.gymapp.features.profile.settings.domain.SettingsViewModel
import com.gymapp.main.data.repository.config.ConfigRepository
import com.gymapp.main.data.repository.config.ConfigRepositoryInterface
//import com.gymapp.main.data.db.GymDatabase
import com.gymapp.main.launcher.data.LauncherRepository
import com.gymapp.main.launcher.data.LauncherRepositoryInterface
import com.gymapp.main.launcher.domain.LauncherViewModel
import com.gymapp.main.network.ApiManagerImpl
import com.gymapp.main.network.ApiManagerInterface
import com.gymapp.main.network.interceptors.AuthHeaderTokenInterceptor
import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import java.util.concurrent.TimeUnit

object ApplicationModule {
    val networkModule =
        module {
            single {
                OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .addInterceptor(ChuckInterceptor(androidContext()))
                    .addInterceptor(AuthHeaderTokenInterceptor())
                    .build()
            }

            single<ApiManagerInterface> { ApiManagerImpl(get()) }
        }

//    val databaseModule = module {
//        single {
//            Room.databaseBuilder(
//                androidContext(),
//                GymDatabase::class.java,
//                GymDatabase.DATABASE_NAME
//            ).build()
//        }
//
//        single { get<GymDatabase>().gymDao() }
//    }

    val repositoryModule = module {
        single<LauncherRepositoryInterface> {
            LauncherRepository(
                get()
//                get()
            )
        }

        single<AuthRepositoryInterface> {
            AuthRepository(
                get()
//                get()
            )
        }

        single<AuthInteractorInterface> {
            AuthInteractor()
        }

        single<GymsRepositoryInterface> {
            GymsRepository(get()/*, get()*/)
        }

        single<ConfigRepositoryInterface> {
            ConfigRepository(get()/*, get()*/)
        }

        single<ProfileRepositoryInterface> {
            ProfileRepository(get()/*, get()*/)
        }
    }

    val viewModelModule = module {

        viewModel {
            LauncherViewModel(get(), get())
        }

        viewModel {
            AuthViewModel(get(), get(), get())
        }

        viewModel {
            HomepageViewModel(get(), get())
        }

        viewModel {
            MapViewViewModel(get())
        }

        viewModel {
            GymDetailViewModel(get())
        }

        viewModel {
            ProfileViewModel(get())
        }

        viewModel {
            SettingsViewModel(get())
        }
    }

}


