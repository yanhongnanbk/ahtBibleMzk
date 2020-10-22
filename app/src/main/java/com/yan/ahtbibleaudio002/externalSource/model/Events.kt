package com.yan.ahtbibleaudio002.externalSource.model

import com.yan.ahtbibleaudio002.models.AudioItem
import java.util.ArrayList


class Events {
    class SongChanged internal constructor(val song: AudioItem?)

    class SongStateChanged internal constructor(val isPlaying: Boolean)

    class PlaylistUpdated internal constructor(val songs: List<AudioItem>)

    class ProgressUpdated internal constructor(val progress: Int)

    class DurationUpdate internal constructor(val duration: Int)

    // trigger service to resend info song
    class EmptyObject internal constructor()

}
