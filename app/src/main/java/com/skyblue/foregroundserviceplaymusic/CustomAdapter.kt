package com.skyblue.foregroundserviceplaymusic

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.skyblue.foregroundserviceplaymusic.MainActivity.GalleryPicture
import com.skyblue.foregroundserviceplaymusic.databinding.RowModelBinding

class CustomAdapter(private val mList: List<GalleryPicture>,
                    private val context: MainActivity) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RowModelBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ItemsViewModel = mList[position]
       /*
         For image : holder.imageView.setImageURI(Uri.parse(ItemsViewModel.path))
        */
        holder.binding.imageview.setImageResource(R.drawable.ic_music);
        holder.binding.textView.text = ItemsViewModel.name

        holder.binding.main.setOnClickListener{
            val intent = Intent(context, MusicPlayerActivity::class.java)
            intent.putExtra("uri", ItemsViewModel.path)
            intent.putExtra("name", ItemsViewModel.name)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(val binding: RowModelBinding) : RecyclerView.ViewHolder(binding.root)
}