package com.book.study.zen.chapter08;

public class HumanFactory extends AbstractHumanFactory {


    @Override
    public <T extends Human> T createHuman(Class<T> c) {
        Human human = null;
        try {
            // 产生一个人种
            human = (T) Class.forName(c.getName()).newInstance();
        }catch (Exception e){
            System.out.println("人种生成错误");
        }
        return (T) human;
    }
}
