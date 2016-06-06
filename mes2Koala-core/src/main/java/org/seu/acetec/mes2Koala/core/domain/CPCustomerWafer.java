package org.seu.acetec.mes2Koala.core.domain;

import javax.persistence.*;

/**
 * @author yuxiangque
 * @version 2016/3/30
 */
@Entity
@Table(name = "E_CP_CUSTOMER_WAFER")
@Access(AccessType.PROPERTY)
public class CPCustomerWafer extends MES2AbstractEntity {

    private CustomerCPLot customerCPLot;

    private String waferCode;
    private String waferIndex;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_CP_LOT_ID")
    public CustomerCPLot getCustomerCPLot() {
        return customerCPLot;
    }

    public void setCustomerCPLot(CustomerCPLot customerCPLot) {
        this.customerCPLot = customerCPLot;
    }

    public String getWaferCode() {
        return waferCode;
    }

    public void setWaferCode(String waferId) {
        this.waferCode = waferId;
    }

    @Override
    public String[] businessKeys() {
        return new String[0];
    }

    public String getWaferIndex() {
        return waferIndex;
    }

    public void setWaferIndex(String waferIndex) {
        this.waferIndex = waferIndex;
    }
}
