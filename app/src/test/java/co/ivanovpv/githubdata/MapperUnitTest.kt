package co.ivanovpv.githubdata

import co.ivanovpv.githubdata.api.model.GithubUserDto
import co.ivanovpv.githubdata.data.toDomain
import co.ivanovpv.githubdata.data.toDto
import co.ivanovpv.githubdata.domain.model.GithubUser
import com.google.gson.Gson
import kotlinx.serialization.json.Json
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
    fun testMappingGithubUser() {
        val githubUserDto = Json.decodeFromString<GithubUserDto>(json)
        val githubUser = githubUserDto.toDomain()
        val githubUserDto2 = githubUser.toDto()
        val githubUser2 = githubUserDto2.toDomain()
        assertEquals(githubUser, githubUser2)
        assertEquals(githubUserDto, githubUserDto2)
    }
}