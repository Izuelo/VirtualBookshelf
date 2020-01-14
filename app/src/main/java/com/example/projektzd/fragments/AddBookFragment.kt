package com.example.projektzd.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.fragment.app.Fragment


import com.example.projektzd.database.DatabaseHelper

import android.app.AlertDialog
import android.app.DatePickerDialog
import androidx.databinding.DataBindingUtil

import com.example.projektzd.databinding.FragmentAddbookBinding
//
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*


class AddBookFragment : Fragment() {
    lateinit var binding: FragmentAddbookBinding
    lateinit var dbHelper: DatabaseHelper
    var startDateString: String = " "
    var returnDateString: String = " "

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        dbHelper = DatabaseHelper(activity)

        binding =
            DataBindingUtil.inflate(
                inflater,
                com.example.projektzd.R.layout.fragment_addbook,
                container,
                false
            )

//        handlePickDate()
//        handleInserts()
//        handleViewing()
//        handleDelete()
//        handleUpdate()
//
//
//        binding.nameTxt.addTextChangedListener(watcher)

        return binding.root
    }

//    var watcher: TextWatcher = object : TextWatcher {
//
//        override fun beforeTextChanged(
//            s: CharSequence,
//            start: Int,
//            count: Int,
//            after: Int
//        ) {
//        }
//
//        override fun onTextChanged(
//            s: CharSequence,
//            start: Int,
//            before: Int,
//            count: Int
//        ) {
//
//            insertBtn.isEnabled =
//                (nameTxt.text.toString().isNotEmpty())
//        }
//
//        override fun afterTextChanged(s: Editable?) {
//        }
//
//    }
//
//    fun handlePickDate() {
//
//        val c = Calendar.getInstance()
//        val year = c.get(Calendar.YEAR)
//        val month = c.get(Calendar.MONTH)
//        val day = c.get(Calendar.DAY_OF_MONTH)
//        val sdf = SimpleDateFormat("dd-MM-yyyy")
//        rentalDateString = sdf.format(Date())
//        binding.pickDateBtn.setOnClickListener {
//            val dpd = activity?.let { it1 ->
//                DatePickerDialog(
//                    it1,
//                    DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
//                        c.set(Calendar.YEAR, mYear)
//                        c.set(Calendar.MONTH, mMonth)
//                        c.set(Calendar.DAY_OF_MONTH, mDay)
//                        Log.i("XDXDXD", c.time.toString())
//                        rentalDateString = sdf.format(c.time)
//                    },
//                    year,
//                    month,
//                    day
//                )
//            }
//            if (dpd != null) {
//                dpd.show()
//            }
//        }
//
//        returnDateString = sdf.format(Date())
//        binding.returnDateBtn.setOnClickListener {
//            val dpd = activity?.let { it1 ->
//                DatePickerDialog(
//                    it1,
//                    DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
//                        c.set(Calendar.YEAR, mYear)
//                        c.set(Calendar.MONTH, mMonth)
//                        c.set(Calendar.DAY_OF_MONTH, mDay)
//                        Log.i("XDXDXD", c.time.toString())
//                        returnDateString = sdf.format(c.time)
//                    },
//                    year,
//                    month,
//                    day
//                )
//            }
//            if (dpd != null) {
//                dpd.show()
//            }
//        }
//    }
//
//    //
//    fun handleInserts() {
//
//        binding.insertBtn.isEnabled = false
//        binding.insertBtn.setOnClickListener {
//            try {
//
//                dbHelper.insertData(nameTxt.text.toString(), rentalDateString, returnDateString)
//
//
//                nameTxt.setText("")
//                idTxt.setText("")
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//        }
//
//    }
//
//    fun handleViewing() {
//        binding.viewBtn.setOnClickListener(
//            View.OnClickListener {
//                val buffer = StringBuffer()
//                val res = dbHelper.allData
//                if (res.count == 0) {
//                    return@OnClickListener
//                }
//                val formater = DateTimeFormatter.ofPattern("dd-MM-yyyy")
//                val localDate: LocalDateTime = LocalDateTime.now()
//
//                var sysDate: LocalDate =
//                    LocalDate.of(localDate.year, localDate.monthValue, localDate.dayOfMonth)
//
//                while (res.moveToNext()) {
//                    val valDate: LocalDate =
//                        LocalDate.parse(res.getString(3), formater)
//                    val days = ChronoUnit.DAYS.between(sysDate, valDate)
//
//                    buffer.append("ID :" + res.getString(0) + "\n")
//                    buffer.append("NAME :" + res.getString(1) + "\n")
//                    buffer.append("DATA_WYP :" + res.getString(2) + "\n")
//                    buffer.append("DATA_ODDANIA :" + res.getString(3) + "\n")
//                    buffer.append("DNI_DO_ODDANIA :$days\n")
//                }
//                showDialog("Lista Książek", buffer.toString())
//            }
//        )
//    }
//
//    fun showDialog(title: String, Message: String) {
//        val builder = AlertDialog.Builder(activity)
//        builder.setCancelable(true)
//        builder.setTitle(title)
//        builder.setMessage(Message)
//        builder.show()
//    }
//
//
//    fun handleDelete() {
//        binding.deleteBtn.setOnClickListener {
//            dbHelper.deleteData(nameTxt.text.toString())
//            nameTxt.text.clear()
//            idTxt.text.clear()
//        }
//    }
//
//    fun handleUpdate() {
//        binding.updateBtn.setOnClickListener {
//            val tof: Boolean = dbHelper.updateData(
//                idTxt.text.toString(),
//                rentalDateString,
//                returnDateString
//            )
//            nameTxt.text.clear()
//            idTxt.text.clear()
//        }
//    }
//
//    fun showToast(text: String) {
////        Toast.makeText(this@AddBookFragment, text, Toast.LENGTH_LONG).show()
//    }
}
