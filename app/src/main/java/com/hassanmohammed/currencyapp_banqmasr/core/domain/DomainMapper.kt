package com.hassanmohammed.currencyapp_banqmasr.core.domain

interface DomainMapper<DomainModel> {
    fun toDomain(): DomainModel
}
