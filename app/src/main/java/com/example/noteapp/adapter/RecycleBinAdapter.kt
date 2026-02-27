package com.example.noteapp.adapter

import android.content.Context
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.R
import com.example.noteapp.dataroom.DataBaseHandler
import com.example.noteapp.dataroom.NoteEntity
import com.example.noteapp.databinding.ItemListRecycleBinBinding


class RecycleBinAdapter(
    private val context: Context,
    private val dao : DataBaseHandler

): RecyclerView.Adapter<RecycleBinAdapter.RecycleBinViewHolder>() {
    private val noteList: List<NoteEntity> = dao.noteDao().getNoteForRecycler(DataBaseHandler.FALSE_DELETE)
    // chacge to ArrayList
    private val allData = ArrayList(noteList)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =

        RecycleBinViewHolder(
            ItemListRecycleBinBinding.inflate(LayoutInflater.from(context), parent, false)
        )

    override fun getItemCount() = allData.size

    override fun onBindViewHolder(holder: RecycleBinViewHolder, position: Int) {

        holder.setData(allData[position])
    }

    inner class RecycleBinViewHolder(
        private val binding: ItemListRecycleBinBinding,

        ): RecyclerView.ViewHolder(binding.root){

        fun setData(data : NoteEntity){

            binding.textTitleNotes.text = data.title

            binding.imgDeleteNotes.setOnClickListener {

                AlertDialog.Builder(ContextThemeWrapper(context, R.style.AlertDialogCustom))
                    .setTitle("حذف یادداشت")
                    .setMessage("آیا میخواهید این یادداشت برای همیشه حذف شود")
                    .setIcon(R.drawable.ic_delete)
                    .setNegativeButton("بله"){_,_ ->

                        val result = dao.noteDao().deleteNoteById(data.id)
                        if (result > 0){
                            showText("یادداشت حذف شد")
                            allData.removeAt(adapterPosition)
                            notifyItemRemoved(adapterPosition)

                        }else{
                            showText("عملیات حذف ناموفق بود")
                        }


                    }
                    .setPositiveButton("خیر"){_,_ ->

                    }
                    .create()
                    .show()

            }

            binding.imgRestoreNotes.setOnClickListener {

                AlertDialog.Builder(ContextThemeWrapper(context, R.style.AlertDialogCustom))
                    .setTitle("بازگردانی یادداشت")
                    .setMessage("آیا میخواهید این یادداشت به بازگردانده شود")
                    .setIcon(R.drawable.ic_restore)
                    .setNegativeButton("بله"){_,_ ->

                        val result = dao.noteDao().editNote(data.id, DataBaseHandler.FALSE_DELETE)
                        if (result > 1){
                            showText("یادداشت به صفحه ی اصلی منتقل شد")
                            allData.removeAt(adapterPosition)
                            notifyItemRemoved(adapterPosition)

                        }else{
                            showText("عملیات انتقال ناموفق بود")
                        }


                    }
                    .setPositiveButton("خیر"){_,_ ->

                    }
                    .create()
                    .show()

            }


        }

    }

    private fun showText(text : String){
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
}