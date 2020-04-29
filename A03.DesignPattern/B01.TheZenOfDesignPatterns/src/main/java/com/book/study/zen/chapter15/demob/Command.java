package com.book.study.zen.chapter15.demob;

import com.book.study.zen.chapter15.demoa.CodeGroup;
import com.book.study.zen.chapter15.demoa.PageGroup;
import com.book.study.zen.chapter15.demoa.RequirementGroup;

public abstract class Command {

    protected RequirementGroup rg = new RequirementGroup();
    protected PageGroup pg = new PageGroup();
    protected CodeGroup cg = new CodeGroup();

    // 只有一个方法，你要我做什么事情
    public abstract void execute();

}
