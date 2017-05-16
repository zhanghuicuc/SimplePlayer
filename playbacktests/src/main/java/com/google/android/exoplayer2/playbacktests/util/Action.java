/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.android.exoplayer2.playbacktests.util;

import android.util.Log;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector;

/**
 * Base class for actions to perform during playback tests.
 */
public abstract class Action {

  private final String tag;
  private final String description;

  /**
   * @param tag A tag to use for logging.
   * @param description A description to be logged when the action is executed.
   */
  public Action(String tag, String description) {
    this.tag = tag;
    this.description = description;
  }

  /**
   * Executes the action.
   *
   * @param player The player to which the action should be applied.
   * @param trackSelector The track selector to which the action should be applied.
   */
  public final void doAction(ExoPlayer player, MappingTrackSelector trackSelector) {
    Log.i(tag, description);
    doActionImpl(player, trackSelector);
  }

  /**
   * Called by {@link #doAction(ExoPlayer, MappingTrackSelector)} do actually perform the action.
   *
   * @param player The player to which the action should be applied.
   * @param trackSelector The track selector to which the action should be applied.
   */
  protected abstract void doActionImpl(ExoPlayer player, MappingTrackSelector trackSelector);

  /**
   * Calls {@link ExoPlayer#seekTo(long)}.
   */
  public static final class Seek extends Action {

    private final long positionMs;

    /**
     * @param tag A tag to use for logging.
     * @param positionMs The seek position.
     */
    public Seek(String tag, long positionMs) {
      super(tag, "Seek:" + positionMs);
      this.positionMs = positionMs;
    }

    @Override
    protected void doActionImpl(ExoPlayer player, MappingTrackSelector trackSelector) {
      player.seekTo(positionMs);
    }

  }

  /**
   * Calls {@link ExoPlayer#stop()}.
   */
  public static final class Stop extends Action {

    /**
     * @param tag A tag to use for logging.
     */
    public Stop(String tag) {
      super(tag, "Stop");
    }

    @Override
    protected void doActionImpl(ExoPlayer player, MappingTrackSelector trackSelector) {
      player.stop();
    }

  }

  /**
   * Calls {@link ExoPlayer#setPlayWhenReady(boolean)}.
   */
  public static final class SetPlayWhenReady extends Action {

    private final boolean playWhenReady;

    /**
     * @param tag A tag to use for logging.
     * @param playWhenReady The value to pass.
     */
    public SetPlayWhenReady(String tag, boolean playWhenReady) {
      super(tag, playWhenReady ? "Play" : "Pause");
      this.playWhenReady = playWhenReady;
    }

    @Override
    protected void doActionImpl(ExoPlayer player, MappingTrackSelector trackSelector) {
      player.setPlayWhenReady(playWhenReady);
    }

  }

  /**
   * Calls {@link MappingTrackSelector#setRendererDisabled(int, boolean)}.
   */
  public static final class SetRendererDisabled extends Action {

    private final int rendererIndex;
    private final boolean disabled;

    /**
     * @param tag A tag to use for logging.
     * @param rendererIndex The index of the renderer.
     * @param disabled Whether the renderer should be disabled.
     */
    public SetRendererDisabled(String tag, int rendererIndex, boolean disabled) {
      super(tag, "SetRendererDisabled:" + rendererIndex + ":" + disabled);
      this.rendererIndex = rendererIndex;
      this.disabled = disabled;
    }

    @Override
    protected void doActionImpl(ExoPlayer player, MappingTrackSelector trackSelector) {
      trackSelector.setRendererDisabled(rendererIndex, disabled);
    }

  }

}
