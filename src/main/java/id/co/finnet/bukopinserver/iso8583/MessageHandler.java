/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.co.finnet.bukopinserver.iso8583;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISORequestListener;
import org.jpos.iso.ISOSource;

/**
 *
 * @author anggi
 */
public class MessageHandler implements ISORequestListener {

    @Override
    public boolean process(ISOSource isos, ISOMsg isomsg) {
        try {
            if (isomsg.getMTI().equalsIgnoreCase("2800")) {
                return handleNetman(isos, isomsg);
            } else {
                System.out.println("INVALID MTI");
                return false;
            }
        } catch (ISOException ex) {
            Logger.getLogger(MessageHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(MessageHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MessageHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;

    }

    private boolean handleNetman(ISOSource iSOSource, ISOMsg iSOMsg) throws InterruptedException, IOException, ISOException {
        ISOMsg response = (ISOMsg) iSOMsg.clone();
        response.setMTI("2810");
        response.set(39, "00");
        send(iSOSource, response, false, 0);
        return true;
    }

    private void send(ISOSource iSOSource, ISOMsg iSOMsg, boolean isReversal, int delay) throws InterruptedException, IOException, ISOException {
        if (!isReversal) {
            Thread.sleep(delay * 1000);
            iSOSource.send(iSOMsg);
        }
    }

}
