package api.bulletinBoard.client.models.announcements

import api.bulletinBoard.client.models.surveys.details.SurveyDetailsDto
import api.bulletinBoard.client.models.userGroups.UserGroupHierarchyDto
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

/**
 * DTO с данными для редактирования объявления
 * @param id Идентификатор объявления
 * @param authorName Имя автора объявления
 * @param content Текстовое содержимое объявления
 * @param viewsCount Количество просмотров объявления
 * @param audienceSize Размер аудитории объявления
 * @param audienceHierarchy Иерархия групп пользователей
 * @param surveys Массив опросов объявления
 * @param publishedAt Момент публикации объявления. Объявление не является опубликованным, если null
 * @param hiddenAt Момент сокрытия объявления. Объявление не является скрытым, если null
 * @param delayedHidingAt Момент отложенного сокрытия объявления. Объявление не будет скрыто автоматически, если null
 * @param delayedPublishingAt Момент отложенной публикации объявления. Объявление не будет опубликовано автоматически, если null
 */
@Serializable
data class ContentForAnnouncementUpdatingDto @OptIn(ExperimentalSerializationApi::class) constructor(
    @JsonNames("id")
    val id: String,
    @JsonNames("authorName")
    val authorName: String,
    @JsonNames("content")
    val content: String,
    @JsonNames("viewsCount")
    val viewsCount: Int,
    @JsonNames("audienceSize")
    val audienceSize: Int,
    @JsonNames("audienceHierarchy")
    val audienceHierarchy: UserGroupHierarchyDto,
    @JsonNames("surveys")
    val surveys: List<SurveyDetailsDto>?,
    @JsonNames("publishedAt")
    val publishedAt: LocalDateTime?,
    @JsonNames("hiddenAt")
    val hiddenAt: LocalDateTime?,
    @JsonNames("delayedHidingAt")
    val delayedHidingAt: LocalDateTime?,
    @JsonNames("delayedPublishingAt")
    val delayedPublishingAt: LocalDateTime?
)