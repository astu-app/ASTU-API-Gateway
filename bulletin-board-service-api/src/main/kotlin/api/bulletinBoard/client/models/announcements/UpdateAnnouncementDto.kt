package api.bulletinBoard.client.models.announcements

import api.bulletinBoard.client.models.common.UpdateIdentifierListDto
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

/**
 * Dto для обновления объявления
 * @param id Уникальный идентификатор объявления.
 * @param content Текстовое содержимое объявления. Может быть null, если значение свойства не изменилось.
 * @param audienceIds Список идентификаторов аудиторий, которым предназначено объявление. Может быть null, если значения свойства не изменились.
 * @param attachmentIds Список идентификаторов прикрепленных файлов к объявлению. Может быть null, если значения свойства не изменились.
 * @param delayedPublishingAtChanged Флаг, указывающий, было ли изменено значение момента отложенной публикации.
 * @param delayedPublishingAt Момент отложенной публикации объявления. Может быть null, если отложенная публикация не предполагается.
 * @param delayedHidingAtChanged Флаг, указывающий, было ли изменено значение момента отложенного сокрытия.
 * @param delayedHidingAt Момент отложенного сокрытия объявления. Может быть null, если отложенное сокрытие не предполагается.
 */
@Serializable
data class UpdateAnnouncementDto(
    val id: String,
    val content: String? = null,
    val audienceIds: UpdateIdentifierListDto? = null,
    val attachmentIds: UpdateIdentifierListDto? = null,
    val delayedPublishingAtChanged: Boolean,
    val delayedPublishingAt: LocalDateTime? = null,
    val delayedHidingAtChanged: Boolean,
    val delayedHidingAt: LocalDateTime? = null
)