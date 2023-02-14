package com.example.barcodescanner

import android.content.ActivityNotFoundException
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.barcodescanner.databinding.ActivityMainBinding
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnScan.setOnClickListener {
            val intent = Intent("com.google.zxing.client.android.SCAN")
            intent.putExtra("SCAN_MODE", "PRODUCT_MODE")

            try {
                activityResultLauncher.launch(intent)
            }catch (e: ActivityNotFoundException){
                Toast.makeText(this, "Scanner nicht installiert", Toast.LENGTH_SHORT).show()
            }
        }

        activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            runBlocking {
                if(it.resultCode == RESULT_OK && it.data !=null){
                    binding.tvStatus.text = it.data!!.getStringExtra("SCAN_RESULT_FORMAT")
                    binding.tvResult.text = it.data!!.getStringExtra("SCAN_RESULT")
                }
            }
        }
    }
}