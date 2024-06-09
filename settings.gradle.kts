plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
rootProject.name = "gateway"

include("auth-api")
include("account-data-api")
include("university-data-api")
include("request-service-api")
include("universal-request-service-api")
include("chat-service-api")
