package edu.skku.cs.afinal

data class Channel(
    val description: String,
    val item: List<Item>,
    val lastbuilddate: String,
    val link: String,
    val num: Int,
    val start: Int,
    val title: String,
    val total: Int
)