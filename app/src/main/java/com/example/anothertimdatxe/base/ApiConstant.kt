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
    const val USER_POST_DETAIL = "/v1/user_posts/{${RequestParam.ID}}"
    const val USER_HISTORY = "/v1/user_history/{${RequestParam.ID}}"
    const val USER_SEARCH_CITY_POST = "/v1/searches/city_user_post"
    const val USER_REFRESH_TOKEN = "/v1/user_refresh"

    //[DRIVER]
    const val DRIVER_SESSIONS = "/v1/driver_sessions"
    const val DRIVER_REGISTRATIONS = "/v1/driver_registrations"
    const val DRIVER_ACTIVATIONS = "/v1/driver_activations"
    const val DRIVER_INFO = "/v1/drivers/{${RequestParam.ID}}"
    const val DRIVER_PASSWORDS = "/v1/driver_passwords"
    const val DRIVER_POST_CREATED = "/v1/driver_posts"
    const val DRIVER_POST_DETAIL = "/v1/driver_posts/{${RequestParam.ID}}"
    const val DRIVER_HISTORY = "/v1/driver_history/{${RequestParam.ID}}"
    const val DRIVER_REVENUE = "/v1/driver/{${RequestParam.ID}}/statistics"
    const val DRIVER_SEARCH_CITY_POST = "/v1/searches/city_driver_post"
    const val DRIVER_REFRESH_TOEKN = "/v1/driver_refresh"
    const val DRIVER_CANCEL_REQUEST = "/v1/driver_book_options/{${RequestParam.ID}}"
    const val DRIVER_CANCEL_DRIVER_BOOKING = "/v1/driver_cancel_driver_book"
    const val DRIVER_FINISH_TRIP = "/v1/finish_driver_book"

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

    //Hot Cities
    const val HOT_CITIES = "/v1/cities"

    interface httpStatusCode {
        companion object {
            const val OK = 200
            const val CREATE = 201
            const val UNAUTHORIZED = 401
            const val INTERNAL_ERROR_SERVER = 500
        }
    }
}