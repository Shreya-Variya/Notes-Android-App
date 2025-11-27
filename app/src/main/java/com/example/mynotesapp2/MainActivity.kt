package com.example.mynotesapp2

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mynotesapp2.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
     var dataAdapter: DataAdapter? = null
     var data: ArrayList<Data> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initAdapter()

//        val data = listOf(
//            Data(1,1,"hello","Hello world from Shreya!!"),
//            Data(2,2,"hello","Hello world from Vidhi!!"),
//            Data(3,3,"hello","Hello world from Diya!!"),
//            Data(4,4,"hello","Hello world from Khushi!!"),
//            Data(1,1,"hello","Hello world from Shreya!!"),
//            Data(2,2,"hello","Hello world from Vidhi!!"),
//            Data(3,3,"hello","Hello world from Diya!!"),
//            Data(4,4,"hello","Hello world from Khushi!!")
//        )


        GlobalScope.launch(Dispatchers.IO) {
            val response = try {
                ApiObject.api.getData()
            }
            catch (e: IOException){
                Toast.makeText(applicationContext, "App Error : ${e.message}", Toast.LENGTH_SHORT).show()
                return@launch
            }
            catch (e: HttpException){
                Toast.makeText(applicationContext, "Http Error : ${e.message}", Toast.LENGTH_SHORT).show()
                return@launch
            }
            if (response.isSuccessful && response.body() != null){
                withContext(Dispatchers.Main){
                    data.clear()
                    data.addAll(response.body()!!)
                    binding.rcView.apply {
                       adapter?.notifyDataSetChanged()
                    }
                }
            }
        }

//        dataAdapter = DataAdapter(data)
//        binding.rcView.layoutManager = LinearLayoutManager(this)
//        binding.rcView.adapter = dataAdapter
    }

    private fun initAdapter() {
        dataAdapter = DataAdapter(data)
        val layoutManager = LinearLayoutManager(this@MainActivity)
        binding.rcView.layoutManager = layoutManager
        binding.rcView.setHasFixedSize(true)
        binding.rcView.adapter = dataAdapter
    }
}