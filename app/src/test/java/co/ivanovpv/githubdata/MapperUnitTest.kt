package co.ivanovpv.githubdata

import co.ivanovpv.githubdata.data.toDomain
import co.ivanovpv.githubdata.data.toDto
import co.ivanovpv.githubdata.model.GithubUser
import com.google.gson.Gson
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class MapperUnitTest {
    private lateinit var json: String

    @Before
    fun readJson() {
        val input = this.javaClass.classLoader?.getResourceAsStream("test_user.json")
        json = input?.bufferedReader().use { it?.readText() ?: "" }
    }

    @Test
    fun mapping_isCorrect() {
        val githubUser = Gson().fromJson(json, GithubUser::class.java)
        val githubUserDto = githubUser.toDto()
        val githubUser2 = githubUserDto.toDomain()
        val githubUserDto2 = githubUser2.toDto()
        assertEquals(githubUser, githubUser2)
        assertEquals(githubUserDto, githubUserDto2)
    }
}