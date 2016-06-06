package org.seu.acetec.mes2Koala.core.domain;

import javax.persistence.*;

/**
 * @author yuxiangque
 * @version 2016/3/28
 */
@Entity
@Table(name = "E_CP_PASS_NODE")
@Access(AccessType.PROPERTY)
@PrimaryKeyJoinColumn(name = "CP_NODE_ID")
public class CPPassNode extends CPNode {
}
