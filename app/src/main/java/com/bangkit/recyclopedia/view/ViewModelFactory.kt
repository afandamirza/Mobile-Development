package com.bangkit.recyclopedia.view

//class ViewModelFactory(private val preference: UserPreference, private val context: Context) : ViewModelProvider.NewInstanceFactory() {
//
//    @Suppress("UNCHECKED_CAST")
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        return when {
//            modelClass.isAssignableFrom(SplashViewModel::class.java) -> {
//                SplashViewModel(preference) as T
//            }
//            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
//        }
//    }
//
//}