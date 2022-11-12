package id.melur.gliandroidtest

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class DataStoreViewModel(private val pref: DataStore) : ViewModel()  {

    fun getMovieId(): LiveData<Int> {
        return pref.getMovieId().asLiveData()
    }

    fun saveData(id: Int) {
        viewModelScope.launch {
            pref.saveDataMovieId(id)
        }
    }

    fun clearData() {
        viewModelScope.launch {
            pref.clearData()
        }
    }
}