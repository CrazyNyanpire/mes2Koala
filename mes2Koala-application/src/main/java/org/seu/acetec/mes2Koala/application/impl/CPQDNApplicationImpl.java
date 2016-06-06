package org.seu.acetec.mes2Koala.application.impl;

import java.util.List;
import java.util.Set;

import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;
import org.seu.acetec.mes2Koala.application.CPQDNApplication;
import org.seu.acetec.mes2Koala.core.domain.CPQDN;
import org.seu.acetec.mes2Koala.core.domain.FTQDN;

@Named
@Transactional
public class CPQDNApplicationImpl extends GenericMES2ApplicationImpl<CPQDN> implements CPQDNApplication {

	public CPQDN getCPQDN(Long id) {
		return CPQDN.get(CPQDN.class, id);
	}
	
	public void creatCPQDN(CPQDN cPQDN) {
		cPQDN.save();
	}
	
	public void updateCPQDN(CPQDN cPQDN) {
		cPQDN .save();
	}
	
	public void removeCPQDN(CPQDN cPQDN) {
		if(cPQDN != null){
			cPQDN.remove();
		}
	}
	
	public void removeCPQDNs(Set<CPQDN> cPQDNs) {
		for (CPQDN cPQDN : cPQDNs) {
			cPQDN.remove();
		}
	}
	
	public List<CPQDN> findAllCPQDN() {
		return CPQDN.findAll(CPQDN.class);
	}
	
	@Override
    public List<CPQDN> findAllDoingByCPLotId(Long cpLotId) {
        StringBuilder jpql = new StringBuilder("select a from CPQDN a where 1 = 1 ");
        jpql.append(" and a.status < 2");
        if (cpLotId != null) {
            jpql.append(" and a.cpLot.id = ?");
        }
        jpql.append(" order by a.id desc");
        return find(jpql.toString(), cpLotId);
    }
	
	@Override
    public CPQDN findByCPLotId(Long cpLotId) {
        StringBuilder jpql = new StringBuilder("select a from CPQDN a where 1 = 1 ");
        if (cpLotId != null) {
            jpql.append(" and a.cpLot.id = ?");
        }
        jpql.append(" order by a.id desc");
        return findOne(jpql.toString(), cpLotId);
    }
}
