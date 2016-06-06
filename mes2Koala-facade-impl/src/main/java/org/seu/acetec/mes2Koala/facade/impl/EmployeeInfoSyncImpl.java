package org.seu.acetec.mes2Koala.facade.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.dayatang.domain.InstanceFactory;
import org.dayatang.querychannel.QueryChannelService;
import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.openkoala.organisation.core.domain.Employee;
import org.openkoala.organisation.facade.EmployeeFacade;
import org.openkoala.organisation.facade.JobFacade;
import org.openkoala.organisation.facade.OrganizationFacade;
import org.openkoala.organisation.facade.PostFacade;
import org.openkoala.organisation.facade.dto.EmployeeDTO;
import org.openkoala.organisation.facade.dto.JobDTO;
import org.openkoala.organisation.facade.dto.OrganizationDTO;
import org.openkoala.organisation.facade.dto.PostDTO;
import org.openkoala.organisation.facade.dto.ResponsiblePostDTO;
import org.openkoala.security.org.core.domain.EmployeeUser;
import org.openkoala.security.org.facade.MesPositionFacade;
import org.openkoala.security.org.facade.MesStaffsFacade;
import org.openkoala.security.org.facade.OADepartmentFacade;
import org.openkoala.security.org.facade.OAUserFacade;
import org.openkoala.security.org.facade.OAUserPrivFacade;
import org.openkoala.security.org.facade.SecurityOrgAccessFacade;
import org.openkoala.security.org.facade.SecurityOrgConfigFacade;
import org.openkoala.security.org.facade.command.CreateEmpolyeeUserCommand;
import org.openkoala.security.org.facade.dto.AuthorizationCommand;
import org.openkoala.security.org.facade.dto.EmployeeUserDTO;
import org.openkoala.security.org.facade.dto.MesPositionDTO;
import org.openkoala.security.org.facade.dto.MesStaffsDTO;
import org.openkoala.security.org.facade.dto.OADepartmentDTO;
import org.openkoala.security.org.facade.dto.OAUserDTO;
import org.openkoala.security.org.facade.dto.OAUserPrivDTO;
import org.seu.acetec.mes2Koala.facade.EmployeeInfoSync;

@Named
public class EmployeeInfoSyncImpl implements EmployeeInfoSync {
	@Inject
	private EmployeeFacade employeeFacade;

	@Inject
	private MesStaffsFacade mesStaffsFacade;

	@Inject
	private OADepartmentFacade oaDepartmentFacade;

	@Inject
	private OAUserPrivFacade oaUserPrivFacade;

	@Inject
	private MesPositionFacade mesPositionFacade;

	@Inject
	private PostFacade postFacade;

	@Inject
	private JobFacade jobFacade;

	@Inject
	private SecurityOrgConfigFacade securityOrgConfigFacade;

	@Inject
	private OrganizationFacade organizationFacade;

	private static Logger LOGGER = Logger.getLogger(EmployeeInfoSyncImpl.class);

	public void sync() {
		List<MesStaffsDTO> list = mesStaffsFacade.findAll();
		List<EmployeeDTO> employeeList = employeeFacade.pagingQueryEmployees(
				new EmployeeDTO(), 0, 10000).getData();
		List<PostDTO> postList = postFacade.pagingQueryPosts(new PostDTO(), 0,
				1000).getData();
		for (MesStaffsDTO mesStaffsDTO : list) {
			EmployeeDTO employeeDTO = this.getEmployeePersistent(employeeList,
					mesStaffsDTO);
			if(employeeDTO == null){
				continue;
			}
			if(employeeDTO != null && mesStaffsDTO.getAccounts().indexOf("ACE") < 0){
				employeeFacade.terminateEmployee(employeeDTO);
				continue;
			}
			employeeDTO = this.createEmployeeDTO(employeeDTO, mesStaffsDTO);
			if (employeeDTO.getId() == null) {
				employeeDTO.setPostName(mesStaffsDTO.getUserPriv());
				employeeFacade.createEmployee(employeeDTO);
			} else {
				// employeeDTO.setOrganizationName(organizationName);
				employeeDTO.setPostName(mesStaffsDTO.getUserPriv());
				employeeFacade.updateEmployeeInfo(employeeDTO);
			}
			PostDTO postDTO = this.getPostPersistent(postList, mesStaffsDTO);
			if (postDTO != null) {
				ResponsiblePostDTO responsiblePostDTO = new ResponsiblePostDTO();
				ResponsiblePostDTO[] rplist = new ResponsiblePostDTO[1];
				responsiblePostDTO.setPostId(postDTO.getId());
				responsiblePostDTO.setPrincipal(true);
				rplist[0] = responsiblePostDTO;
				employeeFacade.transformPost(employeeDTO.getId(), rplist);
			}
		}
	}

