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

import android.os.Bundle
import android.util.Log
import com.badlogic.gdx.backends.android.AndroidApplication
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration
import com.github.kostasdrakonakis.annotation.Intent
import com.snapyr.sdk.Snapyr


@Intent
class MainActivity : AndroidApplication() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize(FlappyBird(context), AndroidApplicationConfiguration())
        // Create an snapyr client with the given Android app context and Snapyr write key.
        // Create an snapyr client with the given Android app context and Snapyr write key.
        var singleton:SnapyrData= SnapyrData.instance;
        context.packageName
        Log.d("package", context.packageName );
        Log.d("onDoIdentify",singleton.identifyKey)
        val snapyr = Snapyr.Builder(context, singleton.identifyKey)
            .enableDevEnvironment()
            .enableSnapyrPushHandling()
            .trackApplicationLifecycleEvents() // Enable this to record certain application events automatically
            .recordScreenViews() // Enable this to record screen views automatically
            .flushQueueSize(1)
            .build()

// Set the initialized instance as a globally accessible instance.

// Set the initialized instance as a globally accessible instance.
        Snapyr.setSingletonInstance(snapyr)
    }

}
