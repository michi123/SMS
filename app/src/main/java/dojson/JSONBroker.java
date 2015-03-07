/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dojson;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

/**
 *
 * @author Dominik
 */
public class JSONBroker {
    private final Gson GSON;

    public JSONBroker() {
        GSON = new Gson();
    }
    
    public String messageToJson(MsgPack pack){
        System.out.println("messagetoJson");
        return GSON.toJson(pack);
    }
    public String contactToJson(MsgPack pack){
        return GSON.toJson(pack);
    }
    public String deviceToJson(MsgPack pack){
        return GSON.toJson(pack);
    }
    
    public MsgPack extractObject(String json){
        System.out.println("zavolan Extract object");
        MsgPack mes = null;
        Type type2 = new TypeToken<MsgPack<MessageCo>>() {}.getType();
        mes = GSON.fromJson(json,type2);
        return mes;
    }
    
    
    
}