	public InvokeResult syncDepartment() {
		LOGGER.info("syncDepartment Start!!!");
		List<MesStaffsDTO> list = mesStaffsFacade.findAllDepartnent();
		List<OrganizationDTO> organizationList = this.findOrganizationAll();
		for (MesStaffsDTO mesStaffsDTO : list) {
			OrganizationDTO organizationDTO = this.getOrganizationPersistent(
					organizationList, mesStaffsDTO.getDeptName());
			organizationDTO = this.createOrganizationDTO(organizationDTO,
					mesStaffsDTO);
			if (organizationDTO.getId() == null) {
				organizationFacade.createDepartment(Long.valueOf(1),
						organizationDTO);
			} else {
				organizationFacade.updateOrganization(organizationDTO);
			}
		}
		return InvokeResult.success();
	}

	public List<OrganizationDTO> findOrganizationAll() {
		QueryChannelService queryChannel = InstanceFactory.getInstance(
				QueryChannelService.class, "queryChannel_org");

		StringBuilder jpql = new StringBuilder(
				"SELECT DISTINCT NEW org.openkoala.organisation.facade.dto.OrganizationDTO"
						+ "(r.id, olm.commissioner.id, r.name, r.sn, r.createDate, r.terminateDate, r.description, r.category, r.version) "
						+ "FROM OrganizationLineManagement olm LEFT JOIN olm.responsible r "
						+ "WHERE olm.commissioner is not null AND  olm.toDate > :queryDate AND olm.fromDate <= :queryDate ORDER BY r.id ASC");
		List<OrganizationDTO> all = queryChannel
				.createJpqlQuery(jpql.toString())
				.addParameter("queryDate", new Date()).list();
		return all;
	}

	@SuppressWarnings("unchecked")
	public void createUser() {
		List<Employee> employeeList = (List<Employee>) employeeFacade.findAll()
				.getData();
		List<Employee> newEmployee = new ArrayList<Employee>();
		for (Employee employee : employeeList) {
			CreateEmpolyeeUserCommand command = new CreateEmpolyeeUserCommand();
			// name=&userAccount=&description=&employeeid=
			command.setName(employee.getName());
			command.setUserAccount(employee.getSn());
			command.setEmployeeId(employee.getId());
			command.setCreateOwner("Koala");
			InvokeResult invokeResult = securityOrgConfigFacade
					.createEmployeeUser(command);
			if (invokeResult.isHasErrors()) {
				newEmployee.add(employee);
			}
		}
	}

	public void grantUser() {
		/*
		 * List<EmployeeUserDTO> noRoleUsers = securityOrgAccessFacade
		 * .queryEmployeeUsersNoRole(); for (EmployeeUserDTO user : noRoleUsers)
		 * { AuthorizationCommand command = new AuthorizationCommand(); //
		 * actorId
		 * （用户ID）=&organizationId=2&organizationName=IT&authorityIds=（角色ID）
		 * command.setActorId(user.getId());
		 * command.setOrganizationId(Long.valueOf(2));
		 * command.setOrganizationName("IT"); command.setAuthorityIds(new Long[]
		 * { Long.valueOf(9) }); // SecurityConfigFacade InvokeResult
		 * invokeResult = securityOrgConfigFacade
		 * .grantAuthorityToActorInScope(command); if
		 * (invokeResult.isHasErrors())
		 * LOGGER.info(invokeResult.getErrorMessage()); }
		 */

	}

	private EmployeeDTO createEmployeeDTO(EmployeeDTO employeeDto,
			MesStaffsDTO mesStaffsDTO) {
		if (employeeDto == null)
			employeeDto = new EmployeeDTO();
		employeeDto.setGender("男".equals(mesStaffsDTO.getSex()) ? "MALE"
				: "FEMALE");
		employeeDto.setMobilePhone(mesStaffsDTO.getMobile());
		employeeDto.setFamilyPhone("");
		employeeDto.setEmail(mesStaffsDTO.getEmail());
		employeeDto.setName(mesStaffsDTO.getName());
		employeeDto.setSn(mesStaffsDTO.getAccounts().replace("ACE", ""));
		employeeDto.setOrganizationName(mesStaffsDTO.getDeptName());
		employeeDto.setPostName(mesStaffsDTO.getUserPriv());
		return employeeDto;
	}

	private EmployeeDTO getEmployeePersistent(List<EmployeeDTO> employeeList,
			MesStaffsDTO mesStaffsDTO) {
		for (EmployeeDTO employeeDTO : employeeList) {
			if (employeeDTO.getSn().equalsIgnoreCase(
					mesStaffsDTO.getAccounts().replace("ACE", ""))) {
				return employeeDTO;
			}
		}
		return null;
	}

	private PostDTO getPostPersistent(List<PostDTO> postList,
			MesStaffsDTO mesStaffsDTO) {
		for (PostDTO postDTO : postList) {
			if (postDTO.getSn().equalsIgnoreCase(
					mesStaffsDTO.getDeptName() + "-"
							+ mesStaffsDTO.getUserPriv())) {
				return postDTO;
			}
		}
		return null;
	}

