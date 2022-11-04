//package id.melur.gliandroidtest.database
//
//import android.content.Context
//import androidx.room.Database
//import androidx.room.Room
//import androidx.room.RoomDatabase
//
//@Database(entities = [MovieEntity::class], version = 1)
//abstract class MovieDatabase: RoomDatabase() {
//    abstract fun movieDao(): MovieDao
////    abstract fun noteDao(): NoteDao
//
//    companion object {
//        private var INSTANCE: UserDatabase? = null
//
//        fun getInstance(context: Context): UserDatabase? {
//            if (INSTANCE == null) {
//                synchronized(UserDatabase::class) {
//                    INSTANCE = Room.databaseBuilder(context.applicationContext,
//                        UserDatabase::class.java, "User.db").build()
//                }
//            }
//            return INSTANCE
//        }
//
//        fun destroyInstance() {
//            INSTANCE = null
//        }
//    }
//}