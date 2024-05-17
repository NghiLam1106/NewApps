package com.example.do_an_co_so_3.presentation.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.do_an_co_so_3.domain.model.Article
import com.example.do_an_co_so_3.domain.usercase.news.AddArticle
import com.example.do_an_co_so_3.domain.usercase.news.DeleteArticle
import com.example.do_an_co_so_3.domain.usercase.news.GetSavedArticle
import com.example.do_an_co_so_3.util.UIComponent
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getSavedArticleUseCase: GetSavedArticle,
    private val deleteArticleUseCase: DeleteArticle,
    private val addArticleUseCase: AddArticle
) : ViewModel() {

    var sideEffect by mutableStateOf<UIComponent?>(null)
        private set

    fun onEvent(event: DetailsEvent) {
        when (event) {
            is DetailsEvent.UpsertDeleteArticle -> {
                viewModelScope.launch {
                    val userId = Firebase.auth.currentUser?.uid
                    val userID = event.article.userId
                    if (userId != null) {
                        // Người dùng đã đăng nhập
                        if (userId == userID) {
                            // Bài báo thuộc về người dùng hiện tại
                            val article = getSavedArticleUseCase(url = event.article.url)
                            if (article == null) {
                                // Bài báo chưa tồn tại, thực hiện thêm mới
                                upsertArticle(article = event.article, userId = userId)
                            } else {
                                // Bài báo đã tồn tại, thực hiện xóa
                                deleteArticle(article = event.article)
                            }
                        } else {
                            // Bài báo không thuộc về người dùng hiện tại, thực hiện thêm mới
                            upsertArticle(article = event.article, userId = userId)
                        }
                    }
                    else {
                        sideEffect = UIComponent.Toast("Cần đăng nhập")
                    }
                }
            }

            is DetailsEvent.RemoveSideEffect -> {
                sideEffect = null
            }
        }
    }

    private suspend fun deleteArticle(article: Article) {
        deleteArticleUseCase(article)
        sideEffect = UIComponent.Toast("Xóa thành công")
    }

    private fun upsertArticle(article: Article, userId: String) {
        addArticleUseCase(article, userId)
        sideEffect = UIComponent.Toast("Lưu thành công")
    }

}