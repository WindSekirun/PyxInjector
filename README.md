## PyxInjector
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-PyxInjector-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/6362) [![](https://jitpack.io/v/WindSekirun/PyxInjector.svg)](https://jitpack.io/#WindSekirun/PyxInjector) [![Kotlin](https://img.shields.io/badge/kotlin-1.1.4-blue.svg)](http://kotlinlang.org) [![GitHub license](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0) 


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
    implementation 'com.github.WindSekirun:PyxInjector:1.1.1'
}
```

### Annotation Fields

#### *@BindView*
Precondition: **Activity / Fragment will inherit *InjectActivity*, *InjectFragment*, *InjectSupportFragment* or your custom object**

Annotation Field with @BindView with Optional View ID for PyxInjector to find and cast the corresponding view.

```Java
private @BindView TextView mTxtName; // 1)
private @BindView TextView txtName2; // 2)
private @BindView(R.id.txtName3) TextView txtName3; // 3)
```

It support three mode of Injection

1. resource id != field name with Config - PREFIX_M : You don't need to write View ID for this view
2. resource id == field name : You don't need to write View ID for this view
3. Explicit statement : You can write View ID for this view

#### *@Extra*
Precondition: **Activity will inherit *InjectActivity* or your custom object**

Annotation Field with @Extra with Optional extra key to find and cast the corresponding intent extras.

```Java
private @Extra String age = ""; // 1)
private @Extra("name") String name = ""; // 2)
```

It support two mode of Injecton

1. extra name == field name : You don't need to write extra key for this intent extras
2. Explicit statement: You can write extra key for this intent extras

#### *@Argument*
Precondition: **Fragment will inherit *InjectFragment*, *InjectSupportFragment* or your custom object**

Annotation Field with @Argument with Optional extra key to find and cast the corrsponding fragment arguments.

```Java
private @Argument String age = ""; // 1)
private @Argument("name") String name = ""; // 2)
```

It support two mode of Injecton

1. extra name == field name : You don't need to write extra key for this fragment arguments.
2. Explicit statement: You can write extra key for this fragment arguments.

#### *@OnClicks* / *@OnClicks*
Precondition: **Activity / Fragment will inherit *InjectActivity*, *InjectFragment*, *InjectSupportFragment* or your custom object**

Annotation Field with @OnClick, @OnClicks with View ID to find and invoke methods

```Java
@OnClick(R.id.btnDo)
private void clickDo() { // 1)
    Toast.makeText(getActivity(), "Clicked fragment", Toast.LENGTH_SHORT).show();
}

@OnClicks({R.id.btnDo, R.id.btnDo2})
private void clickDo(View v) { // 2)
    Intent intent = new Intent(this, SecondActivity.class);
    intent.putExtra("name", "John");
    intent.putExtra("age", "17");
    startActivity(intent);
}
```

It support two mode of Injection
1. methods without parameter : You don't need to declare any parameter
2. methods with View parameter

#### *@OnLongClick* / *@OnLongClicks*
Precondition: **Activity / Fragment will inherit *InjectActivity*, *InjectFragment*, *InjectSupportFragment* or your custom object**

Annotation Field with @OnLongClick, @OnLongClicks with View ID to find and invoke methods

```Java
@OnLongClick(R.id.btnDo)
private void clickDo() { // 1)
    Toast.makeText(getActivity(), "Clicked fragment", Toast.LENGTH_SHORT).show();
}

@OnLongClicks(value = {R.id.btnDo, R.id.btnDo2}, defaultReturn = true)
private void clickDo(View v) { // 2)
    Intent intent = new Intent(this, SecondActivity.class);
    intent.putExtra("name", "John");
    intent.putExtra("age", "17");
    startActivity(intent);
}
```

It support three mode of Injection
1. methods without parameter : You don't need to declare any parameter
2. methods with View parameter
3. defaultReturn : true if the callback consumed the long click, false (default or ignore) otherwise.

#### *@OnSeekbarChange* [Since 1.1]
Precondition: **Activity / Fragment will inherit *InjectActivity*, *InjectFragment*, *InjectSupportFragment* or your custom object**

Annotation Field with @OnSeekbarChange with View ID to find and invoke OnSeekBarChangeListener.onProgressChanged(seekbar: SeekBar?, progress: Int, fromUser: Boolean)

```Java
@OnSeekbarChange(R.id.seekBar)
private void changeSeekbar(int progress, boolean fromUser) {
    txtName3.setText(String.format("changeSeekbar::progress = %d, fromUser = %s", progress, String.valueOf(fromUser)));
}
```

#### *@OnEditTextChange* [Since 1.1]
Precondition: **Activity / Fragment will inherit *InjectActivity*, *InjectFragment*, *InjectSupportFragment* or your custom object**

Annotation Field with @OnEditTextChange with View ID to find and invoke methods of TextWatcher


```Java
@OnEditTextChange(value = R.id.editText, trigger = EditTextChangeTrigger.AFTER)
private void changeAfterEditText(EditText editText) {
    mTxtName.setText("changeAfterEditText::");
}

@OnEditTextChange(value = R.id.editText, trigger = EditTextChangeTrigger.BEFORE)
private void changeBeforeEditText(EditText editText, CharSequence s, int start, int count, int after) {
    txtName2.setText(String.format("changeBeforeEditText:: s = %s, start = %d, count = %d, after = %d", s, start, count, after));
}

@OnEditTextChange(R.id.editText)
private void changeTextEditText(EditText editText, CharSequence s, int start, int before, int count) {
    txtName3.setText(String.format("changeTextEditText:: s = %s, start = %d, before = %d, count = %d", s, start, before, count));
}
```

##### EditTextChangeTrigger
* EditTextChangeTrigger.AFTER : invoke when TextWatcher.afterTextChanged(Editable s) is called
* EditTextChangeTrigger.BEFORE : invoke when TextWatcher.beforeTextChanged(CharSequence s, int start, int count, int after) is called
* EditTextChangeTrigger.TEXT (DEFAULT, OMISSIBLE) : invoke when TextWatcher.onTextChanged(CharSequence s, int start, int before, int count) is called

#### *@OnCheckChange* [Since 1.1]
Precondition: **Activity / Fragment will inherit *InjectActivity*, *InjectFragment*, *InjectSupportFragment* or your custom object**

Annotation Field with @OnCheckChange with View ID to find and invoke CompoundButton.OnCheckedChangeListener.onCheckedChanged (CompoundButton buttonView, boolean isChecked)
                                                                                                            
```Java
@OnCheckChange(R.id.checkBox)
private void changeCheckBox(boolean isChecked) {
    txtName3.setText(String.format("changeCheckBox:: isChecked = %s", String.valueOf(isChecked)));
}
```

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

### Config (Optional)
as 1.0.0 We support Config of PyxInjector.

Application class
```Java
Config config = new Config(BindViewPrefix.PREFIX_M);
PyxInjector.initializeApplication(config);
```

#### BindViewPrefix
* None - (DEFAULT) not apply rule
* PREFIX_M - apply implicit rule when field name is mTxtName and view id is txtName

### Custom Object
as 1.0.0, We support *InjectActivity*, *InjectFragment*, *InjectSupportFragment* to inject all activity / fragement.

if you need to inherit other class, insert this code in proper methods

#### Fragment

**Kotlin**
```Kotlin
protected val injector = PyxInjector()

override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        injector.execute(activity, this, getCreatedView());
}
```

**Java**
```Java
protected PyxInjector injector = new PyxInjector();

@Override
public void onViewCreated(@Nullable View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    injector.execute(activity, this, getCreatedView());
}
```

#### Activity or Others

**Kotlin**
```Kotlin
protected val injector = PyxInjector()

override fun onContentChanged() {
    super.onContentChanged()
    injector.execute(this, this, findViewById(android.R.id.content))
}
```

**Java**
```Java
protected PyxInjector injector = new PyxInjector();

@Override
public void onContentChanged() {
    super.onContentChanged();
    injector.execute(this, this, getCreatedView());
}
```

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
