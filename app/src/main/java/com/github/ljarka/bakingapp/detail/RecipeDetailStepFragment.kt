package com.github.ljarka.bakingapp.detail

import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import com.github.ljarka.bakingapp.R
import com.github.ljarka.bakingapp.network.model.Step
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.SimpleExoPlayerView
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import kotlinx.android.synthetic.main.fragment_recipe_step_detail.*
import kotlinx.android.synthetic.main.fragment_recipe_step_detail.view.*


class RecipeDetailStepFragment : Fragment() {
    private var player: SimpleExoPlayer? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_recipe_step_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val step: Step? = arguments?.getParcelable(EXTRA_STEP)
        view.description.text = step?.description

        if (step?.videoURL!!.isNotEmpty()) {
            prepareExoPlayer(view.playerView, step.videoURL, savedInstanceState)
        }
    }

    private fun prepareExoPlayer(playerView: SimpleExoPlayerView, videoUrl: String, savedInstanceState: Bundle?) {
        playerView.visibility = View.VISIBLE

        val bandwidthMeter = DefaultBandwidthMeter()
        val videoTrackSelectionFactory = AdaptiveTrackSelection.Factory(bandwidthMeter)
        val trackSelector = DefaultTrackSelector(videoTrackSelectionFactory)
        player = ExoPlayerFactory.newSimpleInstance(context, trackSelector)
        val dataSourceFactory = DefaultDataSourceFactory(context, Util.getUserAgent(context, context?.applicationInfo?.name))
        playerView.player = player
        val videoSource = ExtractorMediaSource(Uri.parse(videoUrl),
                dataSourceFactory, DefaultExtractorsFactory(), null, null)
        player?.prepare(videoSource)

        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL)
            playerView.layoutParams.height = resources.displayMetrics.heightPixels
            description.visibility = View.GONE
            hideSystemUI()
        }

        if (savedInstanceState != null) {
            val currentPosition: Long = savedInstanceState.getLong(CURRENT_POSITION)

            if (currentPosition.compareTo(0) != 0) {
                player?.seekTo(savedInstanceState.getLong(CURRENT_POSITION))
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong(CURRENT_POSITION, player?.currentPosition ?: 0)
    }

    private fun hideSystemUI() {
        activity?.window?.decorView?.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        player?.stop()
        player?.release()
    }

    companion object {
        val CURRENT_POSITION = "CurrentPosition"
        val FRAGMENT_TAG = "RecipeDetailStepFragment"
        private val EXTRA_STEP = "extra_step"
        fun newInstance(step: Step?): Fragment {
            val recipeDetailStepFragment = RecipeDetailStepFragment()
            val arguments = Bundle()
            arguments.putParcelable(EXTRA_STEP, step)
            recipeDetailStepFragment.arguments = arguments
            return recipeDetailStepFragment
        }
    }
}