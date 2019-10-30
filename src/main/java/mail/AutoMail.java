package mail;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Properties;

import javax.mail.internet.AddressException;

import base.ReadProperties;
import mail.ReadHtml;

public class AutoMail {
    public static void main(String[] args) throws AddressException, FileNotFoundException, IOException {
        MailInfo mailInfo = new MailInfo();
        // 读取收件人和抄送人的邮箱
        String sendTo[] = readMail("sendto");
        String cclist[] = readMail("cc");
        String attachfiles[] = readMail("attachfiles");

        // 通过ReadProperties读取发件人的邮箱配置信息，再逐个set进mailInfo中，以便后期发送
        mailInfo.setMailServerHost(ReadProperties.getprop("mail", "MailServerHost"));
        mailInfo.setMailServerPort(ReadProperties.getprop("mail", "MailServerPort"));
        mailInfo.setValidate(true);
        mailInfo.setUsername(ReadProperties.getprop("mail", "Username"));
        mailInfo.setPassword(ReadProperties.getprop("mail", "Password"));// 您的邮箱密码或授权码
        mailInfo.setFromAddress(ReadProperties.getprop("mail", "FromAddress"));
        mailInfo.setToAddress(sendTo);
        mailInfo.setSubject(ReadProperties.getprop("mail", "Subject"));
        mailInfo.setCcAddress(cclist);
        mailInfo.setAttachFileNames(attachfiles);

        // 读取ReportNG的测试报告overview.html
        String mailcontent = ReadHtml.readString("./target/surefire-reports/html/overview.html");
        // 读取overview.html里外部引用css文件
        String cssvalue = ReadHtml.readString("./target/surefire-reports/html/reportng.css");
        // 将读取后的css拼接成<style>，作为新html里文档定义样式
        String changestr = "<style type=\"text/css\">h1 {display : inline}" + cssvalue + "</style>";
        // 把原文link引用的css替换掉
        mailcontent = mailcontent.replace("<link href=\"reportng.css\" rel=\"stylesheet\" type=\"text/css\" />", changestr);
        // 替换原文的标题
        String logostr = "<h1 style=\"color:red ; font-size:50px;font-family: '楷体','楷体_GB2312';\">MAVEN_DEMO</h1><h1>自动化测试报告</h1>";
        mailcontent = mailcontent.replace("<h1>Test Results Report</h1>", logostr);
        // 把所有的mailInfo的set进新html里邮件内容content
        mailInfo.setContent(mailcontent);
        // 发送html格式邮件
        SendMail.sendHtmlMail(mailInfo);

    }

    public static String[] readMail(String mailtype) throws FileNotFoundException, IOException {

        int i = 0;
        Properties props = new Properties();
        // 加载properties的配置内容
        props.load(new FileInputStream("./target/classes/properties/" + mailtype + ".properties"));
        // 根据property字段的size大小，定义一个数组mailto
        String[] mailto = new String[props.size()];
        // 遍历props里的所有元素
        for (Enumeration enu = props.elements(); enu.hasMoreElements(); ) {
            // 取下一个元素的value值
            String value = (String) enu.nextElement();
            mailto[i] = value;
            i++;
        }
        return mailto;
    }

}
