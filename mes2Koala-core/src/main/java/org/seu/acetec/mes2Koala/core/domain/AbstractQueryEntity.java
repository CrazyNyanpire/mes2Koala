/**
 * 
 */
package org.seu.acetec.mes2Koala.core.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.dayatang.domain.Entity;
import org.dayatang.domain.EntityRepository;
import org.dayatang.domain.InstanceFactory;


/**
 * 抽象实体类，可作为所有领域实体的基类，提供ID和版本属性。
 * 
 * @author harlow
 * 
 */
@MappedSuperclass
public abstract class AbstractQueryEntity implements Entity {

	private static final long serialVersionUID = 8882145540383345037L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "uuid")
	private String id;

	/**
	 * 获得实体的标识
	 * 
	 * @return 实体的标识
	 */
	public String getId() {
		return id;
	}

	/**
	 * 设置实体的标识
	 * 
	 * @param id
	 *            要设置的实体标识
	 */
	public void setId(String id) {
		this.id = id;
	}

	@Transient
	public boolean isNew() {
		return id == null || "".equals("");
	}

	public boolean existed() {
		if (isNew()) {
			return false;
		}
		return getRepository().exists(getClass(), id);
	}

	public boolean notExisted() {
		return ! existed();
	}
	
	public boolean existed(String propertyName, Object propertyValue) {
		List<?> entities = getRepository().createCriteriaQuery(getClass()).eq(propertyName, propertyValue).list();
		return !(entities.isEmpty());
	}

	private static EntityRepository repository;

	public static EntityRepository getRepository() {
		if (repository == null) {
			repository = InstanceFactory.getInstance(EntityRepository.class,"repository_gqc");
		}
		return repository;
	}

	public static void setRepository(EntityRepository repository) {
		AbstractQueryEntity.repository = repository;
	}

	public void save() {
		getRepository().save(this);
	}

	public void remove() {
		getRepository().remove(this);
	}

	/**
	 * 请改用每个实体对象的实例方法的existed()方法。
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


	@Override
	public abstract int hashCode();

	@Override
	public abstract boolean equals(Object other);

	@Override
	public abstract String toString();
}
