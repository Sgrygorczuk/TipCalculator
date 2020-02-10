package com.orczukproduction.tipcalculator

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity()  {

    //Variables that keep track of everything
    private val logicUnit = MainLogic() //Main Logic or the parsing function which stores and calculates
                                        //all of the information that is being displayed to the user
    private var upDownState = ""        //Keeps track of which button is pressed so that if it's clicked again it
                                        //can be drawn as inactive

    /*
    Input: Bundle
    Output: Void
    Purpose: Connects the layout to all the functions as well as keeps track of the SeekBars progress
        updating the UI based on user interaction with it.
    */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        guestSeekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
            override fun onStartTrackingTouch(seekBar: SeekBar) {}

            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                Log.d("Admin", "MainActivity: Progress changed to $progress")
                guestAmountText.text = progress.toString()
                logicUnit.setPeople(progress.toString())
                splitText.text = logicUnit.getSplit()
            }
        })
    }

    /*
    Input: View
    Output: Void
    Purpose: Inputs a choice from the keypad to the parsing function and updates the rest of the UI
        to display the changes
    */
    fun clickedNumberButton(view : View){
        Log.d("Admin", "MainActivity: Keypad button ${view.tag} was clicked")
        //Adds the character to the parsing class and updates the UI
        inputText.text = logicUnit.setChar(view.tag.toString())
        tipText.text = logicUnit.getTip()
        totalText.text = logicUnit.getTotal()
        splitText.text = logicUnit.getSplit()
    }

    /*
    Input: View
    Output: Void
    Purpose: When user clicks on a percentage button, it updates the display to highlight that button
        and performs necessary calculation and displays them in the UI
    */
    fun percentButton(view : View){
        Log.d("Admin", "MainActivity: ${view.tag} was clicked")
        var newButton : View = tenPercent

        //Sets all buttons to be drawn inactive
        zeroPercent.setBackgroundDrawable(resources.getDrawable(R.drawable.action_button_inactive_selector))
        fivePercent.setBackgroundDrawable(resources.getDrawable(R.drawable.action_button_inactive_selector))
        tenPercent.setBackgroundDrawable(resources.getDrawable(R.drawable.action_button_inactive_selector))
        fifteenPercent.setBackgroundDrawable(resources.getDrawable(R.drawable.action_button_inactive_selector))
        twentyPercent.setBackgroundDrawable(resources.getDrawable(R.drawable.action_button_inactive_selector))
        twentyfivePercent.setBackgroundDrawable(resources.getDrawable(R.drawable.action_button_inactive_selector))

        //Checks which button was clicked
        when (view.tag)
        {
            "0" -> { newButton = zeroPercent }
            "5" -> { newButton = fivePercent }
            "10" -> { newButton = tenPercent }
            "15" -> { newButton = fifteenPercent }
            "20" -> { newButton = twentyPercent }
            "25" -> { newButton = twentyfivePercent }
        }

        //Draws the clicked button as active
        newButton.setBackgroundDrawable(resources.getDrawable(R.drawable.action_button_active_selector))

        //Updates calculation and UI
        logicUnit.setPercent(view.tag.toString())
        tipText.text = logicUnit.getTip()
        totalText.text = logicUnit.getTotal()
        splitText.text = logicUnit.getSplit()
    }

    /*
    Input: String
    Output: Void
    Purpose: When a user clicks on a rounding button, it will either highlight a button if its currently
            not selected or turn it off it is currently selected. Afterwards updates the calculation and UI
    */
    fun roundButton(view : View){
        Log.d("Admin", "MainActivity: ${view.tag} was clicked")
        var newButton : View = up

        //Sets both buttons off
        up.setBackgroundDrawable(resources.getDrawable(R.drawable.action_button_inactive_selector))
        down.setBackgroundDrawable(resources.getDrawable(R.drawable.action_button_inactive_selector))

        //If the button clicked is not the same as the one currently clicked turn it on
        if(upDownState != view.tag) {
            when (view.tag) {
                "up" -> {
                    newButton = up
                    upDownState = "up"
                }
                "down" -> {
                    newButton = down
                    upDownState = "down"
                }
            }
            newButton.setBackgroundDrawable(resources.getDrawable(R.drawable.action_button_active_selector))
        }
        //Else turn it off
        else{
            upDownState = ""
        }
        //Update the UI
        logicUnit.setRounding(upDownState)
        totalText.text = logicUnit.getTotal()
        splitText.text = logicUnit.getSplit()
    }
}