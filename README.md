# DBHiltApp
Building a basic App using MVVM, DataBinding, Hilt and Retrofit

## Hilt
### Add the next dependencies before to start

#### gradle.properties (project)
```java
classpath 'com.google.dagger:hilt-android-gradle-plugin:2.28-alpha'
```
#### gradle.properties (app)
```java
apply plugin: 'kotlin-kapt'
apply plugin: 'dagger.hilt.android.plugin'

dependencies {
  ...
  implementation "com.google.dagger:hilt-android:2.28-alpha"
  kapt "com.google.dagger:hilt-android-compiler:2.28-alpha"
  implementation 'androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha01'
  kapt 'androidx.hilt:hilt-compiler:1.0.0-alpha01'
}
```


### Time to create Hilt classes

#### Annotate your Application class
```kotlin
@HiltAndroidApp
class DBHiltApplication : Application() {
...
}
```

#### Create Retrofit part: DataSource + Repository
```kotlin
interface ApiDataSource {

    @GET("api")
    suspend fun pixabayImages(
        @Query("key") key: String?,
        @Query("q") q: String?,
        @Query("image_type") imageType: String?,
        @Query("pretty") pretty: Boolean?
    ): Response<PixabayImagesOut>

}
```
```kotlin
@Singleton
class ApiRepository @Inject constructor(private val dataSource: ApiDataSource) {

    suspend fun launchPixabayImages(pixabayIn: PixabayImagesIn): Response<PixabayImagesOut> = this.dataSource.pixabayImages(
        pixabayIn.key,
        pixabayIn.q,
        pixabayIn.imageType,
        pixabayIn.pretty
    )

}
```

#### Create the Module class
This type of Hilt class allows you to create and have the instance of any class in any point of the project.
In our case, Retrofit DataSource and Repository are defined to use them to get basic data from the Pixabay server.

```kotlin
@Module
@InstallIn(ApplicationComponent::class)
class ApiModule {

    @Provides
    fun provideBaseUrl() = BuildConfig.BASE_URL

    @Provides
    @Singleton
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
    } else {
        OkHttpClient.Builder().build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, BASE_URL: String): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideApiDataSource(retrofit: Retrofit) = retrofit.create(ApiDataSource::class.java)

    @Provides
    @Singleton
    fun provideApiRepository(dataSource: ApiDataSource) = ApiRepository(dataSource)
}
```

#### Annotate your ViewModel
To get the Repository instance into your ViewModel, the _@ViewModelInject_ annotation is required.
```kotlin
class MainViewModel @ViewModelInject constructor(private val repository: ApiRepository) : ViewModel() {
...
}
```

#### Annotate your View (Activity/Fragment)
Finally, to get and execute the ViewModel annotated from your View, it is essential to use _@AndroidEntryPoint annotation.
```kotlin
@AndroidEntryPoint
class MainFragment : Fragment() {

  private lateinit var viewModel: MainViewModel

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    this.viewModel = ViewModelProvider(this, defaultViewModelProviderFactory).get(MainViewModel::class.java)
    ...
  }

}
```


## DataBinding
### Add the next dependencies before to start
#### gradle.properties (app)
```java
android {
  ...
  buildFeatures {
    dataBinding true
  }
}

dependencies {
  ...
  kapt 'com.android.databinding:compiler:3.1.4'
}
```

### Modify your xml where you want to use DataBinding using _layout_ and _data_ tags
In this case, _listVisible_ variable is defined to manage the visibility of a list and an empty information label.

```xml
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools">

    <data>

        <import type="android.view.View" />

        <variable
            name="listVisible"
            type="java.lang.Boolean" />
    </data>

    <androidx.constraintlayout.widget.ConstraintLayout
        android:id="@+id/main"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        tools:context=".feature.main.view.MainFragment">
        
        <androidx.recyclerview.widget.RecyclerView
            android:id="@+id/recyclerView"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:visibility="@{listVisible ? View.VISIBLE : View.GONE, default=gone}" />

        <TextView
            android:id="@+id/emptyView"
            style="@style/PrimaryText"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="@string/generic_empty_label"
            android:visibility="@{listVisible ? View.GONE : View.VISIBLE, default=gone}"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintLeft_toLeftOf="parent"
            app:layout_constraintRight_toRightOf="parent"
            app:layout_constraintTop_toTopOf="parent" />
            
    </androidx.constraintlayout.widget.ConstraintLayout>
    
</layout>
```

### Manage the behaviour from the View 
Finally, after adding _layout_ and _data_ tags into your xml, DataBinding library generates a class (_your xml name + Binding_) to get the instances of your views.

```kotlin
private lateinit var binding: MainFragmentBinding

override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
  super.onViewCreated(view, savedInstanceState)

  this.binding = MainFragmentBinding.bind(view)
  ...
}

private fun showList() {
  this.binding.listVisible = true
}

private fun showEmptyView() {
  this.binding.listVisible = false
}
```
