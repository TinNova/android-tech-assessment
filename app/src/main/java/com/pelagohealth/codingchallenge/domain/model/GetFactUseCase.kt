package com.pelagohealth.codingchallenge.domain.model

import com.pelagohealth.codingchallenge.data.repository.FactRepository
import javax.inject.Inject

class GetFactUseCase @Inject constructor(private val repository: FactRepository) {

    suspend fun execute(): Result<Fact> {
        return repository.get()
            .map { dto ->
                Fact(text = dto.text, url = dto.sourceUrl)
            }
            .onFailure { e ->
                println(e)
                //TODO: Implement "Coding on Rails"
                //NOTE: Coding on Rails makes errors explicit, it elevates them to first-class concerns, currently errors are an after thought
                // This can be implemented ourselves using a Sealed Class and generics containing Success(), Failure(), or use a library such as Arrow
            }
    }
}
