/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.co.finnet.bukopinserver.iso8583;

import id.co.finnet.bukopinserver.domain.Pelanggan;
import id.co.finnet.bukopinserver.domain.dao.PelangganDao;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISORequestListener;
import org.jpos.iso.ISOSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author anggi
 */
@Service
public class MessageHandler implements ISORequestListener {

    @Autowired
    private PelangganDao pelangganDao;

    @Override
    public boolean process(ISOSource isos, ISOMsg isomsg) {
        try {
            if (isomsg.getMTI().equalsIgnoreCase("2800")) {
                return handleNetman(isos, isomsg);
            } else if (isomsg.getMTI().substring(0,2).equalsIgnoreCase("22")) {
                handleFinancialRequest(isos, isomsg);
            } else {
                System.out.println("invalid mti");
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

    public boolean handleFinancialRequest(ISOSource iSOSource, ISOMsg iSOMsg) throws IOException, ISOException, InterruptedException {
        if (iSOMsg.getString(2).equals("99502")) {
           return handlePrepaid(iSOSource, iSOMsg);
        }else if(iSOMsg.getString(2).equals("99501")){
            // handle untuk postpaid
            return true;
        } else {
            // kalo kode pan nya salah
            ISOMsg response = (ISOMsg) iSOMsg.clone();
            response.setMTI("2210");
            
            //response kode produk salah
            response.set(39, "92");
            
            send(iSOSource, iSOMsg, false, 0);
            return  true;
        }
    }

    public boolean handlePrepaid(ISOSource iSOSource, ISOMsg iSOMsg) throws IOException, ISOException {

        String msn = iSOMsg.getString(48).substring(7, 18);

        Pelanggan pelanggan = pelangganDao.findByNoMeter(msn);
        ISOMsg response = (ISOMsg) iSOMsg.clone();

        if (pelanggan != null) {
            response.set(39, "00");
            // diisi sesuai spec
            
            return true;
        } else {
            response.setMTI("2210");
            response.set(39, "14");
            iSOSource.send(response);
            return true;
        }

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
