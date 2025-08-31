package cd.zgeniuscoders.zwallet.modules.auth.di

import cd.zgeniuscoders.zwallet.modules.auth.data.AuthenticationServiceImpl
import cd.zgeniuscoders.zwallet.modules.auth.domains.services.AuthenticationService
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object authModule {


    @Provides
    @Singleton
    fun provideFirebaseAuthentication(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideAuthenticationService(auth: FirebaseAuth): AuthenticationService {
        return AuthenticationServiceImpl(auth)
    }


}