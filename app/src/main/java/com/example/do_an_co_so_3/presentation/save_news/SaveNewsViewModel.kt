package com.example.do_an_co_so_3.presentation.save_news

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.do_an_co_so_3.domain.repository.NewsRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SaveNewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {
    private val _state = mutableStateOf(SaveNewsState())
    val state: State<SaveNewsState> = _state

    // Xác thực người dùng bằng Firebase Auth
    val auth = Firebase.auth
    private var userId = Firebase.auth.currentUser?.uid

    init {
        // Lắng nghe sự thay đổi trong tài khoản
        auth.addAuthStateListener { firebaseAuth ->
            val updatedUser = firebaseAuth.currentUser
            val updatedUserId = updatedUser?.uid
            if (updatedUserId != userId) {
                // Nếu userId mới khác với userId cũ
                // Thực hiện truy vấn danh sách báo mới
                // và cập nhật giao diện người dùng
                userId = updatedUserId
                if (updatedUserId == null) {
                    _state.value = SaveNewsState()
                } else {
                    // Truy vấn danh sách báo theo userId mới
                    getArticles(updatedUserId)
                }
            }
        }
    }

    private fun getArticles(userId: String) {
        newsRepository.getArticlesFromData(userId).onEach {
            _state.value = _state.value.copy(articles = it)
        }.launchIn(viewModelScope)
    }
}
