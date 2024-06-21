import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.bangkit.recyclopedia.api.ApiService
import com.bangkit.recyclopedia.api.response.HistoryItemsResponse
import com.bangkit.recyclopedia.repository.HistoryPaging


class HistoryRepository(private val apiService: ApiService) {
    private var token = ""

    fun setToken(token: String) {
        this.token = token
    }

    fun getStory(): LiveData<PagingData<HistoryItemsResponse>> {
        return Pager(
            config = PagingConfig(
                pageSize = DEFAULTPAGE
            ),
            pagingSourceFactory = {
                HistoryPaging(apiService, token)
            }
        ).liveData
    }

    companion object {
        const val DEFAULTPAGE = 20

        @Volatile
        private var instance: HistoryRepository? = null
        fun getInstance(
            apiService: ApiService,
        ): HistoryRepository =
            instance ?: synchronized(this) {
                instance ?: HistoryRepository(apiService)
            }.also { instance = it }
    }
}