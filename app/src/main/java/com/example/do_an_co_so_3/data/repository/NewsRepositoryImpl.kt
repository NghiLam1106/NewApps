package com.example.do_an_co_so_3.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.do_an_co_so_3.data.remote.NewsApi
import com.example.do_an_co_so_3.data.remote.NewsPagingSource
import com.example.do_an_co_so_3.data.remote.SearchNewsPagingSource
import com.example.do_an_co_so_3.domain.model.Article
import com.example.do_an_co_so_3.domain.repository.NewsRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsApi: NewsApi,
) : NewsRepository {

    private val db = FirebaseFirestore.getInstance()

    override fun getNews(sources: List<String>): Flow<PagingData<Article>> {
        return Pager(config = PagingConfig(pageSize = 10), pagingSourceFactory = {
            NewsPagingSource(
                newsApi = newsApi, sources = sources.joinToString(separator = ",")
            )
        }).flow
    }

    override fun searchNews(searchQuery: String, sources: List<String>): Flow<PagingData<Article>> {
        return Pager(config = PagingConfig(pageSize = 10), pagingSourceFactory = {
            SearchNewsPagingSource(
                searchQuery = searchQuery,
                newsApi = newsApi,
                source = sources.joinToString(separator = ",")
            )
        }).flow
    }


    override suspend fun deleteArticle(article: Article) {
        try {
            val querySnapshot =
                Firebase.firestore.collection("News").whereEqualTo("url", article.url).get().await()

            for (document in querySnapshot.documents) {
                document.reference.delete().await()
            }
        } catch (e: Exception) {
            // Xử lý ngoại lệ nếu có
            throw e
        }
    }

    override fun getArticlesFromData(userID: String): Flow<List<Article>> = callbackFlow {
        // Hàm để lấy các bài báo dựa trên userId
        fun fetchArticlesForUserId(userId: String) {
            db.collection("News").whereEqualTo("userId", userId)
                .addSnapshotListener { snapshot, e ->
                    if (e != null) {
                        close(e)
                        return@addSnapshotListener
                    }

                    val articles =
                        snapshot?.documents?.mapNotNull { it.toObject(Article::class.java) }
                            ?: emptyList()
                    trySend(articles).isSuccess
                }
        }

        // Lấy dữ liệu ban đầu
        fetchArticlesForUserId(userID)

        // Lắng nghe sự thay đổi trong userId
        val userIdListener =
            db.collection("Users").document(userID).addSnapshotListener { snapshot, e ->
                    if (e != null) {
                        close(e)
                        return@addSnapshotListener
                    }

                    if (snapshot != null && snapshot.exists()) {
                        // Lấy giá trị userId mới
                        val newUserId = snapshot.getString("userId") ?: return@addSnapshotListener
                        // Lấy các bài báo cho userId mới
                        fetchArticlesForUserId(newUserId)
                    }
                }


        awaitClose {
            // Đóng lắng nghe cho sự thay đổi userId
            userIdListener.remove()
        }
    }

    override suspend fun getArticle(url: String): Article? {
        return try {
            // Truy vấn tài liệu từ Firestore dựa trên trường chứa URL
            val querySnapshot =
                Firebase.firestore.collection("News").whereEqualTo("url", url).get().await()

            // Lặp qua các tài liệu trả về từ truy vấn
            for (document in querySnapshot.documents) {
                // Chuyển đổi tài liệu thành đối tượng Article và trả về nếu tìm thấy
                val article = document.toObject(Article::class.java)
                if (article != null) {
                    return article
                }
            }
            // Trả về null nếu không tìm thấy bài báo
            null
        } catch (e: Exception) {
            println("Đã xảy ra lỗi: ${e.message}")
            null
        }
    }

    override fun addArticle(article: Article, userId: String) {
        userId.let {
            val articleWithUserId = article.copy(userId = userId)
            // Tiếp tục thêm dữ liệu vào Firestore
            Firebase.firestore.collection("News").document().set(articleWithUserId)
                .addOnSuccessListener {
                    // Thành công khi thêm dữ liệu
                    println("Document successfully added with userId!")
                }.addOnFailureListener { e ->
                    // Xảy ra lỗi khi thêm dữ liệu
                    println("Error adding document with userId: $e")
                }
        }
    }
}
