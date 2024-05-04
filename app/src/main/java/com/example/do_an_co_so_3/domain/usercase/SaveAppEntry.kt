package com.example.do_an_co_so_3.domain.usercase

import com.example.do_an_co_so_3.domain.manager.LocalUserManager

class SaveAppEntry(
    private val loaclUserManager: LocalUserManager
) {
    suspend operator fun invoke() {
        loaclUserManager.saveAppEntry()
    }
}