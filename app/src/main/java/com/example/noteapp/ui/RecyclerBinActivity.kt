package com.example.noteapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.adapter.RecycleBinAdapter
import com.example.noteapp.dataroom.DataBaseHandler
import com.example.noteapp.databinding.ActivityRecyclerBinBinding

class RecyclerBinActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecyclerBinBinding
    private lateinit var dao : DataBaseHandler
    private lateinit var adapter: RecycleBinAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecyclerBinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecycler()

    }

    private fun initRecycler() {

        dao = DataBaseHandler.getDataBase(this)
        adapter = RecycleBinAdapter(this, dao)

        binding.recyclerNote.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        binding.recyclerNote.adapter = adapter

    }
}