package cd.zgeniuscoders.zwallet.shared.di

import cd.zgeniuscoders.zwallet.shared.data.UserServiceImpl
import cd.zgeniuscoders.zwallet.shared.domains.services.UserService
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object shareModule {

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideUserService(db: FirebaseFirestore): UserService {
        return UserServiceImpl(db)
    }
}