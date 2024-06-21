import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import com.bangkit.recyclopedia.api.response.HistoryItemsResponse


@Dao
interface AppDao {
    @androidx.room.Query("SELECT * FROM history_items")
    fun getAllStory(): PagingSource<Int, HistoryItemsResponse>
}