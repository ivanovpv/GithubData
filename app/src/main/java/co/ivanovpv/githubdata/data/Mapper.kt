package co.ivanovpv.githubdata.data

import co.ivanovpv.githubdata.api.model.GithubUserDTO
import co.ivanovpv.githubdata.domain.model.GithubUser

fun GithubUser.toDto(): GithubUserDTO {
	return GithubUserDTO(id, login, nodeId, avatarUrl, gravatarId, url, htmlUrl, followersUrl, followingUrl, gistsUrl, starredUrl, subscriptionsUrl, organizationsUrl, reposUrl, eventsUrl, receivedEventsUrl, type, siteAdmin)
}

fun GithubUserDTO.toDomain(): GithubUser {
	return GithubUser(id, login, nodeId, avatarUrl, gravatarId, url, htmlUrl, followersUrl, followingUrl, gistsUrl, starredUrl, subscriptionsUrl, organizationsUrl, reposUrl, eventsUrl, receivedEventsUrl, type, siteAdmin)
}