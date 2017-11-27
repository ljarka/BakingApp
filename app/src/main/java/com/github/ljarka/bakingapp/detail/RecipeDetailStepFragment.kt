package com.github.ljarka.bakingapp.detail

import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.ljarka.bakingapp.R
import com.github.ljarka.bakingapp.network.model.Step
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
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
            view.playerView.visibility = View.VISIBLE
            val bandwidthMeter = DefaultBandwidthMeter()
            val videoTrackSelectionFactory = AdaptiveTrackSelection.Factory(bandwidthMeter)
            val trackSelector = DefaultTrackSelector(videoTrackSelectionFactory)
            player = ExoPlayerFactory.newSimpleInstance(context, trackSelector)
            val dataSourceFactory = DefaultDataSourceFactory(context, Util.getUserAgent(context, context?.applicationInfo?.name))
            playerView.player = player
            val videoSource = ExtractorMediaSource(Uri.parse(step?.videoURL),
                    dataSourceFactory, DefaultExtractorsFactory(), null, null)
            player?.prepare(videoSource)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        player?.stop()
        player?.release()
    }

    companion object {
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