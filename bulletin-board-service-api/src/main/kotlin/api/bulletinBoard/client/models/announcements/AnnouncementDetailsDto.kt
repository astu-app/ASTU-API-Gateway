package api.bulletinBoard.client.models.announcements

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class AnnouncementDetailsDto(
    val id: String,
    val content: String,
    val authorName: String,
    val viewsCount: Int,
    val audienceSize: Int,
    val surveys: List<api.bulletinBoard.client.models.surveys.details.SurveyDetailsDto>?,
    val publishedAt: LocalDateTime?,
    val hiddenAt: LocalDateTime?,
    val delayedHidingAt: LocalDateTime?,
    val delayedPublishingAt: LocalDateTime?,
    val audience: List<api.bulletinBoard.client.models.users.CheckableUserSummaryDto>,
)