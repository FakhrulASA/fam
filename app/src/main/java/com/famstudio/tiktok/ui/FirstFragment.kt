package com.famstudio.tiktok.ui

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.database.Cursor
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.famstudio.tiktok.databinding.FragmentFirstBinding
import com.famstudio.tiktok.model.request.GetVideoRequestModel
import com.famstudio.tiktok.util.*


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var url: String = ""
    private var audioUrl: String = ""
    private var name: String = ""
    private var id: String = ""
    private var title: String = ""


    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val vm: VideoDownloadViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var list = getAllMedia()
        var sharedPreferences: SharedPreferences =
            requireContext().getSharedPreferences("MySharedPref", MODE_PRIVATE)

        // Creating an Editor object to edit(write to the file)
        var myEdit = sharedPreferences.edit()
        vm.isLoading.observe(requireActivity()) {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }
        binding.button2.setOnClickListener {

            vm.getWeather(GetVideoRequestModel(binding.textView.text.toString()), { data ->
                url = data.data.play
                audioUrl = data.data.music
                name = data.data.author.nickname
                id = data.data.author.id
                title = data.data.title
                myEdit.putString(VIDEO_URL, data.data.play)
                myEdit.putString(AUDIO_URL, data.data.music)
                myEdit.putString(VIDEO_TITLE, data.data.music)
                myEdit.putString(PROFILE_NAME, data.data.music)
                myEdit.putString(PROFILE_AVATAR, data.data.music)
                myEdit.putString(PROFILE_ID, data.data.music)
                myEdit.apply()
                startActivity(Intent(requireContext(), DownloadAndViewerPage::class.java))
            }, {
                myEdit.putString(VIDEO_URL, "")
                myEdit.putString(AUDIO_URL, "")
                myEdit.putString(VIDEO_TITLE, "")
                myEdit.putString(PROFILE_NAME, "")
                myEdit.putString(PROFILE_AVATAR, "")
                myEdit.putString(PROFILE_ID, "")

            })

        }
    }
    fun getAllMedia(): ArrayList<String?>? {
        val videoItemHashSet: HashSet<String> = HashSet()
        val projection = arrayOf(
            MediaStore.Video.VideoColumns.DATA,
            MediaStore.Video.Media.DISPLAY_NAME
        )
        val cursor: Cursor? = requireContext().contentResolver.query(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            projection,
            null,
            null,
            null
        )
        try {
            cursor!!.moveToFirst()
            do {
                videoItemHashSet.add(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)))
            } while (cursor.moveToNext())
            cursor.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ArrayList(videoItemHashSet)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}