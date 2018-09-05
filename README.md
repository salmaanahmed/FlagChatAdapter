# FlagChatAdapter

# Description
FlagChatAdapter is easy to implement enchanting recycler view adapter.
Just extend your adapter with FlagChatAdapter, impliment some methods and voila!
You have got the most beautiful looking chat on your phone.
Zero boilerplate code, just put your variables in the right direction.

<br>
<img height="400" src="https://github.com/salmaanahmed/FlagChatAdapter/blob/master/chat_screen.png?raw=true" />
<br>

# Demonstration
See the flag animation.

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
    implementation 'com.github.salmaanahmed:FlagChatAdapter:1.0'
}
```
# Manual Installation
If you prefer not to use either of the above mentioned dependency managers, you can integrate FlagChatAdapter into your project manually by adding the files contained in the java folder to your project.

# Getting Started
# Extend Your Adapter With FlagChatAdapter
Extend your adapter with FlagChatAdapter and pass context to the adapter
```kotlin
class ChatAdapter(context: Context, private var list: ArrayList<Any>) : FlagChatAdapter(context)
```

# Implement methods
Implement required methods and variables. Write one line functions, isnt it so simple ;)
You may see the sample for detail description
``` kotlin
    //return chat message on the position passed as parameter
    abstract fun chatMessage(position: Int): String

    //return time of message as string format on the position passed as parameter
    abstract fun messageTime(position: Int): String

    //return message sender on the position passed as parameter, if its you, return true
    abstract fun isMe(position: Int): Boolean

    //you must have a variable of animation in the object i.e. if you want to animate or not
    abstract fun animation(position: Int): Boolean

    //the animation variable must be set to false when animation is performed once, otherwise flags will animate on every scroll
    abstract fun setAnimationStatus(position: Int, animationStatus: Boolean)
    
    //Name of the sender
    abstract val otherName: String

    //You shall simply return list.size
    abstract val listSize: Int
```

You have also got longClickEvents
```kotlin
    //You can implement whatever you want onLongClick event
    abstract fun onMessageLongClicked(position: Int)
```
Beautify your chat with provided customization. You can also change color of flags.
Very simple to implement and looks awesome. You can find more details in sample app.

# Contributions and Licence
```FlagChatAdapter``` is available under the MIT license. See the [LICENSE](https://github.com/salmaanahmed/SAExpandableButton/blob/master/LICENCE.txt) file for more info.

Pull requests are welcome! The best contributions will consist of substitutions or configurations for classes/methods known to block the main thread during a typical app lifecycle.

I would love to know if you are using SASlider in your app, send an email to [Salmaan Ahmed](mailto:salmaan.ahmed@hotmail.com)
