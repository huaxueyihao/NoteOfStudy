package com.thread.study.chapter08;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class MMSDeliveryServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 将请求中的数据解析为内部对象
        MMSDeliveryRequest mmsDeliveryReq = this.parseRequest(req.getInputStream());
        Recipient shortNumberRecipient = mmsDeliveryReq.getRecipient();
        Recipient orignalNumberRecipient = null;

        try {
            orignalNumberRecipient = convertShortNumber(shortNumberRecipient);
        } catch (Exception e) {
            // 接收方短号转换为长号时发生数据库异常，触发请求消息的缓存
            AsyncRequestPersistence.getInstance().store(mmsDeliveryReq);


            resp.setStatus(202);
        }
        System.out.println(orignalNumberRecipient);

    }

    private Recipient convertShortNumber(Recipient shortNumberRecipient) {
        Recipient recipient = null;
        return recipient;
    }

    private MMSDeliveryRequest parseRequest(ServletInputStream inputStream) {
        MMSDeliveryRequest mmsDeliveryRequest = new MMSDeliveryRequest();

        return mmsDeliveryRequest;
    }
}
