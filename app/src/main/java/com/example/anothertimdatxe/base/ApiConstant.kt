package com.example.anothertimdatxe.base

object ApiConstant {
    const val USER_SESSIONS = "/v1/user_sessions"
    const val DRIVER_SESSIONS = "/v1/driver_sessions"
    const val USER_CHANGE_PASSWORDS = "/v1/user_passwords/{${RequestParam.ID}}"
    const val DRIVER_REGISTRATIONS = "/v1/driver_registrations"
    const val DRIVER_ACTIVATIONS = "/v1/driver_activations"
    const val USER_ACTIVATIONS = "/v1/user_activations"
    const val USER_PASSWORDS = "/v1/user_passwords"
    const val DRIVER_PASSWORDS = "/v1/driver_passwords"
    const val LOGIN_SOCIAL = "/v1/login_social"

    interface httpStatusCode {
        companion object {
            const val OK = 200
            const val CREATE = 201
            const val UNAUTHORIZED = 401
        }
    }
}