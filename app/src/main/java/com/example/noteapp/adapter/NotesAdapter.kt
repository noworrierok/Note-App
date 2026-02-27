package com.example.noteapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.R
import com.example.noteapp.dataroom.DataBaseHandler
import com.example.noteapp.dataroom.NoteEntity
import com.example.noteapp.databinding.ItemListNoteBinding
import com.example.noteapp.ui.AddNotesActivity

class NotesAdapter(
    private val context: Context,
    private val dao : DataBaseHandler

): RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    private var allData : ArrayList<NoteEntity>

    init {
        // revieve at List
        val noteList: List<NoteEntity> = dao.noteDao().getNoteForRecycler(DataBaseHandler.FALSE_DELETE)
        // chacge to ArrayList
        allData = ArrayList(noteList)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =

        NotesViewHolder(
            ItemListNoteBinding.inflate(LayoutInflater.from(context), parent, false)
        )

    override fun getItemCount() = allData.size

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {

        holder.setData(allData[position])
    }

    inner class NotesViewHolder(
        private val binding: ItemListNoteBinding,

        ): RecyclerView.ViewHolder(binding.root){

        fun setData(data : NoteEntity){

            binding.textTitleNotes.text = data.title

            binding.imgDeleteNotes.setOnClickListener {

                AlertDialog.Builder(ContextThemeWrapper(context, R.style.AlertDialogCustom))
                    .setTitle("انتقال به سطل زباله")
                    .setMessage("آیا میخواهید این یادداشت به سطل زباله منتقل شود")
                    .setIcon(R.drawable.ic_delete)
                    .setNegativeButton("بله"){_,_ ->

                        val result = dao.noteDao().editNote(data.id, DataBaseHandler.TRUE_DELETE)
                        if (result > 0){
                            showText("یادداشت به سطل زباله منتقل شد")
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

            binding.root.setOnClickListener {
                val intent = Intent(context, AddNotesActivity::class.java)
                intent.putExtra("notesId", data.id)
                context.startActivity(intent)
            }

        }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun changeData(data : ArrayList<NoteEntity>){
        allData = data
        // این کار باعث میشه داده ها بعد هر فعالیتی reLoad بشن
        // برنامه از شرایط بهینه فاصله میگیره اما مجبورم
        notifyDataSetChanged()

        // برای بهینه سازی اگه واقعا ایتمی اضافه شده تغییرات رو اعمال کن
//        if (dataroom.size > allData.size){
//            allData = dataroom
//            // چرا allData.size رو بعنوان پوزیشن میدیم ؟
//            // چون پوزیشن اخرین ایتم از دیتای قدیمی
//            // همون پوزیشنی هست که بهش نیاز دارم
//            notifyItemInserted(allData.size) //
//        }

    }
    private fun showText(text : String){
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
}