package com.example.h_mal.transportcodingchallenge.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.h_mal.transportcodingchallenge.R
import com.example.h_mal.transportcodingchallenge.databinding.MainActivityBinding
import com.example.h_mal.transportcodingchallenge.utils.*
import kotlinx.android.synthetic.main.main_activity.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class MainActivity : AppCompatActivity(), KodeinAware, CompletionListener {

    //obtain viewmodel factory from the application instantiation(s)
    override val kodein by kodein()
    private val factory : MainViewModelFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //setup view for databinding for data to be held in view model
        //viewmodels outlive UI so its the best place to keep data
        val binding: MainActivityBinding = DataBindingUtil.setContentView(this, R.layout.main_activity)
        //create viewmodel class
        val viewModel = ViewModelProviders.of(this,factory).get(MainViewModel::class.java)
        //binding viewmodel to databing
        binding.viewmodel = viewModel

        //setup completion listener
        viewModel.completionListener = this

        //observe live data from the view model to change views accordingly
        viewModel.roadLiveDate.observe(this, Observer {
            result_box.show()
            road_id.text = it.displayName
            road_status.text = it.statusSeverity
            road_description.text = it.statusSeverityDescription
        })
    }

    //disable views while loading
    private fun invalidateViews(){
        search_box.disable()
        submit.disable()
    }

    //enable views after loadning
    private fun revalidateViews(){
        search_box.enable()
        submit.enable()
    }

    override fun onStarted() {
        invalidateViews()
        progress_circular.show()
    }

    override fun onSuccess() {
        revalidateViews()
        progress_circular.hide()
    }

    override fun onFailure(message: String) {
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show()
        progress_circular.hide()
        revalidateViews()
    }

}
