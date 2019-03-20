package com.grpc.project.model.clone;

import org.springframework.beans.BeanUtils;

import java.io.*;

/**
 * @author liuzheng
 * 2018/11/19 8:56
 */
public class Product implements Cloneable {

    private String name;



    private Attribute attribute;



    public Object clone() {

        ByteArrayOutputStream byteOut = null;

        ObjectOutputStream objOut = null;

        ByteArrayInputStream byteIn = null;

        ObjectInputStream objIn = null;



        try {

            byteOut = new ByteArrayOutputStream();

            objOut = new ObjectOutputStream(byteOut);

            objOut.writeObject(name);



            byteIn = new ByteArrayInputStream(byteOut.toByteArray());

            objIn = new ObjectInputStream(byteIn);



            return objIn.readObject();

        } catch (IOException e) {

            throw new RuntimeException("Clone Object failed in IO.",e);

        } catch (ClassNotFoundException e) {

            throw new RuntimeException("Class not found.",e);

        } finally{

            try{

                byteIn = null;

                byteOut = null;

                if(objOut != null) objOut.close();

                if(objIn != null) objIn.close();

            }catch(IOException e){

            }

        }

    }

    public static void main(String[] args) {

    }
}
