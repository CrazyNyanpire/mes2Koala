package org.seu.acetec.mes2Koala.core.domain;

import javax.persistence.*;

/**
 * @author yuxiangque
 * @version 2016/3/29
 */
@Entity
@Table(name = "E_CP_SBL_TEMPLATE")
@Access(AccessType.PROPERTY)
public class CPSBLTemplate extends MES2AbstractEntity {
	
	private static final long serialVersionUID = -4651874212268274045L;
	
	private String parentIntegrationIds;
    //规则
    private int rule;
    // bin别：工程师维护产品需要的Bin别
    private String name;
    // 良率上限
    private double lowerLimit;
    // 良率下限
    private double upperLimit;
    // 良率单位
    private LimitUnit limitUnit;
    // 品质:Pass、Fail
    private Quality quality;
    // Site：合并Bin使用，Site一致的Bin别表示这些Bin别可以合并，良率计算时卡合并后的良率；不再对单一病别卡控
    private String site;
    // Bin类型：类型包括HB和SB,
    private Type type;
    // 站点：维护的SBL用于哪个测试站点
    private String node;
    //使用范围
    private String testRange;
    //卡控形式
    private String controlType;
    //产品信息
    private InternalProduct internalProduct;

    //2016/3/31 HongYu
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "Product_ID")
    public InternalProduct getInternalProduct() {
        return internalProduct;
    }

    public void setInternalProduct(InternalProduct internalProduct) {
        this.internalProduct = internalProduct;
    }
    
    @Enumerated(EnumType.STRING)
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Enumerated(EnumType.STRING)
    public LimitUnit getLimitUnit() {
        return limitUnit;
    }

    public void setLimitUnit(LimitUnit limitUnit) {
        this.limitUnit = limitUnit;
    }

    @Enumerated(EnumType.STRING)
    public Quality getQuality() {
        return quality;
    }

    public void setQuality(Quality quality) {
        this.quality = quality;
    }

    public String getParentIntegrationIds() {
        return parentIntegrationIds;
    }

    public void setParentIntegrationIds(String parentIntegrationIds) {
        this.parentIntegrationIds = parentIntegrationIds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLowerLimit() {
        return lowerLimit;
    }

    public void setLowerLimit(double lowerLimit) {
        this.lowerLimit = lowerLimit;
    }

    public double getUpperLimit() {
        return upperLimit;
    }

    public void setUpperLimit(double upperLimit) {
        this.upperLimit = upperLimit;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    @Override
    public String[] businessKeys() {
        return new String[0];
    }

    public int getRule() {
		return rule;
	}

	public void setRule(int rule) {
		this.rule = rule;
	}

	public String getControlType() {
		return controlType;
	}

	public void setControlType(String controlType) {
		this.controlType = controlType;
	}

	public String getTestRange() {
		return testRange;
	}

	public void setTestRange(String testRange) {
		this.testRange = testRange;
	}

	public enum Quality {
        PASS,
        FAIL;

        public static String getStringValue(Quality quality) {
            switch (quality) {
                case PASS:
                    return "PASS";
                case FAIL:
                    return "FAIL";
                default:
                    throw new IllegalArgumentException("");
            }
        }

        public static Quality fromStringValue(String value) {
            switch (value) {
                case "PASS":
                    return PASS;
                case "FAIL":
                    return FAIL;
                default:
                    throw new IllegalArgumentException("");
            }
        }
    }

    public enum LimitUnit {
        PERCENTAGE,
        QUANTITY;

        public static String getStringValue(LimitUnit limitUnit) {
            switch (limitUnit) {
                case PERCENTAGE:
                    return "PERCENTAGE";
                case QUANTITY:
                    return "QUANTITY";
                default:
                    throw new IllegalArgumentException("");
            }
        }

        public static LimitUnit fromStringValue(String value) {
            switch (value) {
                case "PERCENTAGE":
                    return PERCENTAGE;
                case "QUANTITY":
                    return QUANTITY;
                default:
                    throw new IllegalArgumentException("");
            }
        }
    }

    public enum Type {
        HB,  // 未到这个站
        SB;   // 到这个站没进站

        public static String getStringValue(Type state) {
            switch (state) {
                case HB:
                    return "HB";
                case SB:
                    return "SB";
                default:
                    throw new IllegalArgumentException("");
            }
        }

        public static Type fromStringValue(String value) {
            switch (value) {
                case "HB":
                    return HB;
                case "SB":
                    return SB;
                default:
                    throw new IllegalArgumentException("");
            }
        }
    }
}
