package com.bangkit.recyclopedia.view.take_photo

sealed class TakePhotoUiState {
    object Idle : TakePhotoUiState()
    object StartGuessing : TakePhotoUiState()
    object ClaimPoint : TakePhotoUiState()
}