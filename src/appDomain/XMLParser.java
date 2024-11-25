package appDomain;

import implementations.MyStack;

import java.io.*;


public class XMLParser {
    public static void main(String[] args) {
        parseXML(args[0]);
    }

    public static void parseXML(String filePath) {
        try {
            File xmlFile = new File(filePath);
            BufferedReader xmlReader = new BufferedReader(new FileReader(xmlFile));

            String currentLine = xmlReader.readLine();
            int lineNumber = 1;

            // stack to store all the opening tags in a file
            MyStack<String> openTagStack = new MyStack<>();
            MyStack<Integer> tagLineStack = new MyStack<>();

            //tag to store all the errors in the file
            MyStack<String> errorStack = new MyStack<>();
            MyStack<Integer> errorLineStack = new MyStack<>();

            while (currentLine != null) {
                // removing whitespace in line
                currentLine = currentLine.trim();

                if ((currentLine.startsWith("<") && currentLine.endsWith("/>")) ||
                        (currentLine.startsWith("<?") && currentLine.endsWith("?>"))) {
                    currentLine = xmlReader.readLine();
                    lineNumber++;
                    continue;
                }

                if (currentLine.startsWith("<") && currentLine.endsWith(">")) {
                    String[] currentLineArray = currentLine.split("<");

                    for (int i = 0; i < currentLineArray.length; i++) {
                        if (currentLineArray[i].startsWith("/") && !currentLineArray[i].equals("")) {
                            String closeTag = currentLineArray[i].split(">")[0];
                            if (isTagSame(openTagStack.peek(), closeTag)) {
                                openTagStack.pop();
                                tagLineStack.pop();
                            } else {
                                // if close tag does not match the last item in the stack and error has occured
                                errorStack.push(openTagStack.pop());
                                errorLineStack.push(tagLineStack.pop());
                            }
                        } else if (!currentLineArray[i].equals("")) {
                            // getting the tag only without its properties
                            String openTag = currentLineArray[i].split(">")[0].split(" ")[0];
                            openTagStack.push(openTag);
                            tagLineStack.push(lineNumber);
                        }
                    }
                }
                currentLine = xmlReader.readLine();
                lineNumber++;
            }

            int errorSize = errorStack.size();
            if (errorSize > 0) {
                for (int i = 0; i < errorSize; i++) {
                    System.out.println("Error in line " + errorLineStack.pop());
                    System.out.println("<" + errorStack.pop() + ">");
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    // this method compares an opening tag and closing tag
    public static boolean isTagSame(String open, String close) {
        return open.equals(close.split("/")[1]);
    }
}
