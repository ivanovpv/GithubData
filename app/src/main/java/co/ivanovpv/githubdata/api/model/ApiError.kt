package co.ivanovpv.githubdata.api.model

import com.google.gson.annotations.SerializedName

class ApiError {
    @SerializedName("message")
    var message: String? = null

    @SerializedName("documentation_url")
    var documentationUrl: String? = null
}
