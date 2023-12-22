package co.ivanovpv.githubdata.data

import co.ivanovpv.githubdata.api.model.GithubUserDto
import co.ivanovpv.githubdata.domain.model.GithubUser

fun GithubUser.toDto(): GithubUserDto {
	return GithubUserDto(
		id = id,
		login = login,
		nodeId = nodeId,
		avatarUrl = avatarUrl,
		gravatarId = gravatarId,
		url = url,
		htmlUrl = htmlUrl,
		followersUrl = followersUrl,
		followingUrl = followingUrl,
		gistsUrl = gistsUrl,
		starredUrl = starredUrl,
		subscriptionsUrl = subscriptionsUrl,
		organizationsUrl = organizationsUrl,
		reposUrl = reposUrl,
		eventsUrl = eventsUrl,
		receivedEventsUrl = receivedEventsUrl,
		type = type,
		siteAdmin = siteAdmin
	)
}

fun GithubUserDto.toDomain(): GithubUser {
	return GithubUser(
		id = id,
		login = login,
		nodeId = nodeId,
		avatarUrl = avatarUrl,
		gravatarId = gravatarId,
		url = url,
		htmlUrl = htmlUrl,
		followersUrl = followersUrl,
		followingUrl = followingUrl,
		gistsUrl = gistsUrl,
		starredUrl = starredUrl,
		subscriptionsUrl = subscriptionsUrl,
		organizationsUrl = organizationsUrl,
		reposUrl = reposUrl,
		eventsUrl = eventsUrl,
		receivedEventsUrl = receivedEventsUrl,
		type = type,
		siteAdmin = siteAdmin
	)
}