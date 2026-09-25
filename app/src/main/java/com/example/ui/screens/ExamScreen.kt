package com.example.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.audio.AudioHelper

/**
 * Official Mid-Year Exam Screen (Pages 78-79)
 * 2ème Préparatoire - Bienvenu 2
 * Note: 20/20 Marks
 */
@Composable
fun ExamScreen(
    audioHelper: AudioHelper,
    onScoreEarned: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.padding(horizontal = 14.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(4.dp))
                OfficialMidYearExamContent(
                    audioHelper = audioHelper,
                    onScoreEarned = onScoreEarned
                )
                Spacer(modifier = Modifier.height(28.dp))
            }
        }
    }
}
