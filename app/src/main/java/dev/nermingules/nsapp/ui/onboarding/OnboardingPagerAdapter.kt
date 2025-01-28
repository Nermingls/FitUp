package dev.nermingules.nsapp.ui.onboarding

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import dev.nermingules.nsapp.ui.onboarding.OnboardingPageData
import dev.nermingules.nsapp.ui.onboarding.OnboardingPageFragment

class OnboardingPagerAdapter (
    fragment: Fragment,
    private val pages: List<OnboardingPageData>,
    lifecycle: Lifecycle) :FragmentStateAdapter(fragment) {
    override fun getItemCount()=  pages.size
    override fun createFragment(position: Int) = createPage(pages[position])
    private fun createPage(pageData: OnboardingPageData): Fragment = OnboardingPageFragment.newInstance(pageData)


}