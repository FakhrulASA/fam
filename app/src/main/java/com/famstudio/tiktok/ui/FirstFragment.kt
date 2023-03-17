package com.famstudio.tiktok.ui

import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.famstudio.tiktok.databinding.FragmentFirstBinding
import com.famstudio.tiktok.model.request.GetVideoRequestModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val vm: VideoDownloadViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.isLoading.observe(requireActivity()) {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }
        binding.button2.setOnClickListener {
            if (binding.textView.text.isNotEmpty()) {
                vm.getWeather(GetVideoRequestModel(binding.textView.text.toString()), {
                    var intent: Intent =
                        Intent(requireContext(), DownloadAndViewerPage::class.java)
                    intent.putExtra("URL", it.data.play)
                    requireContext().startActivity(intent)
                }, {

                })
                vm.error
                vm.myResponse.observe(requireActivity()) {
                    Toast.makeText(requireContext(), it.msg.toString(), Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}