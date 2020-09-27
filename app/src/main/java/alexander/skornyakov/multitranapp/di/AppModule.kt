package alexander.skornyakov.multitranapp.di

import alexander.skornyakov.multitranapp.repository.TranslationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTranslationRepository(): TranslationRepository = TranslationRepository()

}