package com.adr.movdb.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.adr.movdb.databinding.ActivityMainBinding
import com.adr.movdb.model.api.ApiResponse
import com.adr.movdb.view.component.PopUpDialogFragment
import com.adr.movdb.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getConfiguration()
        subscribeToConfigurationResponse()
    }

    private fun subscribeToConfigurationResponse() = lifecycleScope.launch {
        viewModel.configurationResponse.collectLatest {
            it?.let {
                when (it) {
                    is ApiResponse.Error -> {
                        val popUp = PopUpDialogFragment.newInstance(true, {
                            viewModel.getConfiguration()
                        }, it.error?.second)
                        popUp.show(this@MainActivity.supportFragmentManager, popUp.tag)
                    }
                    is ApiResponse.Completed -> {
                        it.data?.let { response ->
                            viewModel.saveConfiguration(response)
                        } ?: run {
                            val popUp = PopUpDialogFragment.newInstance(true, {
                                viewModel.getConfiguration()
                            }, it.error?.second)
                            popUp.show(this@MainActivity.supportFragmentManager, popUp.tag)
                        }
                    }
                    else -> Unit
                }
            }
        }
    }
}