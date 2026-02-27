package com.example.noteapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.adapter.NotesAdapter
import com.example.noteapp.dataroom.DataBaseHandler
import com.example.noteapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var dataBase: DataBaseHandler
    private lateinit var adapter : NotesAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dataBase = DataBaseHandler.getDataBase(this)

        initRecycler()

        binding.imdAdd.setOnClickListener {

            val intent = Intent(this, AddNotesActivity::class.java)
            intent.putExtra("newNotes" , true)
            startActivity(intent)

        }
        binding.textRecycleBin.setOnClickListener {
            startActivity(Intent(this, RecyclerBinActivity::class.java))

        }

    }

    //   هر وقت به اکتیویتی بعدی رفتیم و برگشتیم
    // با استفاده از onStart() فقط دادهای جدید رو به کاربر نمایش میدیم
//    override fun onStart() {
//        super.onStart()
//        val data = dataBase.noteDao().getNoteForRecycler(DataBaseHandler.FALSE_DELETE)
//        adapter.changeData(data)
//
//    }

    // برای عملکرد بهتر برنامه
    // فقط یکبار initRecycler() رو فراخونی  مکنیم
    private fun initRecycler() {

        adapter = NotesAdapter(this, dataBase)
        binding.recyclerNote.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        binding.recyclerNote.adapter = adapter

    }
}