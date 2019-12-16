package com.yourssu.notissu.data

import com.yourssu.notissu.model.Major

class MajorData {
    private val majors = ArrayList<Major>()
    init {
        //it
        majors.add(Major("컴퓨터학부", "Computer Science & Engineering"))
        majors.add(Major("글로벌미디어학부", "The Global School of Media"))
        majors.add(Major("전자정보공학부", "School of Electronic Engineering"))
        majors.add(Major("소프트웨어학부", "School of Software"))
        majors.add(Major("스마트시스템소프트웨어학과", "Smart Systems Software"))
        //law
        majors.add(Major("법학과", "Law"))
        majors.add(Major("국제법무학과", "Global Law"))
        //human
        majors.add(Major("국어국문학과", "Korean Language & Literature"))
        majors.add(Major("불어불문학과", "French Language & Literature"))
        majors.add(Major("독어독문학과", "German Language & Literature"))
        majors.add(Major("중어중문학과", "Chinese Language & Literature"))
        majors.add(Major("영어영문학과", "English Language & Literature"))
        majors.add(Major("사학과", "Department of History"))
        majors.add(Major("철학과", "Department of Philosophy"))
        majors.add(Major("일어일문학과", "Japanese Language & Literature"))
        //engineer
        majors.add(Major("화학공학과", "Chemical Engineering"))
        majors.add(Major("기계공학과", "School of Mechanical Engineering"))
        majors.add(Major("전기공학과", "School of Electrical Engineering"))
        majors.add(Major("산업정보시스템공학과", "Industrial & Information Systems"))
        majors.add(Major("유기신소재 · 파이버공학과", "Organic Materials & Fiber"))
        //science
        majors.add(Major("수학과", "College of Mathematics"))
        majors.add(Major("물리학과", "Physics"))
        majors.add(Major("화학과", "Department of Chemistry"))
        majors.add(Major("정보통계보험수리학과", "Statistics and Actuarial Science"))
        majors.add(Major("의생명시스템학부", "School of Systems Biomedical Science"))
        //biz
        majors.add(Major("경영학부", "School of Business Administration"))
        majors.add(Major("벤처중소기업학과", "Entrepreneurship and Small Business"))
        majors.add(Major("회계학과", "Department of Accounting"))
        majors.add(Major("금융학부", "School of Finance"))
        //eco
        majors.add(Major("경제학과", "Economics"))
        majors.add(Major("글로벌통상학과", "Global Commerce"))
        //social
        majors.add(Major("사회복지학부", "School of Social Welfare"))
        majors.add(Major("행정학부", "School of Public Administration"))
        majors.add(Major("정보사회학과", "Information Sociology"))
        majors.add(Major("언론홍보학과", "Journalism, Public Relations, Advertising"))
        majors.add(Major("평생교육학과", "LifeLong Education"))
        majors.add(Major("정치외교학과", "Politics & International Relation"))
        //convergence
        majors.add(Major("융합특성화자유전공학부", "School of Convergence Specialization"))
    }
    companion object {
        @JvmStatic
        fun getInstance() = MajorData()
    }

    fun getMajors(): ArrayList<Major> {
        return majors
    }

    fun getMajorByIndex(index: Int): Major {
        return majors[index]
    }


    //get specific majors

    private fun getSpecificMajors(start: Int, end: Int): ArrayList<Major> {
        val result = ArrayList<Major>()
        for (index in start..end)
            result.add(majors[index])
        return result
    }

    fun getItMajors(): ArrayList<Major> { return getSpecificMajors(0, 4) }

    fun getLawMajors(): ArrayList<Major> { return getSpecificMajors(5, 6) }

    fun getHumanMajors(): ArrayList<Major> { return getSpecificMajors(7, 14) }

    fun getEngineerMajors(): ArrayList<Major> { return getSpecificMajors(15, 19) }

    fun getScienceMajors(): ArrayList<Major> { return getSpecificMajors(20, 24) }

    fun getBizMajors(): ArrayList<Major> { return getSpecificMajors(25, 28) }

    fun getEcoMajors(): ArrayList<Major> { return getSpecificMajors(29, 30) }

    fun getSocialMajors(): ArrayList<Major> { return getSpecificMajors(31, 36) }

    fun getConvergenceMajors(): ArrayList<Major> { return getSpecificMajors(37, 37) }
}