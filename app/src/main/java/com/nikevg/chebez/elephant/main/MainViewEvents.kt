package com.nikevg.chebez.elephant.main

import com.nikevg.chebez.elephant.base.ViewEvent

class RecognitionResultsReadyEvent(val results: String?) : ViewEvent

object CheckMicPermissionEvent : ViewEvent
object MicPermissionDeniedErrorEvent : ViewEvent
object NetworkErrorEvent : ViewEvent
object PurchaseElephantEvent : ViewEvent
object ShowElephantsEvent : ViewEvent
object UnknownErrorEvent : ViewEvent
