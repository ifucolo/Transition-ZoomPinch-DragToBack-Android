# Transition ZoomPinch DragToBack - Android
Library to implement image transition between activities(soon Fragments) with drag to back.

[Article in Portuguese and old but gold](https://medium.com/android-dev-br/transi%C3%A7%C3%A3o-zoom-pinch-e-drag-to-back-de-imageview-f359556a1671)
[Article branch](https://github.com/ifucolo/Transition-ZoomPinch-DragToBack-Android/tree/old-version)

## Demo
![alt tag](https://github.com/ifucolo/ZoomPich-Android/blob/master/gif.gif)

## Full Example
[Sample](https://github.com/ifucolo/Transition-ZoomPinch-DragToBack-Android/tree/new-release/app) the example use [Glide](https://github.com/bumptech/glide) to load image but feel free to use another image load libraries.

## Download

### Gradle:

```groovy
dependencies {
	 implementation 'com.github.ifucolo:Transition-ZoomPinch-DragToBack-Android:1.0.0-beta'
}
```
```
allprojects {
		repositories {
			...
			maven { url "https://jitpack.io" }
		}
	}
```

## Usage

### Xml
Add The **FrameTouch widget** on your destination **Activity** with the imageView inside:

```xml
<com.ifucolo.imagepinchback.widget.FrameTouch
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/frameTouch"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@android:color/black"
    android:orientation="vertical"
    android:layout_gravity="center">

    <ImageView
        android:id="@+id/IMG_ID"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_gravity="center"/>
</com.ifucolo.imagepinchback.widget.FrameTouch>
```

### Acitivity Start

```kotlin
  //Don`t forgot to load your image before this

  ImageDragBackStart(
     activity = this,
     imageView = IMAGE_VIEW,
     imgUrl = IMAGE_URL,
     activityIntent = DESTION_ACTIVITY_INTETN//DragBackEndActivity.newIntent(this)
  )
```

### Acitivity Destination
```kotlin
   val imageDragBackEnd = ImageDragBackEnd(
      activity = this,
      lifecycleOwner = this,
      imageView = IMAGE_VIEW,
      frameTouch = FRAMETOUCH_WIDGET,
      activityIntent = ACTIVITY_INTENT //intent
    )
    
    //You can use the object above to get the link of the image to load: imageDragBackEnd.imgUrl
    //and don't forgot to load the image
```

### Extra
The library already handle the image click listener for you, but if you want to do something more you can do by yourself, just follow the changes to handle the click:

```kotlin
  val imageDragBackStart = ImageDragBackStart(
    ...// ALL STUFF ABOVE 
    needImageClickListener = false
  )
  
  //inside your click listener or whenever you want make the transition, call the follow function
  ...
  imageDragBackStart.startActivityTransition()
  
```


## License
```
Transition ZoomPinch DragToBack library for Android
Copyright (c) 2018 Iago Fucolo (http://github.com/ifucolo).

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

