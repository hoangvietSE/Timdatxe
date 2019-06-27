package com.example.anothertimdatxe.base

object ApiConstant {
    //[USER]
    const val USER_SESSIONS = "/v1/user_sessions"
    const val USER_CHANGE_PASSWORDS = "/v1/user_passwords/{${RequestParam.ID}}"
    const val USER_LIST_POST_CREATED = "/v1/users/{${RequestParam.USER_ID}}/user_posts"
    const val USER_ACTIVATIONS = "/v1/user_activations"
    const val USER_PASSWORDS = "/v1/user_passwords"
    const val USER_INFO = "/v1/users/{${RequestParam.ID}}"

    //[DRIVER]
    const val DRIVER_SESSIONS = "/v1/driver_sessions"
    const val DRIVER_REGISTRATIONS = "/v1/driver_registrations"
    const val DRIVER_ACTIVATIONS = "/v1/driver_activations"
    const val DRIVER_PASSWORDS = "/v1/driver_passwords"

    //Login Soical
    const val LOGIN_SOCIAL = "/v1/login_social"

    //FAQS
    const val SHOW_FAQS = "/v1/faqs"

    interface httpStatusCode {
        companion object {
            const val OK = 200
            const val CREATE = 201
            const val UNAUTHORIZED = 401
        }
    }
}