package com.example.basearchitecture.shared.exceptions

class BadRequestException(val mMessage: String?) : Exception(mMessage) {}