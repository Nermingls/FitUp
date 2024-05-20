package dev.nermingules.nsapp.onboarding

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import dev.nermingules.nsapp.base.BaseFragment
import dev.nermingules.nsapp.databinding.FragmentOnboardingPageBinding

class OnboardingPageFragment : BaseFragment<FragmentOnboardingPageBinding>(FragmentOnboardingPageBinding::inflate) {

    private lateinit var player: Player

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        player = ExoPlayer.Builder(requireContext())
            .build()
            .apply {
                repeatMode = Player.REPEAT_MODE_ONE
                playWhenReady = true
            }
    }

    override fun onStart() {
        super.onStart()
        val videoUriString = arguments?.getString(ARGS_URI_KEY)
        val videoUri = Uri.parse(videoUriString)
        val mediaItem = MediaItem.fromUri(videoUri)
        player.addMediaItem(mediaItem)
        player.prepare()
    }

    override fun onStop() {
        super.onStop()
            player.release()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        player.release()
    }


    private fun initViews() {
        getUIArguments()?.let {
            binding.materialTextView.text = it.title
            binding.materialTextView.visibility = if (it.title.isNullOrEmpty()) View.GONE else View.VISIBLE

            binding.materialTextView2.text = it.description
            binding.materialTextView2.visibility = if (it.description.isNullOrEmpty()) View.GONE else View.VISIBLE


            // Sayfanın "cardView" özelliğine sahip olup olmadığını kontrol et
            if (it.useCardView) {
                binding.materialTextView.visibility = View.GONE
                binding.materialTextView2.visibility = View.GONE
                binding.materialCardView.visibility = View.VISIBLE
                binding.cardText.text = it.description

            } else {
                binding.materialTextView.visibility = View.VISIBLE
                binding.materialCardView.visibility = View.GONE
            }

            binding.backgroundPlayer.player = player
        }
    }

    private fun getUIArguments(): OnboardingPageData? {
        val title = arguments?.getString(TITLE_KEY)
        val description = arguments?.getString(DESCRIPTION_KEY)
        val videoUriString = arguments?.getString(ARGS_URI_KEY)
        val useCardView = arguments?.getBoolean(CARD_VIEW_KEY) ?: false

        if (title != null || description != null || !videoUriString.isNullOrEmpty()) {
            return OnboardingPageData(title, description, videoUriString, useCardView)
        }

        return null
    }

    companion object {
        private const val TITLE_KEY = "TITLE_KEY"
        private const val DESCRIPTION_KEY = "DESCRIPTION_KEY"
        private const val ARGS_URI_KEY = "ARGS_URI_KEY"
        private const val CARD_VIEW_KEY = "CARD_VIEW_KEY"

        @JvmStatic
        fun newInstance(pageData: OnboardingPageData) =
            OnboardingPageFragment().apply {
                arguments = Bundle().apply {
                    putString(TITLE_KEY, pageData.title)
                    putString(DESCRIPTION_KEY, pageData.description)
                    putString(ARGS_URI_KEY, pageData.videoUri.toString())
                    putBoolean(CARD_VIEW_KEY, pageData.useCardView)
                }
            }
    }
}
