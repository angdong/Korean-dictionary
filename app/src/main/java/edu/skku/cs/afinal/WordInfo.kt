package edu.skku.cs.afinal

data class WordInfo(
    val pos_info: List<PosInfo>,
    val pronunciation_info: List<PronunciationInfo>,
    val relation_info: List<RelationInfo>,
    val word: String,
    val word_type: String,
    val word_unit: String
)