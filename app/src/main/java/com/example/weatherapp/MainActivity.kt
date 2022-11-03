package com.example.weatherapp

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    var CITY: String = "Las Vegas, NV, USA"
    var API: String = "e65772382e379b9648b55b8b7c22766f" // Use API key

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // finding the button
        val button = findViewById<Button>(R.id.button)
        // finding the editText
        val editText = findViewById<EditText>(R.id.editText)
        // setting onClickListener
        button.setOnClickListener {
            // gets current view
            val view: View? = this.currentFocus

            //checks if view is not null
            if (view != null) {
                //creates variable for input manager and intializes it
                val inputMethodManager =
                    getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                // hiding keyboard ..
                inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            }

            // getting the user input
            val text = editText.text
            // assign to CITY variable
            CITY = text.toString()
            weatherTask().execute()
            // clears the editText
            editText.text = null

        }
        weatherTask().execute()

    }

    inner class weatherTask() : AsyncTask<String, Void, String>() {
        override fun onPreExecute() {
            super.onPreExecute()
            /* Showing the ProgressBar, Making the main design GONE */
            findViewById<ProgressBar>(R.id.loader).visibility = View.VISIBLE
            findViewById<RelativeLayout>(R.id.mainContainer).visibility = View.GONE
            findViewById<TextView>(R.id.errorText).visibility = View.GONE
        }

        override fun doInBackground(vararg params: String?): String? {
            var response:String?
            try{
                response = URL("https://api.openweathermap.org/data/2.5/weather?q=$CITY&units=imperial&appid=$API").readText(
                    Charsets.UTF_8
                )
            }catch (e: Exception){
                Log.d("TAG", "City not found!")
                response = URL("https://api.openweathermap.org/data/2.5/weather?q=Las+Vegas,+NV,+USA&units=imperial&appid=$API").readText(
                    Charsets.UTF_8
                )
                CITY = "Las Vegas, NV, USA"
            }
            return response
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            try {
                /* Extracting JSON returns from the API */
                val jsonObj = JSONObject(result)
                val main = jsonObj.getJSONObject("main")
                val sys = jsonObj.getJSONObject("sys")
                val wind = jsonObj.getJSONObject("wind")
                val weather = jsonObj.getJSONArray("weather").getJSONObject(0)
                val updatedAt:Long = jsonObj.getLong("dt")
                val updatedAtText = "Updated at: "+ SimpleDateFormat("MM/dd/yyyy hh:mm a", Locale.ENGLISH).format(Date(updatedAt*1000))
                val temp = main.getString("temp").split(".")
                val tempMin = main.getString("temp_min").split(".")
                val tempMax = main.getString("temp_max").split(".")
                val pressure = main.getString("pressure")
                val humidity = main.getString("humidity")
                val sunrise:Long = sys.getLong("sunrise")
                val sunset:Long = sys.getLong("sunset")
                val windSpeed = wind.getString("speed")
                val weatherDescription = weather.getString("description")

                val address = jsonObj.getString("name")+", "+sys.getString("country")

                /* Populating extracted data into our views */
                findViewById<TextView>(R.id.address).text = address
                findViewById<TextView>(R.id.updated_at).text =  updatedAtText
                findViewById<TextView>(R.id.status).text = weatherDescription.capitalize()
                findViewById<TextView>(R.id.temp).text = temp[0] +"°F"
                findViewById<TextView>(R.id.temp_min).text = "Min Temp: " + tempMin[0]+"°F"
                findViewById<TextView>(R.id.temp_max).text = "Max Temp: " + tempMax[0]+"°F"
                findViewById<TextView>(R.id.sunrise).text = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(sunrise*1000))
                findViewById<TextView>(R.id.sunset).text = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(sunset*1000))
                findViewById<TextView>(R.id.wind).text = windSpeed
                findViewById<TextView>(R.id.pressure).text = pressure
                findViewById<TextView>(R.id.humidity).text = humidity

                /* Views populated, Hiding the loader, Showing the main design */
                findViewById<ProgressBar>(R.id.loader).visibility = View.GONE
                findViewById<RelativeLayout>(R.id.mainContainer).visibility = View.VISIBLE

            } catch (e: Exception) {
                findViewById<ProgressBar>(R.id.loader).visibility = View.GONE
                findViewById<TextView>(R.id.errorText).visibility = View.VISIBLE
            }

        }
    }
}
