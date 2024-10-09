package com.example.alphaquiz

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.example.alphaquiz.databinding.ProgressDialogBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Date

object Utils {

    private var dialog: AlertDialog?=null
    private val auth= FirebaseAuth.getInstance()

    fun showDialog(context: Context, message:String){
        val process= ProgressDialogBinding.inflate(LayoutInflater.from(context))
        process.text.text=message.toString()

        dialog= AlertDialog.Builder(context).setView(process.root).setCancelable(false).create()
        dialog!!.show()
    }

    fun hideDialog(){
        dialog!!.dismiss()
    }

    fun currentUserId(): String {
        return auth.currentUser!!.uid
    }

    suspend fun getCurrentUserName(): String? {
        val currentUser = FirebaseAuth.getInstance().currentUser
        return if (currentUser != null) {
            val userId = currentUser.uid
            val databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userId).child("name")
            try {
                val snapshot = databaseReference.get().await()
                snapshot.getValue(String::class.java)

            } catch (e: Exception) {
                // Handle the error if needed
                null
            }
        } else {
            null
        }
    }

    fun getTime(): String {


        val formatter = SimpleDateFormat("HH:mm:ss")
        val date: Date = Date(System.currentTimeMillis())
        val stringdate = formatter.format(date)


        return stringdate

    }
}