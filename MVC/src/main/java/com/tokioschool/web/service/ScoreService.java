package com.tokioschool.web.service;

import java.util.Optional;

import com.tokioschool.web.domain.Score;

public interface ScoreService {
	Optional<Score> findByUserIdAndFilmId(Long userId, Long filmId);
	Score save(Score score);
}
