package ds.githubclient.data.network.model

import com.google.gson.annotations.SerializedName
import java.util.*


data class User(
    @SerializedName("login") var login: String? = null,
    @SerializedName("id") var id: Int? = null,
    @SerializedName("node_id") var nodeId: String? = null,
    @SerializedName("avatar_url") var avatarUrl: String? = null,
    @SerializedName("gravatar_id") var gravatarId: String? = null,
    @SerializedName("url") var url: String? = null,
    @SerializedName("html_url") var htmlUrl: String? = null,
    @SerializedName("followers_url") var followersUrl: String? = null,
    @SerializedName("following_url") var followingUrl: String? = null,
    @SerializedName("gists_url") var gistsUrl: String? = null,
    @SerializedName("starred_url") var starredUrl: String? = null,
    @SerializedName("subscriptions_url") var subscriptionsUrl: String? = null,
    @SerializedName("organizations_url") var organizationsUrl: String? = null,
    @SerializedName("repos_url") var reposUrl: String? = null,
    @SerializedName("events_url") var eventsUrl: String? = null,
    @SerializedName("received_events_url") var receivedEventsUrl: String? = null,
    @SerializedName("type") var type: String? = null,
    @SerializedName("site_admin") var siteAdmin: Boolean? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("company") var company: String? = null,
    @SerializedName("blog") var blog: String? = null,
    @SerializedName("location") var location: String? = null,
    @SerializedName("email") var email: String? = null,
    @SerializedName("hireable") var hireable: Boolean? = null,
    @SerializedName("bio") var bio: String? = null,
    @SerializedName("public_repos") var publicRepos: Int? = null,
    @SerializedName("public_gists") var publicGists: Int? = null,
    @SerializedName("followers") var followers: Int? = null,
    @SerializedName("following") var following: Int? = null,
    @SerializedName("created_at") var createdAt: Date? = null,
    @SerializedName("updated_at") var updatedAt: Date? = null
) {
    fun update(user: User): User {
        user.login?.let { login = it }
        user.id?.let { id = it }
        user.nodeId?.let { nodeId = it }
        user.avatarUrl?.let { avatarUrl = it }
        user.gravatarId?.let { gravatarId = it }
        user.url?.let { url = it }
        user.htmlUrl?.let { htmlUrl = it }
        user.followersUrl?.let { followersUrl = it }
        user.followingUrl?.let { followingUrl = it }
        user.gistsUrl?.let { gistsUrl = it }
        user.starredUrl?.let { starredUrl = it }
        user.subscriptionsUrl?.let { subscriptionsUrl = it }
        user.organizationsUrl?.let { organizationsUrl = it }
        user.reposUrl?.let { reposUrl = it }
        user.eventsUrl?.let { eventsUrl = it }
        user.receivedEventsUrl?.let { receivedEventsUrl = it }
        user.type?.let { type = it }
        user.siteAdmin?.let { siteAdmin = it }
        user.name?.let { name = it }
        user.company?.let { company = it }
        user.blog?.let { blog = it }
        user.location?.let { location = it }
        user.email?.let { email = it }
        user.hireable?.let { hireable = it }
        user.bio?.let { bio = it }
        user.publicRepos?.let { publicRepos = it }
        user.publicGists?.let { publicGists = it }
        user.followers?.let { followers = it }
        user.following?.let { following = it }
        user.createdAt?.let { createdAt = it }
        user.updatedAt?.let { updatedAt = it }
        return user
    }
}