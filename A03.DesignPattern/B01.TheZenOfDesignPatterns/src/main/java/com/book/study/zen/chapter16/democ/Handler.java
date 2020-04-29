package com.book.study.zen.chapter16.democ;

public abstract class Handler {

    private Handler nextHandler;

    // 每个处理者都必须对请求作出处理
    public final Response handleMessage(Request request) {
        Response response = null;
        // 判断是否是自己的处理级别
        if (this.getHandlerLevel().equals(request.getRequestLevel())) {
            response = this.echo(request);

        } else { // 不属于自己的处理级别
            // 判断是否有下一个处理者
            if (this.nextHandler != null) {
                response = this.nextHandler.handleMessage(request);
            } else {
                // 没有适当的处理者，业务自行处理

            }
        }
        return response;
    }

    public void setNext(Handler handler) {
        this.nextHandler = handler;
    }


    protected abstract Response echo(Request request);

    protected abstract Level getHandlerLevel();


}
