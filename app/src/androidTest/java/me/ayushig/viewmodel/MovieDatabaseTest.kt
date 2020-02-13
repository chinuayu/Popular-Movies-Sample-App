package me.ayushig.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.ayushi.samplefortransferwise.waitForValue
import junit.framework.Assert.assertNotNull
import me.ayushig.viewmodel.db.MoviesDAO
import me.ayushig.viewmodel.db.MoviesData
import me.ayushig.viewmodel.db.MoviesDataBase
import org.junit.*
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4ClassRunner::class)
class MovieDatabaseTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private var moviesDAO: MoviesDAO? = null
    private var moviesDatabase: MoviesDataBase? = null

    @Before
    fun setup() {
        moviesDatabase = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().targetContext,
            MoviesDataBase::class.java
        ).allowMainThreadQueries().build()

        moviesDAO = moviesDatabase!!.moviesDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        moviesDatabase!!.close()
    }

    @Test
    fun basicAssertions() {
        assertNotNull(moviesDatabase)
        assertNotNull(moviesDAO)
    }

    @Test
    fun testInsertion() {
        val movie = MoviesData(
            1, 1, 1.2f, "demo", 1.2f,
            null, null, false, "hello"
        )

        moviesDAO!!.insertMovie(movie)

        val allMoviesData = moviesDAO!!.getMovieItems().waitForValue()
        Assert.assertEquals(allMoviesData[0].title, movie.title)
    }

    @Test
    fun should_Flush_All_Data() {
        moviesDAO?.deleteAllData()
        val allMoviesData = moviesDAO!!.getMovieItems().waitForValue()
        Assert.assertEquals(allMoviesData.size, 0)
    }
}