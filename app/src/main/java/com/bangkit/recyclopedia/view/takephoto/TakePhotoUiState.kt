package com.bangkit.recyclopedia.view.takephoto

sealed class TakePhotoUiState {
    object Idle : TakePhotoUiState()
    object StartGuessing : TakePhotoUiState()
    object ClaimPoint : TakePhotoUiState()
    object Uploading : TakePhotoUiState()
}