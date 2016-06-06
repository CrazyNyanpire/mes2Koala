package org.seu.acetec.mes2Koala.core.domain;
import org.dayatang.domain.Entity;
import org.dayatang.domain.EntityRepository;
import org.dayatang.domain.InstanceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
/**
 * 抽象实体类，可作为所有领域实体的基类，提供ID,版本,创建时间,最后修改时间属性。
 *
 * @author harlow
 */
@MappedSuperclass
public abstract class MES2AbstractEntity implements Entity {
    private static final long serialVersionUID = 8882145540383345037L;
    private static final Logger LOGGER = LoggerFactory.getLogger(MES2AbstractEntity.class);
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;
    @Version
    @Column(name = "VERSION")
    private int version;
    @Column(name = "CREATETIMESTAMP", updatable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date createTimestamp;

    @Column(name = "LASTMODIFYTIMESTAMP")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date lastModifyTimestamp;
    @Column(name = "CREATEEMPLOYNO")
    private String createEmployNo;

    @Column(name = "LASTMODIFYEMPLOYNO")
    private String lastModifyEmployNo;

    @Column(name = "LOGIC")
    private Integer logic;
    /**
     * 获得实体的标识
     *
     * @return 实体的标识
     */
    public Long getId() {
        return id;
    }
    /**
     * 设置实体的标识
     *
     * @param id 要设置的实体标识
     */
    public void setId(Long id) {
        this.id = id;
    }
    /**
     * 获得实体的版本号。持久化框架以此实现乐观锁。
     *
     * @return 实体的版本号
     */
    public int getVersion() {
        return version;
    }
    /**
     * 设置实体的版本号。持久化框架以此实现乐观锁。
     *
     * @param version 要设置的版本号
     */
    public void setVersion(int version) {
        this.version = version;
    }
    public Date getCreateTimestamp() {
        return createTimestamp;
    }
    public void setCreateTimestamp(Date createTimestamp) {
        this.createTimestamp = createTimestamp;
    }
    public Date getLastModifyTimestamp() {
        return lastModifyTimestamp;
    }
    public void setLastModifyTimestamp(Date lastModifyTimestamp) {
        this.lastModifyTimestamp = lastModifyTimestamp;
    }
    public String getCreateEmployNo() {
        return createEmployNo;
    }
    public void setCreateEmployNo(String createEmployNo) {
        this.createEmployNo = createEmployNo;
    }
    public String getLastModifyEmployNo() {
        return lastModifyEmployNo;
    }
    public void setLastModifyEmployNo(String lastModifyEmployNo) {
        this.lastModifyEmployNo = lastModifyEmployNo;
    }
    public Integer getLogic() {
        return logic;
    }
    public void setLogic(Integer logic) {
        this.logic = logic;
    }
    @Transient
    public boolean isNew() {
        return id == null || id.intValue() == 0;
    }
    @Override
    public boolean existed() {
        if (isNew()) {
            return false;
        }
        return getRepository().exists(getClass(), id);
    }
    @Override
    public boolean notExisted() {
        return !existed();
    }
    public boolean existed(String propertyName, Object propertyValue) {
        List<?> entities = getRepository().createCriteriaQuery(getClass()).eq(propertyName, propertyValue).list();
        return !(entities.isEmpty());
    }
    private static EntityRepository repository;
    public static EntityRepository getRepository() {
        if (repository == null) {
            repository = InstanceFactory.getInstance(EntityRepository.class, "repository_security");
        }
        return repository;
    }
    public static void setRepository(EntityRepository repository) {
        MES2AbstractEntity.repository = repository;
    }
    public void save() {
        Date date = new Date();
        if(this.getId() == null && this.getCreateTimestamp() == null)
            this.setCreateTimestamp(date);
        if(this.getId() == null && this.getCreateEmployNo() == null && this.getLastModifyEmployNo() != null)
            this.setCreateEmployNo(this.getLastModifyEmployNo());
        this.setLastModifyTimestamp(date);
        getRepository().save(this);
    }
    public void remove() {
        getRepository().remove(this);
    }
    /**
     * 请改用每个实体对象的实例方法的existed()方法。
     *
     * @param clazz
     * @param id
     * @return
     */
    @Deprecated
    public static <T extends Entity> boolean exists(Class<T> clazz, Serializable id) {
        return getRepository().exists(clazz, id);
    }
    public static <T extends Entity> T get(Class<T> clazz, Serializable id) {
        return getRepository().get(clazz, id);
    }
    public static <T extends Entity> T getUnmodified(Class<T> clazz, T entity) {
        return getRepository().getUnmodified(clazz, entity);
    }
    public static <T extends Entity> T load(Class<T> clazz, Serializable id) {
        return getRepository().load(clazz, id);
    }
    public static <T extends Entity> List<T> findAll(Class<T> clazz) {
        return getRepository().findAll(clazz);
    }
    /**
     * 获取业务主键。业务主键是判断相同类型的两个实体在业务上的等价性的依据。如果相同类型的两个
     * 实体的业务主键相同，则认为两个实体是相同的，代表同一个实体。
     * <p>业务主键由实体的一个或多个属性组成。
     *
     * @return 组成业务主键的属性的数组。
     */
    public abstract String[] businessKeys();
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((createEmployNo == null) ? 0 : createEmployNo.hashCode());
        result = prime * result + ((createTimestamp == null) ? 0 : createTimestamp.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((lastModifyEmployNo == null) ? 0 : lastModifyEmployNo.hashCode());
        result = prime * result + ((lastModifyTimestamp == null) ? 0 : lastModifyTimestamp.hashCode());
        result = prime * result + ((logic == null) ? 0 : logic.hashCode());
        result = prime * result + version;
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MES2AbstractEntity other = (MES2AbstractEntity) obj;
        if (createEmployNo == null) {
            if (other.createEmployNo != null)
                return false;
        } else if (!createEmployNo.equals(other.createEmployNo))
            return false;
        if (createTimestamp == null) {
            if (other.createTimestamp != null)
                return false;
        } else if (!createTimestamp.equals(other.createTimestamp))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (lastModifyEmployNo == null) {
            if (other.lastModifyEmployNo != null)
                return false;
        } else if (!lastModifyEmployNo.equals(other.lastModifyEmployNo))
            return false;
        if (lastModifyTimestamp == null) {
            if (other.lastModifyTimestamp != null)
                return false;
        } else if (!lastModifyTimestamp.equals(other.lastModifyTimestamp))
            return false;
        if (logic == null) {
            if (other.logic != null)
                return false;
        } else if (!logic.equals(other.logic))
            return false;
        if (version != other.version)
            return false;
        return true;
    }

    /*
    **
     * 依据业务主键获取哈希值。用于判定两个实体是否等价。
     * 等价的两个实体的hashCode相同，不等价的两个实体hashCode不同。
     *
     * @return 实体的哈希值
     *
    @Override
    public int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder(13, 37);
        Map<String, Object> propValues = new BeanUtils(this).getPropValues();
        for (String businessKey : businessKeys()) {
            builder = builder.append(propValues.get(businessKey));
        }
        return builder.toHashCode();
    }
    /**
     * 依据业务主键判断两个实体是否等价。
     *
     * @param other 另一个实体
     * @return 如果本实体和other等价则返回true, 否则返回false
     *
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }
        if (businessKeys() == null || businessKeys().length == 0) {
            return false;
        }
        if (!(this.getClass().isAssignableFrom(other.getClass()))) {
            return false;
        }
        Map<String, Object> thisPropValues = new BeanUtils(this).getPropValuesExclude(Transient.class);
        Map<String, Object> otherPropValues = new BeanUtils(other).getPropValuesExclude(Transient.class);
        EqualsBuilder builder = new EqualsBuilder();
        for (String businessKey : businessKeys()) {
            builder.append(thisPropValues.get(businessKey), otherPropValues.get(businessKey));
        }
        return builder.isEquals();
    }
    */
}