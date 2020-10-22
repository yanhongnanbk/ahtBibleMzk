package com.yan.ahtbibleaudio002.views.ui

import android.app.PendingIntent
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.SeekBar
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.squareup.otto.Bus
import com.squareup.otto.Subscribe
import com.yan.ahtbibleaudio002.R
import com.yan.ahtbibleaudio002.externalSource.helpers.*
import com.yan.ahtbibleaudio002.externalSource.model.Events
import com.yan.ahtbibleaudio002.externalSource.services.MusicService
import com.yan.ahtbibleaudio002.models.AudioItem
import com.yan.ahtbibleaudio002.viewmodels.DetailActivityViewModel
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_media_controller.*
import java.util.ArrayList

class DetailActivity : AppCompatActivity() {
    lateinit var bus: Bus
    var isExistMedia = false
    lateinit var pendingIntent: PendingIntent
    lateinit var model: DetailActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        // my_child_toolbar is defined in the layout file
        setSupportActionBar(tool_bar)
        // Get a support ActionBar corresponding to this toolbar and enable the Up button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        pendingIntent = PendingIntent.getBroadcast(
            applicationContext, 0,
            Intent().setAction("STOP_TEST_SERVICE"), PendingIntent.FLAG_UPDATE_CURRENT
        )
        var listMedia = ArrayList<AudioItem>()
        listMedia = intent.getSerializableExtra("list") as ArrayList<AudioItem>
        val audioItem = intent.getSerializableExtra("detail") as AudioItem
        val position = intent.getIntExtra(PLAYPOS, -1)
        val p = listMedia.indexOf(audioItem)

        isExistMedia = intent.getBooleanExtra("isExistMedia", false)

        Log.d("DetailActivity", "${p} ${position}")

        model = ViewModelProvider(this).get(DetailActivityViewModel::class.java)
        model.loadMediaItems(listMedia)
        model.setSelectedMedia(audioItem)
        model.setSelectNumber(position)

        bus = BusProvider.instance
        bus.register(this)

        /***/
        if (!isExistMedia) {
            initServicePlayer()
        } else {
            Handler(Looper.getMainLooper()).postDelayed({
                //Your Code
                bus.post(Events.EmptyObject())

            }, 300)
        }
        /***/
        //
        initSeekbarChangeListener()
        shuffle_btn.setOnClickListener { toggleShuffle() }
        previous_btn.setOnClickListener { sendIntent(PREVIOUS) }
        play_pause_btn.setOnClickListener { sendIntent(PLAYPAUSE) }
        next_btn.setOnClickListener {
            sendIntent(NEXT)
        }
        repeat_btn.setOnClickListener { toggleSongRepetition() }
        song_progress_current.setOnClickListener { sendIntent(SKIP_BACKWARD) }
        song_progress_max.setOnClickListener { sendIntent(SKIP_FORWARD) }
        showStatusPlayer();
        /***/
    }

    /**Init*/
    private fun initServicePlayer() {

        Intent(this, MusicService::class.java).apply {
            putExtra(SONG_POS, model.getSelectNUmber().value)
            putExtra("list", model.getMediaList().value)
            putExtra("detail", model.getSelectedMedia().value)
//            putExtra(PLAYPOS, position)
            action = INIT
            startService(this)
        }
    }

    /***/
    private fun toggleShuffle() {
        val isShuffleEnabled = !config.isShuffleEnabled
        config.isShuffleEnabled = isShuffleEnabled
        if (isShuffleEnabled)
            shuffle_btn.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_shuffle_blue
                )
            )
        else
            shuffle_btn.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_shuffle))

    }

    private fun toggleSongRepetition() {
        val repeatSong = !config.repeatSong
        config.repeatSong = repeatSong
        if (repeatSong) repeat_btn.setImageDrawable(
            ContextCompat.getDrawable(
                this,
                R.drawable.ic_repeat_blue
            )
        )
        else repeat_btn.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_repeat))

    }

    private fun showStatusPlayer() {
        val isShuffleEnabled = config.isShuffleEnabled
        if (isShuffleEnabled)
            shuffle_btn.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_shuffle_blue
                )
            )
        else
            shuffle_btn.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_shuffle))

        val repeatSong = config.repeatSong
        if (repeatSong) repeat_btn.setImageDrawable(
            ContextCompat.getDrawable(
                this,
                R.drawable.ic_repeat_blue
            )
        )
        else repeat_btn.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_repeat))


    }

    private fun initSeekbarChangeListener() {
        song_progressbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                val duration = song_progressbar.max.getFormattedDuration()
                val formattedProgress = progress.getFormattedDuration()
                Log.d("Seekbar", "${duration}+${formattedProgress}")
                song_progress_current.text = formattedProgress
                song_progress_max.text = duration
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                Intent(this@DetailActivity, MusicService::class.java).apply {
                    putExtra(PROGRESS, seekBar.progress)
                    action = SET_PROGRESS
                    startService(this)
                }
            }
        })
    }

    /***/

    override fun onDestroy() {
        super.onDestroy()
        bus.unregister(this)

    }

    @Subscribe
    fun songChangedEvent(event: Events.SongChanged) {
        Log.e("change", "----");
        val song = event.song
        if (song != null) {
            model.setSelectedMedia(song)
//            tool_bar.setTitle(song.title)
//            tool_bar.title = song.title


//            this@DetailActivity.runOnUiThread(java.lang.Runnable {
//
//            })
        }

    }

    @Subscribe
    fun songChangeDuration(event: Events.DurationUpdate) {
        val duration = event.duration
        song_progressbar.max = duration / 1000

    }

    @Subscribe
    fun songStateChanged(event: Events.SongStateChanged) {
        val isPlaying = event.isPlaying
        play_pause_btn.setImageDrawable(resources.getDrawable(if (isPlaying) R.drawable.ic_pause_button else R.drawable.ic_play_button))
    }


    @Subscribe
    fun progressUpdated(event: Events.ProgressUpdated) {
        val progress = event.progress
        song_progressbar.progress = progress
    }
    /**Init*/
}