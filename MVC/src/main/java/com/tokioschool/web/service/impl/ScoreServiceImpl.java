package com.tokioschool.web.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tokioschool.web.domain.Score;
import com.tokioschool.web.repository.ScoreRepository;
import com.tokioschool.web.service.ScoreService;

@Service
public class ScoreServiceImpl implements ScoreService {

	@Autowired
	ScoreRepository scoreRepository;
	@Override
	public Optional<Score> findByUserIdAndFilmId(Long userId, Long filmId) {

		return scoreRepository.findByUserIdAndFilmId(userId, filmId);
	}
	@Override
	public Score save(Score score) {
		return scoreRepository.save(score);
	}

}
