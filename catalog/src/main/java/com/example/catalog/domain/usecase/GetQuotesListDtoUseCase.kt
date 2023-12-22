package com.example.catalog.domain.usecase

import com.example.catalog.domain.repository.CatalogRepository
import javax.inject.Inject

class GetQuotesListDtoUseCase @Inject constructor(
    private val catalogRepository: CatalogRepository
) {
    suspend operator fun invoke() = catalogRepository.getQuotes()
}