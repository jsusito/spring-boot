package com.tokioschool.web.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tokioschool.web.domain.Score;

@Repository
public interface ScoreRepository extends CrudRepository<Score, Long> {
	Optional<Score> findByUserIdAndFilmId(Long userId, Long filmId);
}
