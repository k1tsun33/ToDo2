// TaskXmlManager.java
package com.example.todo;

import org.w3c.dom.*;
import org.xml.sax.SAXException;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

class TaskXmlManager {
    private String xmlFilePath;
    private DocumentBuilderFactory docFactory;
    private DocumentBuilder docBuilder;
    private TransformerFactory transformerFactory;

    public TaskXmlManager(String xmlFilePath) {
        this.xmlFilePath = xmlFilePath;
        try {
            docFactory = DocumentBuilderFactory.newInstance();
            docBuilder = docFactory.newDocumentBuilder();
            transformerFactory = TransformerFactory.newInstance();

            // Create initial XML file if it doesn't exist
            File xmlFile = new File(xmlFilePath);
            if (!xmlFile.exists()) {
                createInitialXmlFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createInitialXmlFile() {
        try {
            Document doc = docBuilder.newDocument();

            // Root element - tasks
            Element rootElement = doc.createElement("tasks");
            doc.appendChild(rootElement);

            // Save the XML file
            saveXmlDocument(doc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveXmlDocument(Document doc) throws TransformerException {
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(xmlFilePath));
        transformer.transform(source, result);
    }

    public List<Task> loadTasks() {
        List<Task> tasks = new ArrayList<>();

        try {
            File xmlFile = new File(xmlFilePath);
            Document doc = docBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            NodeList taskNodes = doc.getElementsByTagName("task");

            for (int i = 0; i < taskNodes.getLength(); i++) {
                Node taskNode = taskNodes.item(i);

                if (taskNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element taskElement = (Element) taskNode;

                    String text = taskElement.getElementsByTagName("text").item(0).getTextContent();
                    boolean completed = Boolean.parseBoolean(
                            taskElement.getElementsByTagName("completed").item(0).getTextContent()
                    );

                    tasks.add(new Task(text, completed));
                }
            }
        } catch (IOException | SAXException e) {
            e.printStackTrace();
        }

        return tasks;
    }

    public void saveTasks(List<Task> tasks) {
        try {
            Document doc = docBuilder.newDocument();

            // Root element - tasks
            Element rootElement = doc.createElement("tasks");
            doc.appendChild(rootElement);

            // Add task elements
            for (Task task : tasks) {
                Element taskElement = doc.createElement("task");
                rootElement.appendChild(taskElement);

                // Task text element
                Element textElement = doc.createElement("text");
                textElement.setTextContent(task.getText());
                taskElement.appendChild(textElement);

                // Task completed element
                Element completedElement = doc.createElement("completed");
                completedElement.setTextContent(String.valueOf(task.isCompleted()));
                taskElement.appendChild(completedElement);
            }

            // Save the XML file
            saveXmlDocument(doc);
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
}