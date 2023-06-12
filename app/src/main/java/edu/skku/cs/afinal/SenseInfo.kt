package edu.skku.cs.afinal

data class SenseInfo(
    val definition: String,
    val definition_original: String,
    val lexical_info: List<LexicalInfo>,
    val sense_code: Int,
    val type: String
)