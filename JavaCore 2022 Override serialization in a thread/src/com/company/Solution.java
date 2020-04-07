package com.company;

/*

2022 Override serialization in a thread
Serialization / Deserialization Solution does not work.
Fix bugs without changing method and class signatures.
The main method does not participate in testing.
Write the verification code yourself in the main method:
1) create an instance of the Solution class
2) write in it data - writeObject
3) serialize the Solution class - writeObject (ObjectOutputStream out)
4) Deserialize, we get a new object
5) write in the new object data - writeObject
6) check that the file contains the data from item 2 and item 5

Requirements:
1. The stream field must be declared with the transient modifier.
2. In the writeObject (ObjectOutputStream out) method, the close method of the stream received as a parameter should not be called.
3. The readObject (ObjectInputStream in) method should not call the close method on the stream received as a parameter.
4. In the readObject (ObjectInputStream in) method, the stream field must be initialized with a new FileOutputStream object with parameters (fileName, true).
5. In the Solution class constructor, the stream field must be initialized with a new FileOutputStream object with the (fileName) parameter.


 */

import java.io.*;
import java.util.HashMap;
import java.util.Map;


public class Solution implements Serializable, AutoCloseable {
    transient private FileOutputStream stream;
    private String fileName;

    public Solution(String fileName) throws FileNotFoundException {
        this.stream = new FileOutputStream(fileName);
        this.fileName = fileName;
    }

    public void writeObject(String string) throws IOException {
        stream.write(string.getBytes());
        stream.write("\n".getBytes());
        stream.flush();
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        in.close();
    }

    @Override
    public void close() throws Exception {
        System.out.println("Closing everything!");
        stream.close();
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Solution solution = new Solution("e:\\shalom.txt");
        solution.writeObject("Shalom!\r\n");
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("e:\\solution.obj"));
        oos.writeObject(solution);

        ObjectInputStream ois = new ObjectInputStream((new FileInputStream(("e:\\solution.obj"))));
        Solution solution2 = (Solution) ois.readObject();
        solution2.writeObject("Shalom again");

    }
}

