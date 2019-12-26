package com.book.study.zen.chapter17;

public class Father {


    public static void main(String[] args) {

        SchoolReport sr;
        sr = new FouthGradeSchoolReport();

        sr = new HighScoreDecorator(sr);

        sr = new SortDecorator(sr);

        sr.report();

        sr.sign("呵护");



    }

}
