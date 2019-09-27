package com.example.anothertimdatxe.util

object Constant {
    //DRIVER_POST
    /*
    ['Đang chờ phê duyệt', 'Công khai','Chuyến đi đã kết thúc', 'Hủy bỏ']
     */
    const val DRIVER_POST_PENDING = 0
    const val DRIVER_POST_PUBLISHED = 1
    const val DRIVER_POST_FINISH = 2
    const val DRIVER_POST_CANCEL = 3

    //USER_POST
    /*
    ['Đang chờ phê duyệt', 'Công khai','Đã thỏa thuận với tài xế','Kết thúc','Hủy bỏ']
     */
    const val USER_POST_PENDING = 0
    const val USER_POST_PUBLISHED = 1
    const val USER_POST_DONE = 2
    const val USER_POST_FINISH = 3
    const val USER_POST_CANCEL = 4

    //USER_BOOK
    /*
     ['Đặt thất bại','Đặt thành công','Kết thúc','Hủy chuyến']
     */
    const val USER_BOOK_PENDING = 0
    const val USER_BOOK_DONE = 1
    const val USER_BOOK_FINISH = 2
    const val USER_BOOK_CANCEL = 3

    //DRIVER_BOOK_OPTION
    /*
    ['Đang chờ', 'Được chấp nhận', 'Bị từ chối','Chuyến đi đã kết thúc','Hủy bỏ' ]
     */
    const val DRIVER_BOOK_PENDING = 0
    const val DRIVER_BOOK_ACCEPTED = 1
    const val DRIVER_BOOK_REJECTED = 2
    const val DRIVER_BOOK_FINISH = 3
    const val DRIVER_BOOK_CANCEL = 4

    const val CONVENIENT_TRIP = 0
    const val PRIVATE_TRIP = 1
    const val BOTH_CONVENIENT_AND_PRIVATE = 2

    /**
     * DRIVER POST REASON CANCEL
     */
    const val DRIVER_CANCEL_POST = 0
    const val DRIVER_CANCEL_BOOKING = 1
    const val POST_EXPIRE = 2
}