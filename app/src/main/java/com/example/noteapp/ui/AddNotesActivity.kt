package com.example.noteapp.ui

import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.noteapp.databinding.ActivityAddNotesBinding
import com.example.noteapp.dataroom.DataBaseHandler
import com.example.noteapp.dataroom.NoteEntity
import com.example.noteapp.utility.PersianDate

class AddNotesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddNotesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNotesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val type = intent.getBooleanExtra("newNotes", false)
        val id = intent.getIntExtra("notesId", 0)

        val dataAccessObject = DataBaseHandler.getDataBase(this)

        if (type) {
            binding.txtDate.text = getDate()
        } else {
            val note = dataAccessObject.noteDao().getNoteById(id)
            val edit = Editable.Factory()
            binding.editTitleNotes.text = edit.newEditable(note.title)
            // میخوام مقداری که از دیتا بیس دریافت میکنم بصورت یک متن قابل ویرایش
            // داخل ویو نمایش داده بشه
            binding.editDetailNotes.text = Editable.Factory().newEditable(note.detail)
            binding.txtDate.text = note.date
        }



        binding.btnSave.setOnClickListener {

            val title = binding.editTitleNotes.text.toString()
            val detail = binding.editDetailNotes.text.toString()

            if (title.isNotEmpty()) {
                val notes = NoteEntity(0, title, detail, DataBaseHandler.FALSE_DELETE, getDate())

                //  در صورت true یا false  بودن جنس type یک متد اجرا میشه
                //  نتیجه ی اجرای متد داخل result ذخیره میشه

                val result =
                    if (type)         // اگر داده ها برای یک یاداشت جدیده باید یک رکورد جدید ثبت بشه
                        dataAccessObject.noteDao().insertNote(notes)
                    else                  // در غیر این صورت  داده های موجود فقط باید  روی همان رکورد ویرایش بشن
                        dataAccessObject.noteDao().updateNote(notes)


//                if (result > 0) {
//                    showToast("یادداشت با موفقیت ذخیره شد")
//                    finish()
//                } else {
//                    showToast("خطا در ذخیره سازی یادداشت")
//                }
            } else {
                showToast("عنوان نمیتواند خالی باشد")
            }

        }

        binding.btnCancel.setOnClickListener { finish() }


    }

    private fun getDate(): String {
        // با استفاده از کلاس PersianDate تاریخ شمسی و ساعت کنونی را بدست می آوریم
        val date = PersianDate()

        // این کد تاریخ شمسی فعلی را به رشته تبدیل میکند
        val currentDate = "${date.year}/${date.month}/${date.day}"
        // این کد ساعت و دقیقه و ثانیه فعلی را به رشته تبدیل میکند
        val currentTime = "${date.hour}:${date.min}:${date.second}"

        // در اینجا ساعت و تاریخ را به هم چسبانده و return میکنیم
        return "$currentDate     |     $currentTime"

    }

    private fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }
}