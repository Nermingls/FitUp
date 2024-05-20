package dev.nermingules.nsapp.onboarding

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import dev.nermingules.nsapp.base.BaseFragment
import dev.nermingules.nsapp.R
import dev.nermingules.nsapp.databinding.FragmentSplashBinding

class SplashFragment: BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {

    private val  SPLASH_TIME: Long = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //binding.progressBar.visibility = View.VISIBLE
        //  binding.progressBar.setProgress(100,true)
        android.os.Handler().postDelayed({
            findNavController().navigate(R.id.openOnboarding)
            binding.progressBar.visibility = View.GONE
        }, SPLASH_TIME)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.progressBar.max = 100
        binding.progressBar.progress = 0
        Thread(Runnable {
            var progressStatus = 0
            while (progressStatus < 100) {
                progressStatus++
                // Simulate loading process
                Thread.sleep(SPLASH_TIME / 100)
                activity?.runOnUiThread {
                    binding.progressBar.progress = progressStatus
                }
            }
        }).start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.progressBar.visibility = View.GONE
    }

}