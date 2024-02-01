package kr.co.yigil.travel.repository;

import java.util.List;
import java.util.Optional;
import kr.co.yigil.travel.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CourseRepository extends JpaRepository<Course, Long> {

    void deleteByIdAndMemberId(Long courseId, Long memberId);

    Optional<Course> findByIdAndMemberId(Long courseId, Long memberId);

    @Query("SELECT c FROM Course c JOIN c.spots s WHERE s.place.id = :placeId")
    List<Course> findBySpotPlaceId(@Param("placeId") Long placeId);}