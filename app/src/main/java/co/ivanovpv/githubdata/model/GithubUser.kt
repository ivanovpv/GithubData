package co.ivanovpv.githubdata.model

data class GithubUser(
	val id: Int = 0,
	val login: String? = null,
	val nodeId: String? = null,
	val avatarUrl: String? = null,
	val gravatarId: String? = null,
	val url: String? = null,
	val htmlUrl: String? = null,
	val followersUrl: String? = null,
	val followingUrl: String? = null,
	val gistsUrl: String? = null,
	val starredUrl: String? = null,
	val subscriptionsUrl: String? = null,
	val organizationsUrl: String? = null,
	val reposUrl: String? = null,
	val eventsUrl: String? = null,
	val receivedEventsUrl: String? = null,
	val type: String? = null,
	val siteAdmin: Boolean = false
)