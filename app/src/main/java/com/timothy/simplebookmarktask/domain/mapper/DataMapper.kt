package com.timothy.simplebookmarktask.domain.mapper

interface DataMapper <T, DataModel> {
    fun mapDataModel(dataModel: DataModel): T
}