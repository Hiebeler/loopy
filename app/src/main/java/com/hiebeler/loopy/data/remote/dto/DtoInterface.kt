package com.hiebeler.loopy.data.remote.dto

interface DtoInterface<T> {
    fun toModel(): T
}