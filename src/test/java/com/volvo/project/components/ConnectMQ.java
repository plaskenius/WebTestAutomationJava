package com.project.project.components;

import java.io.*;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import com.ibm.jms.JMSMessage;
import com.ibm.mq.jms.*;
import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import com.project.project.enums.HostName;
import com.project.project.enums.ManagerName;
import com.project.project.enums.QueueName;
import org.apache.commons.io.IOUtils;

import com.ibm.msg.client.wmq.WMQConstants;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class ConnectMQ extends TestBase {

    public static void sendMQ(ManagerName managerName, QueueName queueName, String xmlPath) {
        try {
            MQQueueConnectionFactory cf = new MQQueueConnectionFactory();
            String hostName = "";
            if (managerName == ManagerName.GimliA1) {
                hostName = HostName.GimliA1.toString();
                //port
                //channel
            }
            cf.setHostName(hostName + ".it.project.net");
            cf.setPort(1417);//port
            cf.setQueueManager(managerName.toString());
//            cf.setHostName("eowyn-b3.it.project.net");
//            cf.setPort(1419);
//            cf.setQueueManager("EOWYN_B3");
            cf.setChannel("R006.SRV01");//channel
            cf.setIntProperty(WMQConstants.WMQ_CONNECTION_MODE, WMQConstants.WMQ_CM_CLIENT);

            // MQQueueConnection connection = (MQQueueConnection) cf.createQueueConnection();
            MQQueueConnection connection = (MQQueueConnection) cf.createQueueConnection("cs-ws-s-system-jms", "e7H9-Kf9a8L-g87");

            MQQueueSession session = (MQQueueSession) connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            MQQueue queue = (MQQueue) session.createQueue("queue:///" + queueName.toString());

            MQQueueSender sender = (MQQueueSender) session.createSender(queue);

            long uniqueNumber = System.currentTimeMillis() % 1000;

//            ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
//            InputStream resourceAsStream = contextClassLoader.getResourceAsStream(xmlPath);
            String xmlPath2 = new java.io.File("").getAbsolutePath() + "/src/test/resources/" + xmlPath;
            InputStream resourceAsStream = new FileInputStream(xmlPath2);
            String textMessage = new String(IOUtils.toByteArray(resourceAsStream));

            TextMessage message = session.createTextMessage(textMessage);
            MQDestination mqDestination = new MQDestination("queue:///"+queueName.toString());

            message.setJMSReplyTo(mqDestination);
            message.setJMSCorrelationID(String.valueOf(uniqueNumber));

            // Start the connection
            connection.start();
            sender.send(message);

            logger.info("Message sent to " + queue.getQueueName());
//            logger.info(", message:  " + message.getText());
            sender.close();
            session.close();
            connection.close();
            logger.info("Message Sent OK.\n");
        } catch (JMSException jmsex) {
            logger.warn("Message Send Failure\n");
        } catch (Exception ex) {
            logger.warn("Message Send Failure\n");
        }
    }

    public static void receiveMQ(ManagerName managerName, QueueName queueName) {
        try {
            MQQueueConnectionFactory cf = new MQQueueConnectionFactory();
            String hostName = "";
            if (managerName == ManagerName.GimliA1) {
                hostName = HostName.GimliA1.toString();
            }
            cf.setHostName(hostName + ".it.project.net");
            cf.setPort(1417);
            cf.setQueueManager(managerName.toString());
//            cf.setHostName("eowyn-b3.it.project.net");
//            cf.setPort(1419);
//            cf.setQueueManager("EOWYN_B3");
            cf.setChannel("R006.SRV01");
            cf.setIntProperty(WMQConstants.WMQ_CONNECTION_MODE, WMQConstants.WMQ_CM_CLIENT);

            // MQQueueConnection connection = (MQQueueConnection) cf.createQueueConnection();
            MQQueueConnection connection = (MQQueueConnection) cf.createQueueConnection("cs-ws-s-system-jms", "e7H9-Kf9a8L-g87");

            MQQueueSession session = (MQQueueSession) connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            MQQueue queue = (MQQueue) session.createQueue("queue:///" + queueName.toString());

            MQQueueReceiver receiver = (MQQueueReceiver) session.createReceiver(queue);

            connection.start();

            JMSMessage receivedMessage = (JMSMessage) receiver.receive(2000);
            logger.info("\nReceived message: " + receivedMessage);
//            String textMessage = ((TextMessage) receivedMessage).getText();
//            logger.info("\nMessage Body: \n" + format(textMessage));

            receiver.close();
            session.close();
            connection.close();
        } catch (JMSException jmsex) {
            logger.warn("Message Receive Failure\n");
        } catch (Exception ex) {
            logger.warn("Message Receive Failure\n");
        }

    }

    private static Document parseXmlFile(String in) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(in));
            return db.parse(is);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String format(String unformattedXml) {
        try {
            final Document document = parseXmlFile(unformattedXml);

            OutputFormat format = new OutputFormat(document);
            format.setLineWidth(65);
            format.setIndenting(true);
            format.setIndent(2);
            Writer out = new StringWriter();
            XMLSerializer serializer = new XMLSerializer(out, format);
            serializer.serialize(document);

            return out.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}



