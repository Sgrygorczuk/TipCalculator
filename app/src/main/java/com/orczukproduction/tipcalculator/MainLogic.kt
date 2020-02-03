package com.orczukproduction.tipcalculator

import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.ceil
import kotlin.math.floor

class MainLogic {

    //Variables that are saved to perform calculations
    private var percentage : Double = 0.10       //Saves the percentage, can be saved as 0.05, 0.10, 0.15, 0.20 or 0.25
    private var people : Double = 1.0            //Saves the amount of people that the bill is split between ranges from 1-15
    private var input : String = ""              //Saves the value of the bill
    private var round : String = ""              //Saves the state of rounding, round up ("up"), round down ("down"), or don't round ("")
    private var tip : String = ""                //Saves what the tip is input*percent
    private var total : String = ""              //Saves what the total is based on percentage and rounding state

    /*
    Input: Void
    Output: Boolean
    Purpose: To minimize the number of characters to 9 or less
    */
    private fun isMaxLength() : Boolean { return input.length >= 9 }

    /*
    Input: String
    Output: Void
    Purpose: Saves the percentage chosen by user, can be 0.05 0.10, 0.15, 0.20 or 0.25
    */
    fun setPercent(input : String) {percentage = input.toDouble()/100}

    /*
    Input: String
    Output: Void
    Purpose: Saves the rounding method chosen by user, can be "up", "down", or ""
    */
    fun setRounding(input : String){round = input}

    /*
    Input: String
    Output: Void
    Purpose: Saves the amount of people that the bill is split between, ranges from 1-15
    */
    fun setPeople(input : String){people = input.toDouble()}

    /*
    Input: String
    Output: Void
    Purpose: Updates the state of the input string, it will do one of three things:
        1) Set input = "" if user chose clear
        2) Add a characters "0","1","2","3","4","5","6","7","8","9" and "." to current input string,
            with special checks for "."
        3) Remove a char from the end of the string if the string is not empty
    */
    fun setChar(choice: String) : String {
        //Clear choice
        if(choice == "c") {
            input = ""
            return "$"}
        //Delete choice
        else if(input != "" && choice == "d"){input = input.substring(0, input.length - 1)}
        //Add a character choice
        else if(!isMaxLength()){
            when (choice) {
                "0" -> input += "0"
                "1" -> input += "1"
                "2" -> input += "2"
                "3" -> input += "3"
                "4" -> input += "4"
                "5" -> input += "5"
                "6" -> input += "6"
                "7" -> input += "7"
                "8" -> input += "8"
                "9" -> input += "9"
                "." -> if(!input.contains('.')){ input += if(input == ""){ "0." } else { "." }
                }
            }
            //If the user input 0 followed by any number other than '.' it deletes the 0
            if(input.length >= 2 && input[0] == '0'  && input[1] != '.') { input = input.replaceFirst("0", "")}
        }
        return "$${addCommas(input)}"
    }

    /*
    Input: Void
    Output: String
    Purpose: If there is an input calculates the tip = input*percentage, otherwise returns "$"
    */
    fun getTip() : String {
        return if(input == "") {
            tip = ""
            "$"
        } else {
            tip = (input.toDouble() * percentage).toString()
            tip = BigDecimal(tip.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()
            "$${addCommas(tip)}"
        }
    }

    /*
    Input: Void
    Output: String
    Purpose: If there is an input calculates the total = input + tip and rounds it based on user choices,
             otherwise returns "$"
    */
    fun getTotal() : String {
        return if(input == "") { "$"
        } else {
            total = (tip.toDouble() + input.toDouble()).toString()
            total = when (round) {
                "up" -> { (ceil(total.toDouble())).toString() }
                "down" -> { (floor(total.toDouble())).toString() }
                else -> total
            }
            total = BigDecimal(total.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()
            "$${addCommas(total)}"
        }
    }

    /*
    Input: Void
    Output: String
    Purpose: If there is an input calculates the split = total/people, otherwise returns "$"
    */
    fun getSplit() : String {
        return if(input == "") { "$"
        } else {
            val spilt : String = (total.toDouble()/people).toString()
            return "$${addCommas(BigDecimal(spilt.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString())}"
        }
    }

    /*
    Input: String
    Output: String
    Purpose: Takes any numeric string and breaks it into chunks of three separated by commas
            Ex. 1002311 -> 1,002,311
    */
    private fun addCommas(input : String) : String {
        var output = ""
        //Checks if the original string contains any decimal values if so remove them for time being
        val adjustedInput : String = if(input.contains('.')) { input.substring(0,input.indexOf('.')) } else{ input }
        //If the current string is larger than 3 break it down into the chunks
        var i : Int = adjustedInput.length
        if(adjustedInput.length > 3) {
            while(i > 3){
                //Add up all of the chunks of 3 with commas placed between them
                output = output + "," + adjustedInput.substring(i-3,i)
                i -= 3
            }
        }
        //Add whatever chunk is remaining
        output = adjustedInput.substring(0, i) + output
        //If the original string had a decimal values add them back in
        if(input.contains('.')) { output += input.substring(input.indexOf('.'))}
        return output
    }

}