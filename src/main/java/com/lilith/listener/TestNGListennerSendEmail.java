package com.lilith.listener;


import com.lilith.util.PropertiesUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.testng.IExecutionListener;

import java.io.File;

@Slf4j
public class TestNGListennerSendEmail extends AshfordListener implements IExecutionListener {

    public void onExecutionStart() {

        log.info("Ashford----------所有Case开始执行");
    }


    public void onExecutionFinish() {

        log.info("Ashford----------生成测试报告");

        // 等待测试报告生成
        try {
            Thread.sleep(10000);
        } catch (Exception e){
            e.printStackTrace();
        }
        HtmlEmail mail = new HtmlEmail();
        mail.setHostName(PropertiesUtil.getProperty("email.host.name"));
        mail.setAuthentication(PropertiesUtil.getProperty("email.auth.name"), PropertiesUtil.getProperty("email.auth.code"));
        try {
            mail.setFrom(PropertiesUtil.getProperty("email.from"));
            // 添加多个收件人
            mail.addTo(PropertiesUtil.getProperty("email.to.first"));
            mail.addTo(PropertiesUtil.getProperty("email.to.second"));
            mail.setSubject(reportdate + PropertiesUtil.getProperty("email.subject"));
            mail.setCharset("UTF-8");
            mail.setHtmlMsg(PropertiesUtil.getProperty("email.msg"));
            EmailAttachment emailattachment = new EmailAttachment();
            emailattachment.setPath(System.getProperty("user.dir")+ "/test-report" + File.separator + reportdate+"-report.html");
            emailattachment.setName(PropertiesUtil.getProperty("email.attachment.name"));
            emailattachment.setDescription(EmailAttachment.ATTACHMENT);
            mail.attach(emailattachment);
            mail.send();
        } catch (EmailException e) {
            e.printStackTrace();
        }
    }

}
