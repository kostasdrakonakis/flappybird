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

import android.R
import android.app.AlertDialog
import android.net.Uri
import android.os.Bundle
import android.util.Log
import com.badlogic.gdx.backends.android.AndroidApplication
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration
import com.github.kostasdrakonakis.annotation.Intent


@Intent
class MainActivity : AndroidApplication() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize(FlappyBird(context), AndroidApplicationConfiguration())

        if(intent != null) {
            val currentIntent: Uri? = intent.data;
            handleOpenIntent(currentIntent)
        }
    }

    private fun handleOpenIntent(data: Uri?) {
        if (data == null) {
            return
        }
        val isCorrect = data.getQueryParameter("correct")
        Log.d("isCorrect", isCorrect.toString())
        if (isCorrect != "") {
            if(isCorrect == "true")
                correct()
            else if (isCorrect == "false"){
                wrong()
            } else {
                issue()
            }
        }
    }

    private fun correct() {
        AlertDialog.Builder(context)
            .setTitle("Congratulations!")
            // The dialog is automatically dismissed when a dialog button is clicked.
            .setPositiveButton(
                R.string.yes, null)
            .setNegativeButton(R.string.no, null)
            .setView(com.snapyr.flappybird.R.layout.alert_view)
            .show()
    }

    private fun wrong() {
        AlertDialog.Builder(context)
            .setTitle("Sorry!")
            .setPositiveButton(
                R.string.yes, null)
            .setNegativeButton(R.string.no, null)
            .setView(com.snapyr.flappybird.R.layout.wrong_alert_view)
            .show()
    }


    private fun issue() {
        AlertDialog.Builder(context)
            .setTitle("Wrong deeplink!")
            .setPositiveButton(
                R.string.yes, null)
            .setNegativeButton(R.string.no, null)
            .setView(com.snapyr.flappybird.R.layout.wrong_alert_view)
            .show()
    }
}
