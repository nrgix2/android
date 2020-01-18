package com.example.myapplication.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.model.ForecastResult
import com.example.myapplication.model.Forecast
import com.example.myapplication.model.ForecastList
import com.example.myapplication.model.Main
import com.example.myapplication.network.ApiError
import com.example.myapplication.network.ApiHelpers
import com.example.myapplication.network.ApiRequestCallback
import com.example.myapplication.recyclerview.recyclerview.ForecastAdapter
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.TextView
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.Parcel
import android.os.Parcelable



class MainActivity() : AppCompatActivity(), Parcelable {

    /**
     * All the model are created with a website and with a json from the API (be carefull
     * of some names that they are in Android or some types bad declare)
     */
    lateinit var weatherAdapter : ForecastAdapter
    lateinit var weatherList : MutableList<ForecastList>
    lateinit var apiHelpersInstance: ApiHelpers

    constructor(parcel: Parcel) : this() {

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // init weather list
        weatherList = mutableListOf()

        // init adapter
        weatherAdapter = ForecastAdapter(weatherList)


        apiHelpersInstance = ApiHelpers(this)

        val recyclerView = recyclerview_forecast // on récupère ici le RV
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = weatherAdapter

        apiHelpersInstance.ApiRequestForecastById(
            6454573,
            object : ApiRequestCallback<ForecastResult>() {
                override fun onSuccess(result: ForecastResult) {
                    super.onSuccess(result)
                    Log.d(
                        MainActivity::class.java.canonicalName,
                        "Api request success ! $result"
                    )
                    runOnUiThread {

                        weatherList.addAll(result.forecastList)
                        // log if the list is filled
                        Log.d(weatherList::class.java.canonicalName, "Weather list modified")
                        weatherAdapter.notifyDataSetChanged()
                        //notify when the data change on the adapter
                        Log.d(weatherAdapter::class.java.canonicalName, "Weather notified")


                    }
                }
                // display an error when the request is unsuccessful
                override fun onError(error: ApiError?) {
                    super.onError(error)
                }
            }
        )
    }

    inner class ExitAndroidAppProgrammatically : AppCompatActivity() {

        internal var textView: TextView? = null

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)

        }

        fun closeApplication(view: View) {
            finish()
            moveTaskToBack(true)
        }

    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MainActivity> {
        override fun createFromParcel(parcel: Parcel): MainActivity {
            return MainActivity(parcel)
        }

        override fun newArray(size: Int): Array<MainActivity?> {
            return arrayOfNulls(size)
        }
    }
}
