package com.answer01;

/**
 * �ṩ��ѧ������������Ϊ����
 */
public class StuManager {

    Student[] studentArray = new Student[100];

    /**
     * ���ѧ��
     *
     * @param student ѧ������
     */
    public void add(Student student) {
        for (int i = 0; i < studentArray.length; i++) {
            if (studentArray[i] == null) {
                studentArray[i] = student;
                break;
            }
        }
    }

    /**
     * ����ѧ������ɾ��ѧ��
     *
     * @param name ѧ������
     */
    public Student remove(String name) {
        Student temp = null;
        for (int i = 0; i < studentArray.length; i++) {
            if (studentArray[i] != null && studentArray[i].getName().equals(name)) {
                temp = studentArray[i];
                studentArray[i] = null;
                break;
            }
        }
        return temp;
    }


    /**
     * ����ѧ����������ѧ��
     *
     * @param name ѧ������
     */
    public Student getbyName(String name) {
        for (int i = 0; i < studentArray.length; i++) {
            if (studentArray[i] != null && studentArray[i].getName().equals(name)) {
                return studentArray[i];
            }
        }
        return null;
    }

    //	չʾ����ѧ��
    public void showAll() {
        for (int i = 0; i < studentArray.length; i++) {
            Student student = studentArray[i];
            if (student != null) {
                System.out.println("ѧ�ţ�" + student.getId() + "������" + student.getName() + "���䣺" + student.getAge() + "�Ա�" + student.getSex());
            }
        }
    }


}
