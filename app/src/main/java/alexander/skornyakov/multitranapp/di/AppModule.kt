package alexander.skornyakov.multitranapp.di

import alexander.skornyakov.multitranapp.data.HistoryRoomDatabase
import alexander.skornyakov.multitranapp.repository.TranslationRepository
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTranslationRepository(): TranslationRepository = TranslationRepository()

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context):HistoryRoomDatabase
            = HistoryRoomDatabase.getDatabase(context)

}