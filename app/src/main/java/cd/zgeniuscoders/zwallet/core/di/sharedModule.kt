package cd.zgeniuscoders.zwallet.core.di

import android.content.Context
import cd.zgeniuscoders.zwallet.core.data.DatastoreLocalStorageServiceImpl
import cd.zgeniuscoders.zwallet.core.data.UserServiceImpl
import cd.zgeniuscoders.zwallet.core.domains.services.LocalStorageService
import cd.zgeniuscoders.zwallet.core.domains.services.UserService
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Provides
    @Singleton
    fun provideLocalStorageService(@ApplicationContext context: Context): LocalStorageService {
        return DatastoreLocalStorageServiceImpl(context)
    }
}