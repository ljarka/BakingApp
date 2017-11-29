package com.github.ljarka.bakingapp.detail;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
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
    private static final String CURRENT_POSITION = "CurrentPosition";
    private static final String PLAY_WHEN_READY = "PlayWhenReady";
    public static final String FRAGMENT_TAG = "RecipeDetailStepFragment";
    private static final String EXTRA_STEP = "extra_step";
    private SimpleExoPlayer player;
    private SimpleExoPlayerView playerView;
    private TextView description;
    private String videoUrl;
    private long restoredPlayerPosition;
    private boolean playWhenReady;

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
        playerView = view.findViewById(R.id.playerView);
        videoUrl = step.getVideoURL();

        if (savedInstanceState != null) {
            long currentPosition = savedInstanceState.getLong(CURRENT_POSITION);
            playWhenReady = savedInstanceState.getBoolean(PLAY_WHEN_READY);

            if (currentPosition != 0) {
                restoredPlayerPosition = savedInstanceState.getLong(CURRENT_POSITION);
            }
        }

        if (!TextUtils.isEmpty(videoUrl)) {
            adjustScreenToDisplayMovie();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        initPlayer();
    }

    private void initPlayer() {
        DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        AdaptiveTrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        DefaultTrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        player = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);
        playerView.setPlayer(player);
        DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(getContext(),
                Util.getUserAgent(getContext(), getContext().getApplicationInfo().name));
        ExtractorMediaSource videoSource =
                new ExtractorMediaSource(Uri.parse(videoUrl), dataSourceFactory, new DefaultExtractorsFactory(), null, null);
        player.prepare(videoSource);
        player.seekTo(restoredPlayerPosition);
        player.setPlayWhenReady(playWhenReady);
    }

    private void adjustScreenToDisplayMovie() {
        playerView.setVisibility(View.VISIBLE);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE && !getResources().getBoolean(
                R.bool.tablet)) {
            playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
            playerView.getLayoutParams().height = getResources().getDisplayMetrics().heightPixels;
            description.setVisibility(View.GONE);
            hideSystemUI();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (player != null) {
            outState.putLong(CURRENT_POSITION, player.getCurrentPosition());
            outState.putBoolean(PLAY_WHEN_READY, player.getPlayWhenReady());
        }
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
    public void onStop() {
        super.onStop();
        releasePlayer();
    }

    private void releasePlayer() {
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
