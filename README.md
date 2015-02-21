What is Godot?
==============

Godot is a Gradle plugin to help you keep track of how much time you spend waiting for builds to finish.

How to use this
---------------

Add the following to your project's root buildscript:

```Groovy
buildScript {
  repositories {
    jcenter()
    maven {
      url "https://oss.sonatype.org/content/repositories/snapshots"
    }
  }
  dependencies {
    // ... other buildscript dependencies, e.g. the Android Gradle plugin
    classpath 'de.hannesstruss:godot:0.1-SNAPSHOT'
  }
}

apply plugin: 'de.hannesstruss.godot'
```

When the plugin is applied, it'll keep information about how long each of your builds took. Using

    ./gradlew generateGodotReport

will generate a report with some nice chart.

You should probably also add `godot.log` to your VCS ignores, e.g. `.gitignore`.

Release to Maven
----------------

    ./gradlew clean build uploadArchives


License
-------

    Copyright 2015 Hannes Struß

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
