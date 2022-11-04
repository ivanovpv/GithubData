package co.ivanovpv.githubdata.data

import co.ivanovpv.githubdata.api.model.GithubUserDTO
import co.ivanovpv.githubdata.model.GithubUser

fun GithubUser.toDto(): GithubUserDTO {
	return GithubUserDTO(id, login, nodeId, avatarUrl, gravatarId, url, htmlUrl, followersUrl, followingUrl, gistsUrl, starredUrl)
}

fun GithubUserDTO.toDomain(): GithubUser {
	return GithubUser(id, login, nodeId, avatarUrl, gravatarId, url, htmlUrl, followersUrl, followingUrl, gistsUrl, starredUrl)
}