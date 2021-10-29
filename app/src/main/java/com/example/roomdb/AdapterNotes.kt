package com.example.roomdb

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_notes.view.*

class AdapterNotes:RecyclerView.Adapter<AdapterNotes.ViewHolder>() {
    private var notes= emptyList<Notes>()

    var onItemClick:((Notes)->Unit)?=null

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterNotes.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_notes,parent,false)
        )
    }

    override fun onBindViewHolder(holder: AdapterNotes.ViewHolder, position: Int) {
        val currentItem=notes[position]
        holder.itemView.tvNoteTitle.text=currentItem.noteTitle
        holder.itemView.tvNoteDescription.text=currentItem.noteDescription

        holder.itemView.setOnClickListener{
            onItemClick?.invoke(notes[position])
        }
    }

    override fun getItemCount(): Int {
     return notes.size
    }

    fun setData(notes: List<Notes>){
        this.notes=notes
        notifyDataSetChanged()
    }
}