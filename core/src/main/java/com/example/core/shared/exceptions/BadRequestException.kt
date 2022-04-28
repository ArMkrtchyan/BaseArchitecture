package com.example.core.shared.exceptions

class BadRequestException(val mMessage: String?) : Exception(mMessage) {}