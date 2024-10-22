package co.ivanovpv.githubdata.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class ApiError(
    @SerialName("message")
    val message: String,
    @SerialName("documentation_url")
    val documentationUrl: String? = null,
)
