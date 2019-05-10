package com.uchoa.shuffle.songs.adapters

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.BounceInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import android.widget.TextView
import com.uchoa.shuffle.songs.R
import com.uchoa.shuffle.songs.callbacks.ItemAdapterCallback
import com.uchoa.shuffle.songs.domain.entities.SongEntity
import com.uchoa.shuffle.songs.helpers.DownloaderFactory
import com.uchoa.shuffle.songs.helpers.Downloaders
import java.util.*

class SongsAdapter(
    songs: MutableList<SongEntity>,
    private var callback: ItemAdapterCallback
) : RecyclerView.Adapter<SongsAdapter.MovieViewHolder>() {

    private var context: Context? = null
    private var moviesDisplayed: MutableList<SongEntity> = songs

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        context = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.song_item, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(moviesDisplayed[position])
    }

    override fun getItemCount(): Int {
        return moviesDisplayed.size
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var trackName: TextView = itemView.findViewById(R.id.item_song_name)
        private var artistName: TextView = itemView.findViewById(R.id.item_song_artist)
        private var imageUrl: ImageView = itemView.findViewById(R.id.item_song_image)

        fun bind(song: SongEntity) {

            trackName.text = song.name
            artistName.text = "${song.artist} (${song.genre})"
            imageUrl.setImageResource(R.color.black_transparent_20)

            itemView.setOnClickListener {
                callback.onCellClick(song)
            }

            /**
             * Note: Cleiton Uchoa
             * Switching between downloaders just for fun! :)
             */
            val random = Random().nextInt(3)

            when (random) {
                0 -> DownloaderFactory.getDownloader(Downloaders.GLIDE).loadImage(imageUrl, song.imagePath)
                1 -> DownloaderFactory.getDownloader(Downloaders.PICASSO).loadImage(imageUrl, song.imagePath)
                else -> DownloaderFactory.getDownloader(Downloaders.ASYNC_TASK).loadImage(imageUrl, song.imagePath)
            }

            val view : ConstraintLayout = itemView.findViewById(R.id.adapter_item)
            val anim = AnimationUtils.loadAnimation(itemView.context, R.anim.fade_in)
            anim.interpolator = DecelerateInterpolator()
            view.startAnimation(anim)
        }
    }
}