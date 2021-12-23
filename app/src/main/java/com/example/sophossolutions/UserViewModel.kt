package com.example.sophossolutions

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sophossolutions.models.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/*
@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepositoryImp: UserRepository
) : ViewModel() {

    private val _isLoading: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }

    val users: LiveData<List<User>> by lazy {
        userRepositoryImp.geAllUser()
    }

    val isLoading: LiveData<Boolean> get() = _isLoading

    fun addItem() {
        if (_isLoading.value == false) {
            viewModelScope.launch(Dispatchers.IO) {
                _isLoading.postValue(true)
                userRepositoryImp.getNewUser()
                _isLoading.postValue(false)
            }
        }
    }

    fun deleteItem(toDelete: User) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepositoryImp.deleteUser(toDelete)
        }
    }
}*/


