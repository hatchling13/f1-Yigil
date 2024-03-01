import MyPageCourseList from '@/app/_components/mypage/course/MyPageCourseList';
import { getMyPageCourses } from '@/app/_components/mypage/hooks/myPageActions';
import { myPageCourseListSchema } from '@/types/myPageResponse';
import React from 'react';

export default async function MyPageMyCourse() {
  const courseList = await getMyPageCourses();
  const parsedCourseList = myPageCourseListSchema.safeParse(courseList.content);
  if (!parsedCourseList.success) return <div>failed</div>;
  return (
    <>
      <MyPageCourseList
        placeList={courseList.content}
        totalPage={courseList.total_pages}
      />
    </>
  );
}