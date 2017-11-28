package com.github.ljarka.bakingapp.detail;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.ljarka.bakingapp.R;
import com.github.ljarka.bakingapp.network.model.Step;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class RecipeDetailStepFragment extends Fragment {
    public static final String CURRENT_POSITION = "CurrentPosition";
    public static final String FRAGMENT_TAG = "RecipeDetailStepFragment";
    private static final String EXTRA_STEP = "extra_step";
    private SimpleExoPlayer player;
    private TextView description;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recipe_step_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Step step = getArguments().getParcelable(EXTRA_STEP);
        description = view.findViewById(R.id.description);
        description.setText(step.getDescription());

        if (step.getVideoURL() != null && !step.getVideoURL().isEmpty()) {
            prepareExoPlayer(view.<SimpleExoPlayerView>findViewById(R.id.playerView), step.getVideoURL(), savedInstanceState);
        }
    }

    private void prepareExoPlayer(SimpleExoPlayerView playerView, String videoUrl, @Nullable Bundle savedInstanceState) {
        playerView.setVisibility(View.VISIBLE);

        DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        AdaptiveTrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        DefaultTrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        player = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);
        DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(getContext(),
                Util.getUserAgent(getContext(), getContext().getApplicationInfo().name));
        playerView.setPlayer(player);
        ExtractorMediaSource videoSource =
                new ExtractorMediaSource(Uri.parse(videoUrl), dataSourceFactory, new DefaultExtractorsFactory(), null, null);
        player.prepare(videoSource);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE && !getResources().getBoolean(
                R.bool.tablet)) {
            playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
            playerView.getLayoutParams().height = getResources().getDisplayMetrics().heightPixels;
            description.setVisibility(View.GONE);
            hideSystemUI();
        }

        if (savedInstanceState != null) {
            long currentPosition = savedInstanceState.getLong(CURRENT_POSITION);

            if (currentPosition != 0) {
                player.seekTo(savedInstanceState.getLong(CURRENT_POSITION));
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(CURRENT_POSITION, player.getCurrentPosition());
    }

    private void hideSystemUI() {
        getActivity().getWindow()
                .getDecorView()
                .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (player != null) {
            player.stop();
            player.release();
            player = null;
        }
    }

    public static Fragment newInstance(@Nullable Step step) {
        Fragment recipeDetailStepFragment = new RecipeDetailStepFragment();
        Bundle arguments = new Bundle();
        arguments.putParcelable(EXTRA_STEP, step);
        recipeDetailStepFragment.setArguments(arguments);
        return recipeDetailStepFragment;
    }
}
