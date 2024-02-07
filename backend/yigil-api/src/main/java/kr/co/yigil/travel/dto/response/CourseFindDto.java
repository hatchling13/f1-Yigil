package kr.co.yigil.travel.dto.response;

import kr.co.yigil.travel.Course;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseFindDto {
    private Long courseId;
    private String title;
    private String memberNickname;
    private String memberImageUrl;
    private int favorCount;
    private int commentCount;

    public static CourseFindDto from(Course course, Integer favorCount, Integer commentCount) {
        return new CourseFindDto(
                course.getId(),
                course.getTitle(),
                course.getMember().getNickname(),
                course.getMember().getProfileImageUrl(),
                favorCount,
                commentCount
        );
    }


}
