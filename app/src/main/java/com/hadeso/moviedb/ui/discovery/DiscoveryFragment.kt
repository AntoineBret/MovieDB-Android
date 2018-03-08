package com.hadeso.moviedb.ui.discovery

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hadeso.moviedb.R
import com.hadeso.moviedb.di.Injectable
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

/**
 * Created by 77796 on 21-Dec-17.
 */
class DiscoveryFragment : Fragment(), Injectable {

    companion object {
        fun newInstance(): Fragment {
            return DiscoveryFragment()
        }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: DiscoveryViewModel

    private val movieSelectedPublished = PublishSubject.create<DiscoveryIntent.MovieSelected>()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater?.inflate(R.layout.fragment_main, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(DiscoveryViewModel::class.java)
        viewModel.init()
    }

    fun intents(): Observable<DiscoveryIntent> {
        return Observable.just(DiscoveryIntent.MovieSelected(""))
    }

    fun render(state: DiscoveryViewState) {

    }

}