	private OrganizationDTO getOrganizationPersistent(
			List<OrganizationDTO> organizationList, Long id) {
		for (OrganizationDTO organizationDTO : organizationList) {
			if (organizationDTO.getSn().equalsIgnoreCase(id.toString())) {
				return organizationDTO;
			}
		}
		return null;
	}

	private Long getOrganizationId(List<OrganizationDTO> organizationList,
			String deptParentNo) {
		for (OrganizationDTO organizationDTO : organizationList) {
			if (organizationDTO.getSn().equalsIgnoreCase(deptParentNo)) {
				return organizationDTO.getId();
			}
		}
		return Long.valueOf(1);
	}

	private OrganizationDTO createOrganizationDTO(
			OrganizationDTO organizationDTO, MesStaffsDTO mesStaffsDTO) {
		if (organizationDTO == null)
			organizationDTO = new OrganizationDTO();
		organizationDTO.setSn(mesStaffsDTO.getDeptName().toString());
		if (organizationDTO.getId() != null) {
			organizationDTO.setName(mesStaffsDTO.getDeptName());
		} else {
			organizationDTO.setName(mesStaffsDTO.getDeptName());
		}
		organizationDTO.setDescription(mesStaffsDTO.getDeptName());
		organizationDTO.setOrganizationType(OrganizationDTO.DEPARTMENT);
		return organizationDTO;
	}

	private JobDTO getJobPersistent(List<JobDTO> jobList, String userPriv) {
		for (JobDTO jobDTO : jobList) {
			if (jobDTO.getSn().equalsIgnoreCase(userPriv)) {
				return jobDTO;
			}
		}
		return null;
	}

	private JobDTO createJobDTO(JobDTO jobDTO, MesStaffsDTO mesStaffsDTO) {
		if (jobDTO == null)
			jobDTO = new JobDTO();
		jobDTO.setSn(mesStaffsDTO.getUserPriv());
		jobDTO.setName(mesStaffsDTO.getUserPriv());
		return jobDTO;
	}

	public void syncJob() {
		LOGGER.info("syncJob Start!!!");
		List<MesStaffsDTO> list = mesStaffsFacade.findAll();
		List<JobDTO> jobList = jobFacade.findAllJobs();
		for (MesStaffsDTO mesStaffsDTO : list) {
			JobDTO jobDTO = this.getJobPersistent(jobList,
					mesStaffsDTO.getUserPriv());
			jobDTO = this.createJobDTO(jobDTO, mesStaffsDTO);
			if (jobDTO.getId() == null) {
				jobFacade.createJob(jobDTO);
			} else {
				jobFacade.updateJobInfo(jobDTO);
			}
		}
	}

	private PostDTO getPositionPersistent(List<PostDTO> postList,
			String postCode) {
		for (PostDTO postDTO : postList) {
			if (postDTO.getSn().equalsIgnoreCase(postCode)) {
				return postDTO;
			}
		}
		return null;
	}

	private PostDTO createPositionDTO(PostDTO postDTO,
			MesStaffsDTO mesStaffsDTO, List<OrganizationDTO> orgList) {
		if (postDTO == null)
			postDTO = new PostDTO();
		postDTO.setSn(mesStaffsDTO.getDeptName() + "-"
				+ mesStaffsDTO.getUserPriv());
		postDTO.setName(mesStaffsDTO.getUserPriv());
		OrganizationDTO organizationDTO = this.getOrganizationPersistent(
				orgList, mesStaffsDTO.getDeptName());
		postDTO.setJobId(Long.valueOf(42));
		if (organizationDTO != null && organizationDTO.getId() != null) {
			postDTO.setOrganizationId(organizationDTO.getId());
			postDTO.setOrganizationName(organizationDTO.getName());
		}
		return postDTO;
	}

	public void syncPosition() {
		LOGGER.info("syncPosition Start!!!");
		List<OrganizationDTO> orgList = this.findOrganizationAll();

		List<MesStaffsDTO> list = mesStaffsFacade.findAllPostAndDepartment();
		List<PostDTO> postList = postFacade.pagingQueryPosts(new PostDTO(), 0,
				1000).getData();
		for (MesStaffsDTO mesStaffsDTO : list) {
			PostDTO postDTO = this.getPositionPersistent(
					postList,
					mesStaffsDTO.getDeptName() + "-"
							+ mesStaffsDTO.getUserPriv());
			postDTO = this.createPositionDTO(postDTO, mesStaffsDTO, orgList);
			if (postDTO.getOrganizationId() == null) {
				continue;
			}
			if (postDTO.getId() == null) {
				postFacade.createPost(postDTO);
			} else {
				postFacade.updatePostInfo(postDTO);
			}
		}
	}

	private OrganizationDTO getOrganizationPersistent(
			List<OrganizationDTO> organizationList, String name) {
		for (OrganizationDTO organizationDTO : organizationList) {
			if (organizationDTO.getName().equalsIgnoreCase(name)) {
				return organizationDTO;
			}
		}
		return null;
	}
}
