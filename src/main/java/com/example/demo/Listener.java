package com.example.demo;

import com.example.demo.Models.ReportContent;
import com.example.demo.Models.ReportData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.activemq.Message;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.TextMessage;
import java.io.*;
import java.util.List;

@Component
public class Listener {

    ReportData reportData = null;
    ReportContent reportContent = null;
    ObjectMapper mapper = new ObjectMapper();
    StringBuilder sb = new StringBuilder();

    @JmsListener(destination = "jsonQueue")
    @SendTo("formatedFile")
    public String receiveMessage(final Message jsonMessage) throws JMSException {
        String messageData = null;
        System.out.println("Received message " + jsonMessage);
        String response = null;
        System.out.println(jsonMessage.getClass().toString() + "Type of the object");
        if(jsonMessage instanceof TextMessage) {
            TextMessage textMessage = (TextMessage)jsonMessage;
            messageData = textMessage.getText();

            response  = messageData;

            //JSON from String to Object
            try {
                reportData = mapper.readValue(response, ReportData.class);
            } catch (IOException e) {
                e.printStackTrace();
            }

            List<ReportContent> listOfReports = reportData.getContent();

            sb.append(reportData.getEmail());
            sb.append("\n");
            sb.append("Attendance statistics");
            for (ReportContent item : listOfReports )
            {
                sb.append("\n");
                sb.append("-----------------------------------------------------------------------------");
                sb.append("\n");
                sb.append(item.getStudentName());
                sb.append("\n");
                sb.append(item.getStudentSurname());
                sb.append("\n");
                sb.append(item.getCourseName());
                sb.append("\n");
                sb.append(item.getAttendancePercentage());
                sb.append("\n");
                sb.append("-----------------------------------------------------------------------------");
            }
        }
        return String.valueOf(sb);
    }
}
