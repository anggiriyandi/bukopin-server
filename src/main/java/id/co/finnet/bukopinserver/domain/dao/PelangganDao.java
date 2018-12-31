/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.co.finnet.bukopinserver.domain.dao;

import id.co.finnet.bukopinserver.domain.Pelanggan;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 *
 * @author anggi
 */
public interface PelangganDao extends PagingAndSortingRepository<Pelanggan, String>{
    Pelanggan findByNoMeter(String msn);
}
