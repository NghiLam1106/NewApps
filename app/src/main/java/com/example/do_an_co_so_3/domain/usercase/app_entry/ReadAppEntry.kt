package com.example.do_an_co_so_3.domain.usercase.app_entry

import com.example.do_an_co_so_3.domain.manager.LocalUserManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReadAppEntry @Inject constructor(
    private val loaclUserManager: LocalUserManager
) {
    operator fun invoke(): Flow<Boolean> {
        return loaclUserManager.readAppEntry()
    }
}