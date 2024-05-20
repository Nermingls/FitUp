package dev.nermingules.nsapp.onboarding

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import dev.nermingules.nsapp.base.BaseFragment
import dev.nermingules.nsapp.R
import dev.nermingules.nsapp.databinding.FragmentOnboardingBinding
import kotlinx.coroutines.launch

class OnboardingFragment : BaseFragment<FragmentOnboardingBinding>(FragmentOnboardingBinding::inflate) {

    private lateinit var viewPager2: ViewPager2
    private lateinit var pagerAdapter: OnboardingPagerAdapter

    private val hasReachedLastPage: Boolean
        get() = pagerPosition == pagerAdapter.itemCount - 1

    private var pagerPosition = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        setOnClickListener()
        setViewPager()
    }

    private fun setViewPager() {
        val pages = createPageData()
        pagerAdapter = OnboardingPagerAdapter(this, pages, lifecycle)
        binding.viewPager.adapter = pagerAdapter
        binding.viewPager.registerOnPageChangeCallback(onPageChange)
    }

    private val onPageChange = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            pagerPosition = position
        }
    }

    private fun setOnClickListener() {
        binding.filledButton.setOnClickListener {
            if (hasReachedLastPage) {
                informDoneOnboarding()
            } else {
                moveToNextPage()
            }
        }
    }

    private fun moveToNextPage() {
        binding.viewPager.currentItem += 1
    }

    private fun informDoneOnboarding() {
        lifecycleScope.launch {
            // onboardingViewModel.markHasDoneOnboarding()
            navigateToConsent()
        }
    }

    private fun navigateToConsent() {
        findNavController().navigate(OnboardingFragmentDirections.openHome())
    }

    private fun createPageData() = listOf(
        OnboardingPageData(getString(R.string.onboarding_title1), getString(R.string.onboarding_body1), "file:///android_asset/one.mp4", false),
        OnboardingPageData(null, null, "file:///android_asset/two.mp4", false),
        OnboardingPageData(null, null, "file:///android_asset/three.mp4", false),
    )

}
