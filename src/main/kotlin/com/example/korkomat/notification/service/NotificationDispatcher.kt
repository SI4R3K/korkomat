package com.example.korkomat.notification.service

import com.example.korkomat.notification.component.NotificationHandler
import com.example.korkomat.notification.entity.Notification
import org.springframework.stereotype.Service

@Service
class NotificationDispatcher(
    handlers: List<NotificationHandler>
) {

//    init {
//        println("=== REGISTERED NOTIFICATION HANDLERS ===")
//
//        handlers.forEach {
//            println("Handler: ${it::class.simpleName}, type: ${it.type}")
//        }
//
//        println("========================================")
//    }


    private val handlersByType = handlers.associateBy {
        it.type
    }

    fun send(notification: Notification) {
        val handler = handlersByType[notification.type]
            ?: throw IllegalStateException(
                "No handler found for ${notification.type}"
            )

        handler.send(notification)
    }

}