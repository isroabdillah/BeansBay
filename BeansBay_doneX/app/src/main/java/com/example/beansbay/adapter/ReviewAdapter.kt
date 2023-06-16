package com.example.beansbay.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.beansbay.R
import com.example.beansbay.network.Reviews
import java.util.*


class ReviewAdapter(private val listReview : List<Reviews>):RecyclerView.Adapter<ReviewAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvUserName : TextView = view.findViewById(R.id.nama_reviewer)
        val tvReview : TextView = view.findViewById(R.id.isi_review)
        val tvDate : TextView = view.findViewById(R.id.tanggal_review)
        val ivRating1 : ImageView = view.findViewById(R.id.star_rating_1)
        val ivRating2 : ImageView = view.findViewById(R.id.star_rating_2)
        val ivRating3 : ImageView = view.findViewById(R.id.star_rating_3)
        val ivRating4 : ImageView = view.findViewById(R.id.star_rating_4)
        val ivRating5 : ImageView = view.findViewById(R.id.star_rating_5)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_review, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

//        val date = Date(listReview[position].createdAt.toString())
        holder.tvUserName.text = listReview[position].userId
//        holder.tvDate.text = date.toString()
        holder.tvReview.text = listReview[position].comment

        if(listReview[position].rating == 4){
            holder.ivRating5.setImageResource(R.drawable.ic_baseline_star_border_24)
        }

        if(listReview[position].rating == 3) {
            holder.ivRating5.setImageResource(R.drawable.ic_baseline_star_border_24)
            holder.ivRating4.setImageResource(R.drawable.ic_baseline_star_border_24)
        }

        if(listReview[position].rating == 2) {
            holder.ivRating5.setImageResource(R.drawable.ic_baseline_star_border_24)
            holder.ivRating4.setImageResource(R.drawable.ic_baseline_star_border_24)
            holder.ivRating3.setImageResource(R.drawable.ic_baseline_star_border_24)
        }

        if(listReview[position].rating == 1) {
            holder.ivRating5.setImageResource(R.drawable.ic_baseline_star_border_24)
            holder.ivRating4.setImageResource(R.drawable.ic_baseline_star_border_24)
            holder.ivRating3.setImageResource(R.drawable.ic_baseline_star_border_24)
            holder.ivRating2.setImageResource(R.drawable.ic_baseline_star_border_24)
        }

        if(listReview[position].rating == 0) {
            holder.ivRating5.setImageResource(R.drawable.ic_baseline_star_border_24)
            holder.ivRating4.setImageResource(R.drawable.ic_baseline_star_border_24)
            holder.ivRating3.setImageResource(R.drawable.ic_baseline_star_border_24)
            holder.ivRating2.setImageResource(R.drawable.ic_baseline_star_border_24)
            holder.ivRating1.setImageResource(R.drawable.ic_baseline_star_border_24)
        }
    }

    override fun getItemCount(): Int = listReview.size
}