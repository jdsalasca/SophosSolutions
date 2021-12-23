package com.example.sophossolutions.repository
//
//import androidx.lifecycle.LiveData
//import androidx.room.Delete
//import com.example.sophossolutions.datasource.RestDataSource
//import com.example.sophossolutions.models.User
//import com.example.sophossolutions.models.UserDao
//import kotlinx.coroutines.delay
//import javax.inject.Inject
//
//interface UserRepository {
//    suspend fun getNewUser(): User
//    suspend fun deleteUser(toDelete: User)
//    fun geAllUser(): LiveData<List<User>>
//}
//
//class UserRepositoryImp @Inject constructor(
//
//    private val dataSource: RestDataSource,
//    private val userDao: UserDao
//) : UserRepository {
//
//    override suspend fun deleteUser(toDelete: User) = userDao.delete(toDelete)
//
//    override fun geAllUser(): LiveData<List<User>> = userDao.getAll()
//    override suspend fun getNewUser(): User {
//        delay(2000)
//        val name = dataSource.getUserName().results[0].name!!
//        val location = dataSource.getUserLocation().results[0].location!!
//        val picture = dataSource.getUserPicture().results[0].picture!!
//        val user = User(name.first, name.last, location.city, picture.thumbnail)
//        userDao.insert(user)
//        return user;
//
//    }
//}
