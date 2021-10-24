package com.main.noteapp.feature_login.domain.use_case

import com.main.noteapp.feature_login.data.repository.LoginRepositoryImpl
import com.main.noteapp.feature_login.domain.repository.LoginRepository
import com.main.noteapp.feature_note.domain.model.InvalidNoteException
import com.main.noteapp.feature_note.domain.util.Constants.FILL_ALL_VALUES_ERROR
import com.main.noteapp.utils.Ressource
import javax.inject.Inject

class RegisterUseCase(
    private val repositoryImpl: LoginRepository
) {
    suspend operator fun invoke(email: String, password: String): Ressource<String> {
        if (email.isBlank()) {
            return Ressource.error(FILL_ALL_VALUES_ERROR, null)
        }
        if (password.isBlank()) {
            return Ressource.error(FILL_ALL_VALUES_ERROR, null)
        }
        return repositoryImpl.login(email, password)
    }
}