## PyxInjector [![](https://jitpack.io/v/WindSekirun/PyxInjector.svg)](https://jitpack.io/#WindSekirun/PyxInjector)  [![codebeat badge](https://codebeat.co/badges/a55324d4-2958-413d-81fb-1672e9ced592)](https://codebeat.co/projects/github-com-windsekirun-pyxinjector-master)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-PyxInjector-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/6362)  [![Kotlin](https://img.shields.io/badge/kotlin-1.1.4-blue.svg)](http://kotlinlang.org) [![GitHub license](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0) 


**Annotation Field Injector Library**

Pyx is abbreviation of Pyxis, small and faint constellation in the southern sky.

PyxInjector help you to inject field, methods by various annotation field

릴리즈 소개 글은 [개인 블로그 PyxisPub 에서 보실 수 있습니다.](https://blog.uzuki.live/pyxinjector-dependency-injections/)

### Usages

*rootProject/build.gradle*
```	
allprojects {
    repositories {
	    maven { url 'https://jitpack.io' }
    }
}
```

*app/build.gradle*
```
dependencies {
    implementation 'com.github.WindSekirun:PyxInjector:1.2.1'
}
```

#### Start to use
1. extending ```InjectActivity``` or ```InjectSupportFragment``` in your activity / fragment
2. Done, you can use any annotation fields below.

If you can't extending pre-bulit classes like ```InjectActivity```, you can call ```PyxInjector.getInstance().inject``` maunally. please see [Custom object](https://github.com/WindSekirun/PyxInjector/wiki/[Execute]-Custom-Object) in wiki

### Annotation Fields

#### *@BindView*
Annotation Field with @BindView with Optional View ID for PyxInjector to find and cast the corresponding view.

[Wiki](https://github.com/WindSekirun/PyxInjector/wiki/@BindView)

#### *@Extra*
Annotation Field with @Extra with Optional extra key to find and cast the corresponding intent extras.

[Wiki](https://github.com/WindSekirun/PyxInjector/wiki/@Extra)

#### *@Argument*
Annotation Field with @Argument with Optional extra key to find and cast the corrsponding fragment arguments.

[Wiki](https://github.com/WindSekirun/PyxInjector/wiki/@Argument)

#### *@OnClicks* / *@OnClicks*
Annotation Field with @OnClick, @OnClicks with View ID to find and invoke methods

[Wiki](https://github.com/WindSekirun/PyxInjector/wiki/@OnClick---@OnClicks)

#### *@OnLongClick* / *@OnLongClicks*
Annotation Field with @OnLongClick, @OnLongClicks with View ID to find and invoke methods

[Wiki](https://github.com/WindSekirun/PyxInjector/wiki/@OnLongClick---@OnLongClicks)

#### *@OnSeekbarChange* [Since 1.1]
Annotation Field with @OnSeekbarChange with View ID to find and invoke OnSeekBarChangeListener.onProgressChanged(seekbar: SeekBar?, progress: Int, fromUser: Boolean)

[Wiki](https://github.com/WindSekirun/PyxInjector/wiki/@OnSeekbarChange)

#### *@OnEditTextChange* [Since 1.1]
Annotation Field with @OnEditTextChange with View ID to find and invoke methods of TextWatcher

[Wiki](https://github.com/WindSekirun/PyxInjector/wiki/@OnEditTextChange)

#### *@OnCheckChange* [Since 1.1]
Annotation Field with @OnCheckChange with View ID to find and invoke CompoundButton.OnCheckedChangeListener.onCheckedChanged (CompoundButton buttonView, boolean isChecked)

[Wiki](https://github.com/WindSekirun/PyxInjector/wiki/@OnCheckChange)

### Non-Activity / Fragment Binding [Since 1.1]
```Java
public class ListHolder extends RecyclerView.ViewHolder {
    private @BindView TextView txtNum;

    public ListHolder(View itemView) {
        super(itemView);
        PyxInjector.getInstance().execute(getActivity(), this, itemView);
    }
}
```

### find(@resId, View) [Since 1.1.5]
```Java
txtName2 = PyxInjector.find(R.id.txtName2, PyxUtils.content(this));
````

### Config (Optional)
as 1.0.0 We support Config of PyxInjector.

Application class
```Java
Config config = new Config(BindViewPrefix.PREFIX_M);
PyxInjector.initializeApplication(config);
```

#### BindViewPrefix
[Wiki](https://github.com/WindSekirun/PyxInjector/wiki/[Config]-BindViewPrefix) 

### Custom Object
as 1.0.0, We support *InjectActivity*, *InjectFragment*, *InjectSupportFragment* to inject all activity / fragement.

if you need to inherit other class, [insert this code in proper methods](https://github.com/WindSekirun/PyxInjector/wiki/[Execute]-Custom-Object)

### License 
```
Copyright 2017 WindSekirun (DongGil, Seo)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
