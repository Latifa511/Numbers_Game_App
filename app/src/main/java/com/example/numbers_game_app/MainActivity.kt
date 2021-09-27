package com.example.numbers_game_app
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
     lateinit var clayout: ConstraintLayout
     lateinit var gField: EditText
     lateinit var gButton: Button
     lateinit var messages: ArrayList<String>
     lateinit var tvProm: TextView

     var answer = 0
     var guesses = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var rvMessages = findViewById<RecyclerView>(R.id.rvMessages)

        answer = Random.nextInt(10)

        clayout = findViewById(R.id.clayout)
        messages = ArrayList()

        tvProm = findViewById(R.id.tvProm)

        rvMessages.adapter = MessageAdapter(this, messages)
        rvMessages.layoutManager = LinearLayoutManager(this)

        gField = findViewById(R.id.etGuField)
        gButton = findViewById(R.id.btGuButton)

        gButton.setOnClickListener { addMessg() }
    }

     fun addMessg(){
        val msg = gField.text.toString()
        if(msg.isNotEmpty()){
            if(guesses > 0){
                if(msg.toInt() == answer){
                    disableEntry()
                    showAlertDialog("You win!\n\nPlay again?")
                }else{
                    guesses--
                    messages.add("You guessed $msg")
                    messages.add("You have $guesses guesses left")
                }
                if(guesses == 0){
                    disableEntry()
                    messages.add("You lose - The correct answer was $answer")
                    messages.add("Game Over")
                    showAlertDialog("You lose...\nThe correct answer was $answer.\n\nPlay again?")
                }
            }
            gField.text.clear()
            gField.clearFocus()
            rvMessages.adapter?.notifyDataSetChanged()
        }else{
            Snackbar.make(clayout, "Please enter a number", Snackbar.LENGTH_LONG).show()
        }
    }

     fun disableEntry(){
        gButton.isEnabled = false
        gButton.isClickable = false
        gField.isEnabled = false
        gField.isClickable = false
    }

     fun showAlertDialog(title: String) {

        val dialogBuild = AlertDialog.Builder(this)


         dialogBuild.setMessage(title)

            .setCancelable(false)
            .setPositiveButton("Yes", DialogInterface.OnClickListener {
                    dialog, id -> this.recreate()
            })
            .setNegativeButton("No", DialogInterface.OnClickListener {
                    dialog, id -> dialog.cancel()
            })

        val alert = dialogBuild.create()

        alert.setTitle("Game Over")

        alert.show()
    }
}



