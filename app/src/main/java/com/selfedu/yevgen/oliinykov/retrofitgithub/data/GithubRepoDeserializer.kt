package com.selfedu.yevgen.oliinykov.retrofitgithub.data

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParseException
import com.selfedu.yevgen.oliinykov.retrofitgithub.data.models.GithubRepo

import java.lang.reflect.Type

class GithubRepoDeserializer : JsonDeserializer<GithubRepo> {

    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): GithubRepo {

        val repoJsonObject = json.asJsonObject
        val name = repoJsonObject.get("name").asString
        val url = repoJsonObject.get("url").asString

        val ownerJsonElement = repoJsonObject.get("owner")
        val ownerJsonObject = ownerJsonElement.asJsonObject
        val owner = ownerJsonObject.get("login").asString

        return GithubRepo(name, owner, url)
    }
}
