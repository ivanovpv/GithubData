package co.ivanovpv.githubdata.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GithubUser(
	val id: Int,
	val login: String,
	val nodeId: String,
	val avatarUrl: String,
	val gravatarId: String? = null,
	val url: String,
	val htmlUrl: String,
	val followersUrl: String,
	val followingUrl: String,
	val gistsUrl: String,
	val starredUrl: String,
	val subscriptionsUrl: String,
	val organizationsUrl: String,
	val reposUrl: String,
	val eventsUrl: String,
	val receivedEventsUrl: String,
	val type: String,
	val siteAdmin: Boolean
) : Parcelable