package com.yourssu.notissu.data

import com.yourssu.notissu.model.Major
import java.util.*
import kotlin.collections.ArrayList

class MajorData {

    companion object {
        val majors = ArrayList<Major>(
            listOf(
                //it
                Major(0, "컴퓨터학부", "Computer Science & Engineering"),
                Major(1, "글로벌미디어학부", "The Global School of Media"),
                Major(2, "전자정보공학부", "School of Electronic Engineering"),
                Major(3, "소프트웨어학부", "School of Software"),
                Major(4, "스마트시스템소프트웨어학과", "Smart Systems Software"),
                //law
                Major(5, "법학과", "Law"),
                Major(6, "국제법무학과", "Global Law"),
                //human
                Major(7, "국어국문학과", "Korean Language & Literature"),
                Major(8, "불어불문학과", "French Language & Literature"),
                Major(9, "독어독문학과", "German Language & Literature"),
                Major(10, "중어중문학과", "Chinese Language & Literature"),
                Major(11, "영어영문학과", "English Language & Literature"),
                Major(12, "사학과", "Department of History"),
                Major(13, "철학과", "Department of Philosophy"),
                Major(14, "일어일문학과", "Japanese Language & Literature"),
                //engineer
                Major(15, "화학공학과", "Chemical Engineering"),
                Major(16, "기계공학과", "School of Mechanical Engineering"),
                Major(17, "전기공학과", "School of Electrical Engineering"),
                Major(18, "산업정보시스템공학과", "Industrial & Information Systems"),
                Major(19, "유기신소재 · 파이버공학과", "Organic Materials & Fiber"),
                //science
                Major(20, "수학과", "College of Mathematics"),
                Major(21, "물리학과", "Physics"),
                Major(22, "화학과", "Department of Chemistry"),
                Major(23, "정보통계보험수리학과", "Statistics and Actuarial Science"),
                Major(24, "의생명시스템학부", "School of Systems Biomedical Science"),
                //biz
                Major(25, "경영학부", "School of Business Administration"),
                Major(26, "벤처중소기업학과", "Entrepreneurship and Small Business"),
                Major(27, "회계학과", "Department of Accounting"),
                Major(28, "금융학부", "School of Finance"),
                //eco
                Major(29, "경제학과", "Economics"),
                Major(30, "글로벌통상학과", "Global Commerce"),
                //social
                Major(31, "사회복지학부", "School of Social Welfare"),
                Major(32, "행정학부", "School of Public Administration"),
                Major(33, "정보사회학과", "Information Sociology"),
                Major(34, "언론홍보학과", "Journalism, Public Relations, Advertising"),
                Major(35, "평생교육학과", "LifeLong Education"),
                Major(36, "정치외교학과", "Politics & International Relation"),
                //convergence
                Major(37, "융합특성화자유전공학부", "School of Convergence Specialization"),
                //architecture add
                Major(38, "건축학부", "School of Architecture"),
                //writing add
                Major(39, "문예창작전공", "Creative Writing")
            )
        )

        @JvmStatic
        fun getInstance() = MajorData()
    }

    fun getMajors(): ArrayList<Major> {
        return majors
    }

    fun getMajorByIndex(index: Int): Major {
        return majors[index]
    }


    //get each college

    private fun getCollegeMajors(start: Int, end: Int): ArrayList<Major> {
        val result = ArrayList<Major>()
        for (index in start..end)
            result.add(majors[index])
        return result
    }

    fun getItMajors(): ArrayList<Major> { return getCollegeMajors(0, 4) }

    fun getLawMajors(): ArrayList<Major> { return getCollegeMajors(5, 6) }

    fun getHumanMajors(): ArrayList<Major> {
        val sum = getCollegeMajors(7, 14)
        sum.add(majors[39])
        return sum
    }

    fun getEngineerMajors(): ArrayList<Major> {
        val sum = getCollegeMajors(15, 19)
        sum.add(majors[38])
        return sum }

    fun getScienceMajors(): ArrayList<Major> { return getCollegeMajors(20, 24) }

    fun getBizMajors(): ArrayList<Major> { return getCollegeMajors(25, 28) }

    fun getEcoMajors(): ArrayList<Major> { return getCollegeMajors(29, 30) }

    fun getSocialMajors(): ArrayList<Major> { return getCollegeMajors(31, 36) }

    fun getConvergenceMajors(): ArrayList<Major> { return getCollegeMajors(37, 37) }
}