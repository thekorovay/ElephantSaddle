package com.nikevg.chebez.elephant.di

import android.content.Context
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.*

@Module
@InstallIn(SingletonComponent::class)
class SpeechModule {

    @Provides
    fun provideLocale(): Locale = Locale.forLanguageTag(LANGUAGE_TAG)

    @Provides
    fun provideSpeechRecognizer(
        @ApplicationContext context: Context
    ): SpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)

    @Provides
    fun provideTextToSpeech(
        @ApplicationContext context: Context,
        locale: Locale
    ): TextToSpeech {
        val tts = TextToSpeech(context) { /* todo обработать статус ошибки? Или не особо? */ }
        tts.language = locale
        return tts
    }

    companion object {
        const val LANGUAGE_TAG = "RU"
    }
}
