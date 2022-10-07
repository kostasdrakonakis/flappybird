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

import android.content.Context
import android.preference.PreferenceManager
import android.util.Log
import com.snapyr.sdk.Properties
import com.snapyr.sdk.Snapyr
import com.snapyr.sdk.Traits


class SnapyrComponent (private val context: Context) {

    var singleton:SnapyrData= SnapyrData.instance;

    internal fun build() {
        var snapyr = Snapyr.Builder(context, SnapyrData.instance.identifyKey)
        Log.d("singleton.env", singleton.env);
                if(singleton.env == "dev")
                    snapyr.enableDevEnvironment()
        if(singleton.env == "stg")
            snapyr.enableStageEnvironment()
        snapyr.enableSnapyrPushHandling()
            .trackApplicationLifecycleEvents() // Enable this to record certain application events automatically
            .recordScreenViews() // Enable this to record screen views automatically
            .flushQueueSize(1);
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        if (!prefs.getBoolean("firstTime", false)) {

            if(!Snapyr.Valid())
                Snapyr.setSingletonInstance(snapyr.build());
            val editor = prefs.edit()
            editor.putBoolean("firstTime", true)
            editor.putBoolean("register", false)
            editor.commit()
        }
    }

    internal fun onDoIdentify() {
        Snapyr.with(context).identify(singleton.identifyUserId)
        Snapyr.with(context).identify(Traits().putName(singleton.identifyName))
        Snapyr.with(context).identify(Traits().putEmail(singleton.identifyEmail))
        Snapyr.with(context).identify(Traits().putPhone(singleton.identifyPhone))
        Snapyr.with(context)
            .identify(singleton.identifyUserId, Traits().putValue("games_played", 0), null)
    }



    internal fun onDoTrack() {
        Log.d("onDoTrack", "Track tapped")
        val prefs1 = PreferenceManager.getDefaultSharedPreferences(context)
        if (!prefs1.getBoolean("register", false)) {
        Snapyr.with(context).track("register1")
            val editor = prefs1.edit()
            editor.putBoolean("register", true)
            editor.commit()
        }
    }

    internal fun yourScore(scoreNumber: Int) {
        Log.d("onDoTrack", "Track tapped")
        Snapyr.with(context).track("score",  Properties().putValue("total", scoreNumber));
    }

    internal fun onDoFlush() {
        Log.d("onDoFlush", "Flush tapped")
        Snapyr.with(context).flush()
    }

}
