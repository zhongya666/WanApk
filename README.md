# WanApk

## 项目由来
5年开发经验的大佬过来了，快欢迎我。<br>
工作之余，手工打造一款符合当前主流的Android项目。<br>
关注不迷路，我会持续更新...<br>

## 技术简介
`Retrofit + ARouter + 组件化 + Jetpack + pagging3 + MVVM + 协程 + Koin + Coil`<br>

## 效果图
<img src="https://gitee.com/zhongya666/WanApk/raw/master/preview/1.jpg" width="200" height="400"/>
<img src="https://gitee.com/zhongya666/WanApk/raw/master/preview/2.jpg" width="200" height="400"/>
<img src="https://gitee.com/zhongya666/WanApk/raw/master/preview/3.jpg" width="200" height="400"/><br>
<img src="https://gitee.com/zhongya666/WanApk/raw/master/preview/1.gif" width="200" height="400"/>
<img src="https://gitee.com/zhongya666/WanApk/raw/master/preview/4.png" width="400" height="200"/>

## 项目详情
##### 1、如何独立运行一个Module？
我们只需要在根目录下`gradle.properties`中添加一个`singleModule`标志位，该标志位可以用来表示当前Module是否是独立模块，`true`表示处于独立模块，可单独运行，`false`则表示是一个library。
```
org.gradle.jvmargs=-Xmx2048m
android.useAndroidX=true
android.enableJetifier=true
#是否是需要单独运行某个模块
singleModule=true
#singleModule=false
```

在每个`Module`的`build.gradle`中加入`singleModule`的判断，运行总App时，子Module是属于`library`，而独立运行时，子Module是属于`application`。配置如下：
```
if (singleModule.toBoolean()) {
    apply plugin: 'com.android.application'
} else {
    apply plugin: 'com.android.library'
}
```
当然`AndroidManifest`文件也同理，毕竟在`singleModule`模式下每个可以独立运行的Module都要单独设置应用图标、名称、<intent-filter>等。
```
sourceSets{
        main {
            if (singleModule.toBoolean()) {
                manifest.srcFile 'src/main/alone/AndroidManifest.xml'
            } else {
                manifest.srcFile 'src/main/AndroidManifest.xml'
            }
        }
    }
```
编译运行后，桌面会出现多个应用图标，如下：（考虑到组件化的使用场景，就独立出一个跟app模块耦合性低的模块。玩库是为我另一个项目[WanLibrary](https://gitee.com/zhongya666/WanLibrary.git)而作，主要对我封装的万能小控件做一些演示，其代码简单易懂）
<img src="https://gitee.com/zhongya666/WanApk/raw/master/preview/5.jpg" width="400" height="300"/><br>

那问题来了，组件间如何通信？<br>
主要借助阿里的路由框架[ARouter](https://github.com/alibaba/ARouter)

##### 2、Jetpack组件
###### 2.1、Navigation
`Navigation`是一个管理Fragment切换的组件，支持可视化处理。开发者也完全不用操心Fragment的切换逻辑。
在使用`Navigation`的过程中，会出现点击back按键，界面会重新走了onCreate生命周期，并且将页面重构。目前比较好的解决方法是自定义`NavHostFragment`，将内部replace替换为show/hide。

###### 2.2、Paging3
Paging是一个分页组件，主要与Recyclerview结合分页加载数据。具体使用可参考`首页`和`项目`模块。
`UI层：`
```
class DailyQuestionFragment : BaseAppFragment<HomeFragmentDailyQuestionBinding>(
    ...
    override fun lazyLoadData() {
        lifecycleScope.launchWhenCreated {
            viewModel.dailyQuestionPagingFlow().collectLatest {
                pagingAdapter.submitData(it)
            }
        }
    }
    ...
}
```
`ViewModel层：`
```
class DailyQuestionViewModel(
    val repo: HomeRepo,
    val personalRepo: PersonalRepo
) : BaseViewModel() {
    ...
    /**
     * 请求每日一问数据
     */
    fun dailyQuestionPagingFlow(): Flow<PagingData<ArticleData>> =
        repo.getDailyQuestion().cachedIn(viewModelScope)
   ...
}
```
`Repository层`
```
class HomeRepo : BaseRepository() {
    ...
    /**
     * 请求每日一问
     */
    fun getDailyQuestion(): Flow<PagingData<ArticleData>> {
        return Pager(config) {
            DailyQuestionPagingSource(service)
        }.flow
    }
    ...
}
```
`PagingSource层：`
```
/**
 * GitHub/Gitee：zhongya666 2021/7/8 9:41
 * 每日一问数据源，主要配合Paging3进行数据请求与显示
 */

class DailyQuestionPagingSource(private val service: HomeApi) :
    PagingSource<Int, ArticleData>() {
    override fun getRefreshKey(state: PagingState<Int, ArticleData>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleData> {
        return try {
            val pageNum = params.key ?: 1
            val data = service.getDailyQuestion(pageNum)
            val preKey = if (pageNum > 1) pageNum - 1 else null
            LoadResult.Page(data.data?.datas!!, prevKey = preKey, nextKey = pageNum + 1)

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
```
## 版本
持续更新
下期会手写h5，嵌入到webview中，并实现Android与h5交互`（混合开发）`

## 感谢
[付十一](https://juejin.cn/post/6965464707314860040)
API： 鸿洋大大提供的 WanAndroid API

## 第三方开源库：
[Retrofit](https://github.com/square/retrofit)
[OkHttp](https://github.com/square/okhttp)
[Gson](https://github.com/google/gson)
[Coil](https://github.com/coil-kt/coil)
[Koin](https://github.com/InsertKoinIO/koin)
[Arouter](https://github.com/alibaba/ARouter)
[LoadSir](https://github.com/KingJA/LoadSir) <br>
[大佬的整合库](https://gitee.com/zhongya666/WanLibrary.git)
另外还有上面没列举的一些优秀的第三方开源库，感谢开源。

## 联系方式
 * 可能会因为工作忙碌原因没有及时回复，请联系我的个人微信号：`zhongya_666`