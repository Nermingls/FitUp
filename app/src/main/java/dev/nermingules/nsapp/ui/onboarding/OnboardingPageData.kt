package dev.nermingules.nsapp.ui.onboarding

import android.net.Uri

data class OnboardingPageData(
    val title: String?,
    val description: String?,
    val videoUri: String?,
    val useCardView: Boolean,
)
