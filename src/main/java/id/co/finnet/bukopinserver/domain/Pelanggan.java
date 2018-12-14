/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.co.finnet.bukopinserver.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/**
 *
 * @author anggi
 */
@Data
@Table
@Entity
public class Pelanggan {
    private static final long serialVersionUID = -7371610187321351709L;

    @Id
    @Column(name = "id_pelanggan", unique = true, length = 12)
    private String idPelanggan;

    @Column(name = "no_meter", unique = true, length = 11)
    private String noMeter;

    @Column(name = "nama_pelanggan", length = 25)
    private String namaPelanggan;

    @Column(name = "service_unit", length = 5)
    private String serviceUnit;

    @Column(name = "service_unit_phone", length = 15)
    private String serviceUnitPhone;

    @Column(name = "service_unit_address", length = 35)
    private String serviceUnitAddress;
    
    @Column(name = "distribution_code", length = 2)
    private String distributionCode;
    
    @Column(name = "max_kwh_unit", length = 5)
    private Integer maxKwhUnit;

    @Column(name = "tarif", length = 4)
    private String tarif;

    @Column(name = "daya", length = 9)
    private String daya;

    @Column(name = "delay_response", nullable = false)
    private Long delayResponse = 0L;

    @Column(name = "response_code", nullable = false, length = 4)
    private String responseCode = "00";

    @Column(nullable = false, name = "reversal")
    private Boolean reversal = Boolean.FALSE;
    
}
