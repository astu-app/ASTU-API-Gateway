package api.bulletinBoard.client.models.userGroups

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

/**
 * Данные для создания группы пользователей
 * @param users Список пользователей, которые могут быть назначены администратором или участником группы пользователей
 * @param userGroups Список групп пользователей, которые могут быть установлены в качестве родителей или потомков создаваемой группы пользователей. В списке отсутствуют корневые точки графа пользователей
 */
@Serializable
data class GetUsergroupCreateContentDto @OptIn(ExperimentalSerializationApi::class) constructor(
    @JsonNames("users")
    val users: List<api.bulletinBoard.client.models.users.UserSummaryDto>,

    @JsonNames("usergroups")
    val userGroups: List<UserGroupSummaryDto>
)
