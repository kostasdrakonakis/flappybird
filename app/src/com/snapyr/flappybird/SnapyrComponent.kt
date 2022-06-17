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
import android.util.Log
import com.snapyr.sdk.Snapyr
import com.snapyr.sdk.Traits


class SnapyrComponent (private val context: Context) {

    var singleton:SnapyrData= SnapyrData.instance;

    internal fun onDoIdentify() {
        Snapyr.with(context).identify(singleton.identifyUserId)
        Snapyr.with(context).identify(Traits().putName(singleton.identifyName))
        Snapyr.with(context).identify(Traits().putEmail(singleton.identifyEmail))
    }


    internal fun onDoTrack() {
        Log.d("onDoTrack", "Track tapped")
        Snapyr.with(context).track("ClickPlay")
    }


    internal fun yourScore(score: Int) {
        Log.d("onDoTrack", "Track tapped")
        if(score % 2 == 0)
            Snapyr.with(context).track("reachedascoreof_$score")
    }


    internal fun onScore2SendEmail() {
        Log.d("onDoTrack", "reachedascoreof_2_email")
        Snapyr.with(context).track("reachedascoreof_2_email")
    }

    internal fun onDoFlush() {
        Log.d("onDoFlush", "Flush tapped")
        Snapyr.with(context).flush()
    }

}
