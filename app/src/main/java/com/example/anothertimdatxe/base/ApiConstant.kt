package com.example.anothertimdatxe.base

object ApiConstant {
    //[USER]
    const val USER_SESSIONS = "/v1/user_sessions"
    const val USER_CHANGE_PASSWORDS = "/v1/user_passwords/{${RequestParam.ID}}"
    const val USER_LIST_POST_CREATED = "/v1/users/{${RequestParam.USER_ID}}/user_posts"
    const val USER_ACTIVATIONS = "/v1/user_activations"
    const val USER_PASSWORDS = "/v1/user_passwords"
    const val USER_INFO = "/v1/users/{${RequestParam.ID}}"
    const val USER_UPDATE_INFO = "/v1/users/{${RequestParam.ID}}"
    const val USER_POST_CREATED = "/v1/user_posts"
    const val USER_HISTORY = "/v1/user_history/{${RequestParam.ID}}"

    //[DRIVER]
    const val DRIVER_SESSIONS = "/v1/driver_sessions"
    const val DRIVER_REGISTRATIONS = "/v1/driver_registrations"
    const val DRIVER_ACTIVATIONS = "/v1/driver_activations"
    const val DRIVER_PASSWORDS = "/v1/driver_passwords"
    const val DRIVER_POST_CREATED = "/v1/driver_posts"
    const val DRIVER_HISTORY = "/v1/driver_history/{${RequestParam.ID}}"
    const val DRIVER_REVENUE = "/v1/driver/{${RequestParam.ID}}/statistics"

    //Login Soical
    const val LOGIN_SOCIAL = "/v1/login_social"

    //Term and Condition
    const val TERM_AND_CONDITION = "/v1/page/{${RequestParam.SLUG}}"

    //Version App
    const val VERSION_APP = "/v1/versions"

    //FAQS
    const val SHOW_FAQS = "/v1/faqs"

    //Contact
    const val CONTACT_SYSTEM = "/v1/contact_system"

    interface httpStatusCode {
        companion object {
            const val OK = 200
            const val CREATE = 201
            const val UNAUTHORIZED = 401
        }
    }
}