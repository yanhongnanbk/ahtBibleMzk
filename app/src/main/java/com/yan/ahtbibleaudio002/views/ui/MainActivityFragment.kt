package com.yan.ahtbibleaudio002.views.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.yan.ahtbibleaudio002.R
import com.yan.ahtbibleaudio002.externalSource.helpers.PLAYPOS
import com.yan.ahtbibleaudio002.models.AudioItem
import com.yan.ahtbibleaudio002.remote.AudioServiceInterface
import com.yan.ahtbibleaudio002.repositories.AudioRepository
import com.yan.ahtbibleaudio002.viewmodels.MainActivityViewModel
import com.yan.ahtbibleaudio002.views.adapters.AudioAdapter
import com.yan.ahtbibleaudio002.views.adapters.ItemClickListener
import kotlinx.android.synthetic.main.fragment_main_activity.view.*

const val MAIN_ACTIVITY_FRAGMENT = "MainActivityFragment"

class MainActivityFragment : Fragment(), ItemClickListener {
    var audioList: ArrayList<AudioItem> = arrayListOf()
    private val mainActivityViewModel by viewModels<MainActivityViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModels()
        /**fetch data*/
        mainActivityViewModel.getAllAudios() { result ->
//            Log.d(MAIN_ACTIVITY_FRAGMENT, "all songs => ${result}")
            val albumName = arguments?.getString("fragmentName")

            /***/
            audioList = if (albumName == "All Songs") {

                result as ArrayList<AudioItem>
            } else {
                result.filter {
                    it.category.toString() == albumName
                } as ArrayList<AudioItem>

            }
            /***/
            Log.d("MainActivityFragment","${audioList.size}+${albumName}")

            val adapter = AudioAdapter(audioList, requireActivity(), this)
            // RecyclerView
            view.rv_audio_list.adapter = adapter
            view.rv_audio_list.layoutManager = LinearLayoutManager(requireActivity())
            view.rv_audio_list.setHasFixedSize(true)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main_activity, container, false)
    }

    private fun setupViewModels() {
        val service = AudioServiceInterface.instance
        mainActivityViewModel.audioRepository = AudioRepository(service)
    }

    override fun onItemClickListener(audioItem: AudioItem, position: Int) {
//        Toast.makeText(requireContext(), audioItem.title +"+++" +position,Toast.LENGTH_SHORT).show()
        val intent = Intent(activity, DetailActivity::class.java)
        intent.putExtra("list", audioList)
        intent.putExtra("detail", audioItem)
        intent.putExtra(PLAYPOS, position)
        startActivity(intent)
    }


}