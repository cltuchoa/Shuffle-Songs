package com.uchoa.shuffle.songs.ui.home

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.DecelerateInterpolator
import android.widget.Toast
import com.uchoa.shuffle.songs.R
import com.uchoa.shuffle.songs.adapters.SongsAdapter
import com.uchoa.shuffle.songs.callbacks.ItemAdapterCallback
import com.uchoa.shuffle.songs.domain.entities.SongEntity
import com.uchoa.shuffle.songs.helpers.DownloaderFactory
import com.uchoa.shuffle.songs.helpers.Downloaders
import kotlinx.android.synthetic.main.activity_songs.*
import kotlinx.android.synthetic.main.popup_item.*

class HomeActivity : AppCompatActivity(), HomeContract.View,
    ItemAdapterCallback, SwipeRefreshLayout.OnRefreshListener {

    private var adapter: SongsAdapter? = null
    private lateinit var presenter: HomePresenter
    private lateinit var manager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_songs)
        setSupportActionBar(song_activity_toolbar)

        configureRecycleView()
        configurePullToRefresh()
        configureListeners()

        presenter = HomePresenter(this)
        loadSongs()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.shuffle_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_shuffle -> {
                if (!presenter.isLoadingSongs()) {
                    presenter.shuffle()
                    if (fake_popup.visibility == View.VISIBLE) {
                        hideDetailPopup()
                    }
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onRefresh() {
        loadSongs()
    }

    override fun onCellClick(song: SongEntity) {
        showDetailPopup(song)
    }

    private fun showDetailPopup(song: SongEntity) {
        fake_popup.setBackgroundResource(R.color.black_transparent_80)

        fake_popup.visibility = View.VISIBLE
        popup_song_name.text = song.name
        popup_song_artist.text = song.artist
        popup_song_album.text = song.collectionName
        popup_song_duration.text = song.trackTime
        popup_song_genre.text = song.genre

        popup_song_image.setImageResource(R.drawable.logo)
        DownloaderFactory.getDownloader(
            Downloaders.ASYNC_TASK
        ).loadImage(popup_song_image, song.imagePath)

        val anim = AnimationUtils.loadAnimation(applicationContext, R.anim.popup_in)
        anim.interpolator = DecelerateInterpolator()
        popup_content.startAnimation(anim)
    }

    private fun hideDetailPopup() {
        val popupAnim = AnimationUtils.loadAnimation(applicationContext, R.anim.popup_out)
        popupAnim.interpolator = DecelerateInterpolator()
        popup_content.startAnimation(popupAnim)

        val animOut = AnimationUtils.loadAnimation(applicationContext, R.anim.fade_out)
        animOut.fillAfter = true
        animOut.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationRepeat(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                fake_popup.visibility = View.GONE
                fake_popup.animation = null
            }
        })
        fake_popup.startAnimation(animOut)
    }

    override fun showSongs(songs: MutableList<SongEntity>) {
        recyclerView.visibility = View.VISIBLE
        errorContainer.visibility = View.GONE
        adapter = SongsAdapter(songs, this)
        recyclerView.adapter = adapter
        swipe_container.isRefreshing = false
    }

    override fun showEmptyView() {
        recyclerView.visibility = View.GONE
        errorContainer.visibility = View.VISIBLE
        movieErrorMessage.visibility = View.GONE
        swipe_container.isRefreshing = false
    }

    override fun showError() {
        if (adapter != null) {
            Toast.makeText(this, getString(R.string.server_error_message), Toast.LENGTH_SHORT).show()
        } else {
            recyclerView.visibility = View.GONE
            errorContainer.visibility = View.VISIBLE
            movieErrorMessage.visibility = View.VISIBLE
        }

        swipe_container.isRefreshing = false
    }

    private fun configureRecycleView() {
        manager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = manager
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context, DividerItemDecoration.HORIZONTAL
            )
        )
    }

    private fun configureListeners() {
        fake_popup.setOnClickListener { hideDetailPopup() }
    }

    private fun configurePullToRefresh() {
        swipe_container.setOnRefreshListener(this)
        swipe_container.setColorSchemeResources(R.color.colorAccent)
    }

    private fun loadSongs() {
        swipe_container.isRefreshing = true
        presenter.getSongs()
    }

    override fun onBackPressed() {
        if (fake_popup.visibility == View.VISIBLE) {
            hideDetailPopup()
        } else {
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
}