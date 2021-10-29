package com.example.roomdb

import android.app.AlertDialog
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter=AdapterNotes()
        recyclerVW.adapter=adapter
        recyclerVW.layoutManager=LinearLayoutManager(this)

        viewModel=ViewModelProvider(this).get(ViewModel::class.java)
        viewModel.readAllNotes.observe(this,{notes ->adapter.setData(notes)})

        Log.d("MainActivity","OnCreate")

        fabAddNewNote.setOnClickListener{
            Log.d("MainActivity","OnClickListener")
            showAddNoteDialog()
        }

        adapter.onItemClick={notes -> showActionDialog(notes) }
    }

    private fun showActionDialog(notes: Notes){
        val builder=AlertDialog.Builder(this)

        builder.setTitle("Select actions")
        builder.setPositiveButton("Delete"){_,_->
            viewModel.deleteData(notes)
        }
        builder.setNegativeButton("Update"){_,_->
            showUpdateDialog(notes)
        }
        builder.setNeutralButton("Cancel"){_,_->
            Toast.makeText(this,"Cancel",Toast.LENGTH_SHORT).show()
        }

        builder.create().show()
    }
private fun showUpdateDialog(notes: Notes){

    val dialog=Dialog(this)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.setContentView(R.layout.dialog_update_new_note)
    dialog.setCancelable(true)

    val layoutParams=WindowManager.LayoutParams()
    layoutParams.copyFrom(dialog.window!!.attributes)
    layoutParams.width=WindowManager.LayoutParams.MATCH_PARENT
    layoutParams.height=WindowManager.LayoutParams.WRAP_CONTENT

    val etnoteTitle:EditText=dialog.findViewById(R.id.etnoteTitle)
    val etnoteDescription:EditText=dialog.findViewById(R.id.etnoteDescription)

    etnoteTitle.setText(notes.noteTitle)
    etnoteDescription.setText(notes.noteDescription)


    dialog.findViewById<Button>(R.id.btnCancel).setOnClickListener{
        Log.d("MainActivity","cancel button ")

        dialog.dismiss()
    }


    dialog.findViewById<Button>(R.id.btnUpdateNote).setOnClickListener{
        if (inputCheck(etnoteTitle.text.toString(), etnoteDescription.text.toString()))
        {
            Log.d("MainActivity","store")
            val notes =
                Notes(notes.id, etnoteTitle.text.toString(), etnoteDescription.text.toString())
            viewModel.updateData(notes)
            //Toast.makeText(this,etnoteTitle.text.toString()+"\n"+etnoteDescription.text.toString(),Toast.LENGTH_LONG).show()
            Toast.makeText(this,"Data updated",Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        else{
            Log.d("MainActivity","Toast generate")
            Toast.makeText(this,"its empty",Toast.LENGTH_SHORT).show()
        }
    }

    dialog.show()
    dialog.window!!.attributes=layoutParams
}

    private fun showAddNoteDialog() {
        Log.d("MainActivity","showAddNote")

        val dialog=Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_add_new_note)
        dialog.setCancelable(true)

        val layoutParams=WindowManager.LayoutParams()
        layoutParams.copyFrom(dialog.window!!.attributes)
        layoutParams.width=WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height=WindowManager.LayoutParams.WRAP_CONTENT

        val etnoteTitle:EditText=dialog.findViewById(R.id.etnoteTitle)
        val etnoteDescription:EditText=dialog.findViewById(R.id.etnoteDescription)

        dialog.findViewById<Button>(R.id.btnCancel).setOnClickListener{
            Log.d("MainActivity","cancel button ")

            dialog.dismiss()
        }

        dialog.findViewById<Button>(R.id.btnAddNote).setOnClickListener{
            Log.d("MainActivity","Add button")

            if (inputCheck(etnoteTitle.text.toString(), etnoteDescription.text.toString()))
            {
                Log.d("MainActivity","store")
                        val notes =
                        Notes(0, etnoteTitle.text.toString(), etnoteDescription.text.toString())
                        viewModel.addData(notes)
                //Toast.makeText(this,etnoteTitle.text.toString()+"\n"+etnoteDescription.text.toString(),Toast.LENGTH_LONG).show()
                Toast.makeText(this,"Data Aded",Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
            else{
                Log.d("MainActivity","Toast generate")
                Toast.makeText(this,"its empty",Toast.LENGTH_SHORT).show()
            }
        }
        dialog.show()
        dialog.window!!.attributes=layoutParams
    }

    private fun inputCheck(noteTitle: String,noteDescription: String): Boolean {
        return !(TextUtils.isEmpty(noteTitle) && TextUtils.isEmpty(noteDescription))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.delete_all,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId==R.id.deleteAllNotes){
            viewModel.deleteAllData()
        }
        return super.onOptionsItemSelected(item)
    }

}