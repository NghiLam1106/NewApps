package com.example.do_an_co_so_3.domain.usercase.app_entry

import com.example.do_an_co_so_3.domain.manager.LocalUserManager
import kotlinx.coroutines.flow.Flow

class ReadAppEntry(
    private val loaclUserManager: LocalUserManager
) {
    operator fun invoke(): Flow<Boolean> {
        return loaclUserManager.readAppEntry()
    }
}