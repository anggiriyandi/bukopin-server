/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.co.finnet.bukopinserver.iso8583;

import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISORequestListener;
import org.jpos.iso.ISOSource;

/**
 *
 * @author anggi
 */

public class MessageHandler implements ISORequestListener{

    @Override
    public boolean process(ISOSource isos, ISOMsg isomsg) {
        return true;
        
    }
    
}
