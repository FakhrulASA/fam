package com.famstudio.tiktok.ui

import android.app.DownloadManager
import android.content.*
import android.content.Context.MODE_PRIVATE
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import android.widget.Toast
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.famstudio.tiktok.databinding.FragmentFirstBinding
import com.famstudio.tiktok.model.request.GetVideoRequestModel
import com.famstudio.tiktok.util.*
import com.google.android.material.navigation.NavigationView


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
    private val drawer: DrawerLayout? = null
    private val navigationView: NavigationView? = null

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

        var sharedPreferences: SharedPreferences =
            requireContext().getSharedPreferences("MySharedPref", MODE_PRIVATE)

        // Creating an Editor object to edit(write to the file)
        var myEdit = sharedPreferences.edit()
        binding.button4.setOnClickListener {
            navigateToTiktok()
        }
        binding.videosCont.setOnClickListener {
            startActivity(Intent(DownloadManager.ACTION_VIEW_DOWNLOADS));
        }
        binding.imageView.setOnClickListener {
            var getUrl = getClipboardData()
            if (getUrl.isEmpty() || URLUtil.isValidUrl(getUrl) ) {
                Toast.makeText(
                    requireContext(),
                    "Please paste a valid tiktok video url!",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                vm.getWeather(GetVideoRequestModel(getUrl), { data ->
                    url = data.data.play
                    audioUrl = data.data.music
                    name = data.data.author.nickname
                    id = data.data.author.id
                    title = data.data.title

                    DownloadAndViewerPage.Companion.VideoInfo().apply {
                        url = data.data.play
                        name = data.data.author.nickname
                        duration = data.data.duration.toString()
                    }
                    myEdit.putString(VIDEO_URL, data.data.play)
                    myEdit.putString(AUDIO_URL, data.data.music)
                    myEdit.putString(VIDEO_TITLE, data.data.title)
                    myEdit.putString(PROFILE_NAME, data.data.author.nickname)
                    myEdit.putString(PROFILE_AVATAR, data.data.author.avatar)
                    myEdit.putString(PROFILE_ID, data.data.author.id)
                    myEdit.putString(DURATION, data.data.duration.toString())
                    myEdit.putString(SIZE, data.data.play_count.toString())
                    myEdit.apply()
                    startActivity(Intent(requireContext(), DownloadAndViewerPage::class.java))
                }, {
                    myEdit.putString(VIDEO_URL, "")
                    myEdit.putString(AUDIO_URL, "")
                    myEdit.putString(VIDEO_TITLE, "")
                    myEdit.putString(PROFILE_NAME, "")
                    myEdit.putString(PROFILE_AVATAR, "")
                    myEdit.putString(PROFILE_ID, "")
                    myEdit.putString(DURATION, "")
                    myEdit.putString(SIZE, "")
                    myEdit.apply()
                })
            }


        }
        vm.isLoading.observe(requireActivity()) {
            if (it) {

            } else {

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun navigateToTiktok() {
        val launchIntent: Intent? =
            requireActivity().packageManager.getLaunchIntentForPackage("com.zhiliaoapp.musically")
        if (launchIntent != null) {
            startActivity(launchIntent)
        } else {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=com.zhiliaoapp.musically")
                )
            )
        }
    }

    fun getClipboardData(): String {
        val clipboard = (activity?.getSystemService(Context.CLIPBOARD_SERVICE)) as? ClipboardManager
        var data=  clipboard?.primaryClip?.getItemAt(0)?.text as String
        return if(data.lowercase().contains("tiktok")) data else ""
    }
}