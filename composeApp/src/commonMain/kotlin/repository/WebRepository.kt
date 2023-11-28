package repository

import model.QueryResult

interface WebRepository {

    suspend fun searchRepos(
        query: String,
        sort: String? = null,
        order: String? = null,
    ): QueryResult?
}