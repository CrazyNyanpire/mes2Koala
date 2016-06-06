package org.seu.acetec.mes2Koala.core.domain;

import javax.persistence.*;

@Entity
@Table(name = "E_FT_FINISH_NODE")
@Access(AccessType.PROPERTY)
@PrimaryKeyJoinColumn(name = "FT_NODE_ID")
public class FTFinishNode extends FTNode {
    private static final long serialVersionUID = 7652650081885849856L;
}
