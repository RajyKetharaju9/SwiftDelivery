package com.humber.lab5

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {

    private lateinit var preferenceManager: PreferenceManager
    private lateinit var txtOrderId: TextView
    private lateinit var txtAddress: TextView
    private lateinit var txtStatus: TextView
    private lateinit var btnUpdateStatus: Button
    private lateinit var btnDetails: Button
    private lateinit var edtOrderId: EditText
    private lateinit var btnFetchOrder: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        preferenceManager = PreferenceManager(this)

        txtOrderId = findViewById(R.id.txtOrderId)
        txtAddress = findViewById(R.id.txtAddress)
        txtStatus = findViewById(R.id.txtStatus)
        btnUpdateStatus = findViewById(R.id.btnUpdateStatus)
        btnDetails = findViewById(R.id.btnDetails)
        edtOrderId = findViewById(R.id.edtOrderId)
        btnFetchOrder = findViewById(R.id.btnFetchOrder)

        loadOrderData()

        btnUpdateStatus.setOnClickListener {
            preferenceManager.saveOrder("987", "48 viewcrest circle, Etobicoke", "Delivered")
            loadOrderData()
            Toast.makeText(this, "Order Status Updated!", Toast.LENGTH_SHORT).show()
        }

        btnDetails.setOnClickListener {
            val intent = Intent(this, DeliveryDetailsActivity::class.java)
            val (orderId, address, status) = preferenceManager.getOrder()
            intent.putExtra("orderId", orderId)
            intent.putExtra("address", address)
            intent.putExtra("status", status)
            startActivity(intent)
        }

        btnFetchOrder.setOnClickListener {
            val inputOrderId = edtOrderId.text.toString()
            val (savedOrderId, savedAddress, savedStatus) = preferenceManager.getOrder()

            if (inputOrderId == savedOrderId) {
                txtOrderId.text = "Order ID: $savedOrderId"
                txtAddress.text = "Address: $savedAddress"
                txtStatus.text = "Status: $savedStatus"
                Toast.makeText(this, "Order Found!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Order Not Found!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadOrderData() {
        val (orderId, address, status) = preferenceManager.getOrder()

        if (orderId == "No Order") {
            preferenceManager.saveOrder("987", "48 viewcrest circle, Etobicoke", "Delivered")
        }

        txtOrderId.text = "Order ID: $orderId"
        txtAddress.text = "Address: $address"
        txtStatus.text = "Status: $status"
    }
}
