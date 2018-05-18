LoadingLayout
===

[![jcenter](https://img.shields.io/badge/jcenter-1.0.0-green.svg)](https://bintray.com/savion1162336040/maven/LoadingLayout)
[![android support](https://img.shields.io/badge/Android%20Support-14%2B-blue.svg)]()
#### 简介

简单的页面多种状态展示，支持空页面，错误页面，加载中页面
[loading](https://github.com/Savion1162336040/LoadingLayout/img/loading.png)
[empty](https://github.com/Savion1162336040/LoadingLayout/img/empty.png)
[error](https://github.com/Savion1162336040/LoadingLayout/img/error.png)

#### 使用

build.gradle依赖
```groovy
implementation 'com.savion:LoadingLayout:1.0.0'
```
使用方法

布局文件
```xml
    <com.savion.loadinglayout.LoadingLayout
        android:id="@+id/loadinglayout"
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="center"
            android:text="Hello World!" />
    </com.savion.loadinglayout.LoadingLayout>
```
Activity
```text
        //加载中
        load.showLoading();
        //空页面
        load.showEmpty();
        //错误页面
        load.showError();
        //内容页面
        load.showContent();
```

### 注意

LoadingLayout的child只能有一个，不支持多个child。

### License

```text
   Copyright [2018] [savion]
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

