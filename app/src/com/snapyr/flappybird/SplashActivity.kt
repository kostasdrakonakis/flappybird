/*
 * Copyright 2018 Konstantinos Drakonakis.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.snapyr.flappybird

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.github.kostasdrakonakis.androidnavigator.IntentNavigator
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        var identifyUserId = findViewById(R.id.identify_userid) as EditText;
//        identifyUserId.setText("alina3")
        var identifyKey = findViewById(R.id.identify_key) as EditText;
        identifyKey.setText("HheJr6JJGowjvMvJGq9FqunE0h8EKAIG")
        var identifyEmail = findViewById(R.id.identify_email) as EditText;
//        identifyEmail.setText("alina@snapyr.com")
        var identifyName = findViewById(R.id.identify_name) as EditText;
//        identifyName.setText("alina3")
        var identifyPhone = findViewById(R.id.identify_phone) as EditText;

        var envButton = findViewById(R.id.env) as Button
        var singleton:SnapyrData= SnapyrData.instance;

        playButton.setOnClickListener {
            singleton.identifyUserId=identifyUserId.text.toString()
            singleton.identifyEmail=identifyEmail.text.toString()
            singleton.identifyKey=identifyKey.text.toString()
            singleton.identifyName=identifyName.text.toString()
            singleton.identifyPhone=identifyPhone.text.toString()

            IntentNavigator.startMainActivity(this)
        }

        env.setOnClickListener{
            val builder = AlertDialog.Builder(this)
                .setTitle("Choose Env!")
            builder.setPositiveButton("dev") { dialog, which ->
                singleton.env = "dev"
                identifyKey.setText("38bT1SbGJ0A12CJqk8DFRzypJnIylRmg")
                envButton.setText("Env: dev")

            }
            builder.setNeutralButton("prod") { dialog, which ->
                singleton.env = "prod"
                identifyKey.setText("HheJr6JJGowjvMvJGq9FqunE0h8EKAIG")
                envButton.setText("Env: prod")
            }
            builder.setNegativeButton("stg") { dialog, which ->
                singleton.env = "stg"
                identifyKey.setText("kuxCvTgQdcXAgNjrhrMP2U46VIhUi6Wz")
                envButton.setText("Env: stg")
            }
            builder.show()
        }
    }
}