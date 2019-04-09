package com.example.reflectit.ui.device.available.details.profiles.details

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.reflectit.R

class ProfileDetailsView : Fragment() {

    companion object {
        fun newInstance() = ProfileDetailsView()
    }

    private lateinit var viewModel: ProfileDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.profile_details_view, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ProfileDetailsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
