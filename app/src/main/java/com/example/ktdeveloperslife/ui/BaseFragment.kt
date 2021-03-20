package com.example.ktdeveloperslife.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.ktdeveloperslife.R
import com.example.ktdeveloperslife.network.CacheArrayList
import com.example.ktdeveloperslife.network.NetworkServiceProvider
import com.example.ktdeveloperslife.network.Post
import com.example.ktdeveloperslife.network.Result
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * A simple [Fragment] subclass.
 * Use the [BaseFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
abstract class BaseFragment : Fragment(){

    private var buttonPrev: FloatingActionButton? = null
    private var buttonNext: FloatingActionButton? = null

    private var cardView: MaterialCardView? = null
    private var imageView: ImageView? = null
    private var descView: TextView? = null

    private var emptyView: View? = null
    private var reloadEmptyButton: MaterialButton? = null

    private var errorView: View? = null
    private var reloadErrorButton: MaterialButton? = null

    private var loadingView: ProgressBar? = null

    private var postsIsReached: Boolean? = false

    private val cache: CacheArrayList<Post> = getCache()
    private var page = getFirstPage()
    private val pagePath = getPagePath()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_base, container, false)

        buttonPrev = view.findViewById(R.id.button_prev)
        buttonNext = view.findViewById(R.id.button_next)

        cardView = view.findViewById(R.id.card_view)
        imageView = view.findViewById(R.id.image_view)
        descView = view.findViewById(R.id.description_view)

        emptyView = view.findViewById(R.id.empty_view)
        reloadEmptyButton = view.findViewById(R.id.button_reload)

        errorView = view.findViewById(R.id.error_view)
        reloadErrorButton = view.findViewById(R.id.button_reload_error)

        loadingView = view.findViewById(R.id.loading_view)

        bindListeners()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadFirstPage()
    }

    private fun bindListeners() {

        buttonPrev?.setOnClickListener {
            if (cache.hasPrevious()) {
                val post = cache.previous()
                descView?.text = post.getDescription()
                Glide.with(requireActivity()).load(post.getGifURL()).into(imageView)
                buttonNext?.visibility = View.VISIBLE
                setSuccessState()
            }
            if (!cache.hasPrevious()) {
                buttonPrev!!.visibility = View.INVISIBLE
            }
        }

        buttonNext?.setOnClickListener {
            if (cache.hasNext()) {
                val post = cache.next()
                descView?.text = post.getDescription()
                Glide.with(requireActivity()).load(post.getGifURL()).into(imageView)
                buttonPrev?.visibility = View.VISIBLE
            } else {
                if (postsIsReached == false) {
                    loadNextPage()
                }
            }
        }

        reloadEmptyButton?.setOnClickListener { loadFirstPage() }

        reloadErrorButton?.setOnClickListener {
            if (page == 0) {
                loadFirstPage()
            } else {
                loadNextPage()
            }
        }

    }

    @SuppressLint("CheckResult")
    protected open fun loadFirstPage() {
        NetworkServiceProvider
            .buildService()
            .getPosts(pagePath, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(
                object : SingleObserver<Result> {
                    override fun onSubscribe(d: Disposable) {
                        setLoadingState()
                    }

                    override fun onSuccess(result: Result) {
                        if (result.getResult().isEmpty()) {
                            setEmptyState()
                            postsIsReached = true
                            return
                        }
                        cache.addAll(result.getResult())
                        val post = cache.next()
                        descView?.text = post.getDescription()
                        Glide.with(requireActivity()).load(post.getGifURL()).into(imageView)
                        buttonNext?.visibility = View.VISIBLE
                        setSuccessState()
                    }

                    override fun onError(e: Throwable) {
                        setErrorState()
                    }
                }
            )
    }

    @SuppressLint("CheckResult")
    private fun loadNextPage() {
        page++
        NetworkServiceProvider
            .buildService()
            .getPosts(pagePath, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(
                object : SingleObserver<Result> {
                    override fun onSubscribe(d: Disposable) {
                        setLoadingState()
                    }

                    override fun onSuccess(result: Result) {
                        if (result.getResult().isEmpty()) {
                            postsIsReached = true
                            buttonNext?.visibility = View.INVISIBLE
                            return
                        }
                        cache.addAll(result.getResult())
                        val post = cache.next()
                        descView?.text = post.getDescription()
                        Glide.with(requireActivity()).load(post.getGifURL()).into(imageView)
                        buttonNext?.visibility = View.VISIBLE
                        setSuccessState()
                    }

                    override fun onError(e: Throwable) {
                        setErrorState()
                    }
                }
            )

    }


    protected abstract fun getCache(): CacheArrayList<Post>

    protected abstract fun getFirstPage(): Int

    protected abstract fun getPagePath(): String?

    protected open fun setEmptyState() {
        cardView!!.visibility = View.GONE
        errorView!!.visibility = View.GONE
        emptyView!!.visibility = View.VISIBLE
        loadingView!!.visibility = View.GONE
    }

    protected open fun setSuccessState() {
        cardView!!.visibility = View.VISIBLE
        errorView!!.visibility = View.GONE
        emptyView!!.visibility = View.GONE
        loadingView!!.visibility = View.GONE
    }

    protected open fun setErrorState() {
        cardView!!.visibility = View.GONE
        errorView!!.visibility = View.VISIBLE
        emptyView!!.visibility = View.GONE
        loadingView!!.visibility = View.GONE
    }

    protected open fun setLoadingState() {
        buttonNext!!.visibility = View.INVISIBLE
        cardView!!.visibility = View.GONE
        errorView!!.visibility = View.GONE
        emptyView!!.visibility = View.GONE
        loadingView!!.visibility = View.VISIBLE
    }


}