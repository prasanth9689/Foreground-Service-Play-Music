package com.skyblue.foregroundserviceplaymusic

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.skyblue.foregroundserviceplaymusic.MainActivity.GalleryPicture

class CustomAdapter(private val mList: List<GalleryPicture>,
                    private val context: MainActivity) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_model, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ItemsViewModel = mList[position]

        // sets the image to the imageview from our itemHolder class
       // holder.imageView.setImageURI(Uri.parse(ItemsViewModel.path))
        holder.imageView.setImageResource(R.drawable.ic_music);

        // sets the text to the textview from our itemHolder class
        holder.textView.text = ItemsViewModel.name

        holder.main.setOnClickListener{
            val intent = Intent(context, MusicPlayerActivity::class.java)
            intent.putExtra("uri", ItemsViewModel.path)
            intent.putExtra("name", ItemsViewModel.name)
            context.startActivity(intent)

        }
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageview)
        val textView: TextView = itemView.findViewById(R.id.textView)
        val main: CardView = itemView.findViewById(R.id.main)
    }
}