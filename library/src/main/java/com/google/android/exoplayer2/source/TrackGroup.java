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
package com.google.android.exoplayer2.source;

import android.util.Log;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.util.Assertions;
import java.util.Arrays;
import java.util.List;

// TODO: Add an allowMultipleStreams boolean to indicate where the one stream per group restriction
// does not apply.
/**
 * Defines a group of tracks exposed by a {@link MediaPeriod}.
 * <p>
 * A {@link MediaPeriod} is only able to provide one {@link SampleStream} corresponding to a group
 * at any given time, however this {@link SampleStream} may adapt between multiple tracks within the
 * group.
 */
public final class TrackGroup {

  /**
   * The number of tracks in the group.
   */
  public final int length;

  private final Format[] formats;

  // Lazily initialized hashcode.
  private int hashCode;

  private final List<Float> QoeMedias;
  private final List<Long> SegDurationsUs;
  /**
   * @param formats The track formats. Must not be null or contain null elements.
   */
  public TrackGroup(List<Float> QoeMedias, List<Long> SegDurationsUs, Format... formats) {
    Assertions.checkState(formats.length > 0 && QoeMedias.size()==formats.length);
    this.formats = formats;
    this.length = formats.length;
    this.QoeMedias = QoeMedias;
    this.SegDurationsUs = SegDurationsUs;
  }

  /**
   * @param formats The track formats. Must not be null or contain null elements.
   */
  public TrackGroup(Format... formats) {
    Assertions.checkState(formats.length > 0);
    this.formats = formats;
    this.length = formats.length;
    this.QoeMedias = null;
    this.SegDurationsUs = null;
  }

  /**
   * Returns the QoEMedia of the track at a given index.
   *
   * @param index The index of the track.
   * @return The track's QoEMedia.
   */
  public Float getQoEMedia(int index) {
    return QoeMedias.get(index);
  }

  /**
   * Returns the SegDurationUs of the track at a given index.
   *
   * @param index The index of the track.
   * @return The track's SegDurationUs.
   */
  public Long getSegDutationsUs(int index) {
    return SegDurationsUs.get(index);
  }

  /**
   * Returns the format of the track at a given index.
   *
   * @param index The index of the track.
   * @return The track's format.
   */
  public Format getFormat(int index) {
    return formats[index];
  }

  /**
   * Returns the index of the track with the given format in the group.
   *
   * @param format The format.
   * @return The index of the track, or {@link C#INDEX_UNSET} if no such track exists.
   */
  public int indexOf(Format format) {
    for (int i = 0; i < formats.length; i++) {
      if (format == formats[i]) {
        return i;
      }
    }
    return C.INDEX_UNSET;
  }

  @Override
  public int hashCode() {
    if (hashCode == 0) {
      int result = 17;
      result = 31 * result + Arrays.hashCode(formats);
      hashCode = result;
    }
    return hashCode;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    TrackGroup other = (TrackGroup) obj;
    return length == other.length && Arrays.equals(formats, other.formats);
  }

}
