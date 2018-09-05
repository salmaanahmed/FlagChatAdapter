# FlagChatAdapter

# Description
FlagChatAdapter is easy to implement enchanting recycler view adapter.
Just extend your adapter with FlagChatAdapter, impliment some methods and voila!
You have got the most beautiful looking chat on your phone.

<br>
<img height="400" src="https://github.com/salmaanahmed/FlagChatAdapter/blob/master/chat_screen.png?raw=true" />
<br>

# Demonstration
Multiple ways to input values into the slider.

<br>
<img height="400" src="https://github.com/salmaanahmed/FlagChatAdapter/blob/master/chat_animation.gif?raw=true" />
<br>

# Installation
```FlagChatAdapter``` can be installed using Maven, Gradle, or manually.

# Maven
**Step 1.** Add the JitPack repository to your build file
``` xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```
**Step 2.** Add the dependency
``` xml
<dependency>
    <groupId>com.github.salmaanahmed</groupId>
    <artifactId>FlagChatAdapter</artifactId>
    <version>1.0</version>
</dependency>
```

# Gradle
**Step 1.** Add the JitPack repository to your build file
``` gradle
allprojects {
    repositories {
      ...
      maven { url 'https://jitpack.io' }
    }
}
```
**Step 2.** Add the dependency
``` gradle
dependencies {
  compile 'com.github.salmaanahmed:SASlider:0.1'
}
```
# Manual Installation
If you prefer not to use either of the above mentioned dependency managers, you can integrate FlagChatAdapter into your project manually by adding the files contained in the java folder to your project.

# Getting Started
# Using XML
You can use FlagChatAdapter in XML as following
``` xml
<sasliderdemo.salmaan.ahmsal.com.saslider.SASlider
    android:layout_width="match_parent"
    android:layout_height="100dp"
    android:layout_marginBottom="50dp"
    app:layout_constraintTop_toTopOf="parent"
    app:circleColor="@android:color/darker_gray"
    app:defaultValue="150"
    app:editTextBorderColor="@color/colorGrey"
    app:isDecimal="false"
    app:maxValue="300"
    app:minValue="0"
    app:criticalColor="@color/darkRed"
    app:upperThreshold="200"
    app:lowerThreshold="100"
    app:sliderColor="@color/darkGreen" />
```
High customizeability allows developers to have desired color of circles, slider thumb, edit text border and color of thumb when slider value is critical i.e. beyond the defined thresholds.
Developers can set the slider type to decimal or integer. Set min max range and also the critical thresholds of both upper and lower bounds.

# Using Kotlin
You can create the slider programatically as well, see the example below.
``` kotlin
  val slider = SASlider(this@MainActivity)
  slider.sliderColor = Color.BLUE
  slider.min = 0.0
  slider.max = 50.0
  slider.criticalColor = Color.BLACK
  slider.isDecimal = true
  linearLayout.addView(slider)
```
# Getting value
Developers can query the current value anytime in the slider using the following variable.
``` kotlin
slider.selectedIndex
```

# Contributions and Licence
```FlagChatAdapter``` is available under the MIT license. See the [LICENSE](https://github.com/salmaanahmed/SAExpandableButton/blob/master/LICENCE.txt) file for more info.

Pull requests are welcome! The best contributions will consist of substitutions or configurations for classes/methods known to block the main thread during a typical app lifecycle.

I would love to know if you are using SASlider in your app, send an email to [Salmaan Ahmed](mailto:salmaan.ahmed@hotmail.com)
