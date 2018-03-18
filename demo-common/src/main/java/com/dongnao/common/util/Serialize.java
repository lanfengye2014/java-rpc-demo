package com.dongnao.common.util;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by Lanfengye on 2017-3-19.
 */
public class Serialize {


    public static byte[] serialize(Object o) throws IOException{
        ByteArrayOutputStream byteArrayOutputStream=null;
        Hessian2Output hessian2Output=null;
        try{
            byteArrayOutputStream=new ByteArrayOutputStream();
            hessian2Output=new Hessian2Output(byteArrayOutputStream);
            hessian2Output.writeObject(o);
        }finally {
            hessian2Output.close();
            byteArrayOutputStream.close();
        }

        return byteArrayOutputStream.toByteArray();
    }


    public static  Object deserialize(byte[] bytes) throws IOException{
        ByteArrayInputStream byteArrayInputStream=null;
        Hessian2Input hessian2Input=null;
        Object object=null;
        try{
            byteArrayInputStream=new ByteArrayInputStream(bytes);
            hessian2Input=new Hessian2Input(byteArrayInputStream);
            object=hessian2Input.readObject();
        }finally {
            hessian2Input.close();
            byteArrayInputStream.close();
        }

        return object;
    }
}
