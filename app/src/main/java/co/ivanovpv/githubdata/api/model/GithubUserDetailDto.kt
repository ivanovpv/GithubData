package co.ivanovpv.githubdata.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GithubUserDetailDto(
    @SerialName("login")
    var login: String,
    @SerialName("id")
    var id: Int,
    @SerialName("node_id")
    var nodeId: String,
    @SerialName("avatar_url")
    var avatarUrl: String,
    @SerialName("gravatar_id")
    var gravatarId: String? = null,
    @SerialName("url")
    var url: String,
    @SerialName("html_url")
    var htmlUrl: String,
    @SerialName("followers_url")
    var followersUrl: String,
    @SerialName("following_url")
    var followingUrl: String,
    @SerialName("gists_url")
    var gistsUrl: String,
    @SerialName("starred_url")
    var starredUrl: String,
    @SerialName("subscriptions_url")
    var subscriptionsUrl: String,
    @SerialName("organizations_url")
    var organizationsUrl: String,
    @SerialName("repos_url")
    var reposUrl: String,
    @SerialName("events_url")
    var eventsUrl: String,
    @SerialName("received_events_url")
    var receivedEventsUrl: String,
    @SerialName("type")
    var type: String,
    @SerialName("site_admin")
    var siteAdmin: Boolean,
    @SerialName("name")
    var name: String? = null,
    @SerialName("company")
    var company: String? = null,
    @SerialName("blog")
    var blog: String? = null,
    @SerialName("location")
    var location: String? = null,
    @SerialName("email")
    var email: String? = null,
    @SerialName("hireable")
    var hireable: Boolean? = null,
    @SerialName("bio")
    var bio: String? = null,
    @SerialName("twitter_username")
    var twitterUsername: String? = null,
    @SerialName("public_repos")
    var publicRepos: Int,
    @SerialName("public_gists")
    var publicGists: Int,
    @SerialName("followers")
    var followers: Int,
    @SerialName("following")
    var following: Int,
    @SerialName("created_at")
    var createdAt: String,
    @SerialName("updated_at")
    var updatedAt: String,
)

