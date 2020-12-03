package com.dbhiltapp.app.feature.main.view

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.dbhiltapp.app.R
import com.dbhiltapp.app.databinding.MainFragmentBinding
import com.dbhiltapp.app.feature.main.entities.Hit
import com.dbhiltapp.app.feature.main.entities.PixabayImagesOut
import com.dbhiltapp.app.feature.main.view.adapter.MainListAdapter
import com.dbhiltapp.app.feature.main.view.adapter.OnItemClick
import com.dbhiltapp.app.feature.main.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.main_fragment.*

@AndroidEntryPoint
class MainFragment : Fragment(), OnItemClick {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: MainFragmentBinding

    private lateinit var loader: ProgressDialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.binding = MainFragmentBinding.bind(view)
        this.viewModel = ViewModelProvider(this, defaultViewModelProviderFactory).get(MainViewModel::class.java)

        setupUI()
        setupObservers()
    }

    private fun setupUI() {
        this.loader = ProgressDialog(context)
        this.loader.setTitle("Loading...")
        this.loader.setCancelable(false)
    }

    private fun setupObservers() {
        this.loader.show()
        this.viewModel.images.observe(viewLifecycleOwner, Observer {
            buildPixabayImageList(it)
        })
    }

    /**
     * Main function to build list of Pixabay images
     */
    private fun buildPixabayImageList(response: PixabayImagesOut) {
        this.loader.dismiss()

        if (response.hits != null && response.hits!!.isNotEmpty()) {
            this.binding.listVisible = true

            val adapter = MainListAdapter(response.hits!!, this)
            recyclerView.layoutManager = GridLayoutManager(context, 2)
            recyclerView.adapter = adapter
        } else {
            showEmptyState()
        }
    }

    /**
     * Show empty state if there is an error on request or if list of image is empty
     */
    private fun showEmptyState() {
        this.binding.listVisible = false
    }

    override fun itemClick(data: Hit) {
        Toast.makeText(context, data.user, Toast.LENGTH_SHORT).show()
    }

}