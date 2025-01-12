package com.kleinreveche.tictactoe.presentation.navigation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.kleinreveche.tictactoe.domain.model.AppSetting
import com.kleinreveche.tictactoe.domain.model.AppSettings
import com.kleinreveche.tictactoe.domain.model.LocalPlayer
import com.kleinreveche.tictactoe.domain.model.PLAYER_O
import com.kleinreveche.tictactoe.domain.model.PLAYER_X
import com.kleinreveche.tictactoe.domain.repository.AppSettingRepository
import com.kleinreveche.tictactoe.domain.repository.LocalPlayerRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class ScreenMainViewModel(
    private val appSettingRepository: AppSettingRepository,
    private val localPlayerRepository: LocalPlayerRepository,
) : ViewModel() {
    val defaultPlayer = appSettingRepository.getAppSetting(AppSettings.LAST_PLAYER_VS_COMPUTER)
    val lastPlayer1 = appSettingRepository.getAppSetting(AppSettings.LAST_PLAYER_1)
    val lastPlayer2 = appSettingRepository.getAppSetting(AppSettings.LAST_PLAYER_2)
    var multiplayerShowDetails by mutableStateOf(false)
    var player1 by mutableStateOf("")
    var player2 by mutableStateOf("")
    var singleplayerType by mutableIntStateOf(0)
    val playerTypes = arrayOf(PLAYER_X, PLAYER_O)
    var computerDifficulty by mutableIntStateOf(0)
    var showBottomSheet by mutableStateOf(false)
    var showLocalVsComputerDetails by mutableStateOf(false)
    var showLocalVsPlayerDetails by mutableStateOf(false)
    var showWebDisclaimerSnackbar by mutableStateOf(true)

    fun upsertSetting(
        scope: CoroutineScope,
        appSetting: AppSetting,
    ) {
        scope.launch { appSettingRepository.upsertAppSetting(appSetting) }
    }

    fun upsertPlayer(
        scope: CoroutineScope,
        playerName: String,
    ) {
        scope.launch {
            if (localPlayerRepository.playerExists(playerName)) return@launch
            localPlayerRepository.upsertPlayer(LocalPlayer(playerName))
        }
    }
}
