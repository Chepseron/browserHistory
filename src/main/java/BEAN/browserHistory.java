package BEAN;

import com.amon.db.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

@SessionScoped
@ManagedBean(name = "browserHistory")
public class browserHistory implements Serializable {

    @PersistenceContext(unitName = "browserHistoryPU")
    private EntityManager em;
    @Resource
    private UserTransaction utx;

    private List<Usergroup> group1List = new ArrayList<Usergroup>();
    private Usergroup group1 = new Usergroup();

    private List<Audit> AuditList = new ArrayList<Audit>();
    private Audit audit = new Audit();

    private List<Browserhistory> BrowserList = new ArrayList<Browserhistory>();
    private Browserhistory Browser = new Browserhistory();

    private List<User> userList = new ArrayList<User>();
    private User user = new User();

    private List<Status> statusList = new ArrayList<Status>();
    private Status status = new Status();

    private String username = new String();
    private String password = new String();

    private Boolean remember = new Boolean(false);

    public browserHistory() {
    }

    @PostConstruct
    public void init() {
        try {
            createBrowserModel();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private LineChartModel browserHistoryModel;

    private void createBrowserModel() {
        setBrowserList((List<Browserhistory>) getEm().createQuery("select a from Browser a").getResultList());
        setBrowserModel(new LineChartModel());
        LineChartSeries Cohort = new LineChartSeries();
        Cohort.setFill(true);
        Cohort.setLabel("Browser");

        for (Browserhistory med : getBrowserList()) {
            Cohort.set(med.getIdbrowserHistory(), med.getIdbrowserHistory());
        }

        getBrowserModel().addSeries(Cohort);
        getBrowserModel().setTitle("Browser");
        getBrowserModel().setLegendPosition("ne");
        getBrowserModel().setStacked(true);
        getBrowserModel().setShowPointLabels(true);

        Axis xAxis = new CategoryAxis("Places/Road occured");
        getBrowserModel().getAxes().put(AxisType.X, xAxis);
        Axis yAxis = getBrowserModel().getAxis(AxisType.Y);
        yAxis.setLabel("Deceased victims");
        yAxis.setMin(0);

        BrowserList = (List<Browserhistory>) getEm().createQuery("select a from Browser a").getResultList();
        for (Browserhistory c : BrowserList) {
        }

        for (Browserhistory c : BrowserList) {
        }

    }

    public String login() {
        try {

            setUser((User) getEm().createQuery("select u from User u where u.username = '" + getUsername() + "' and u.pword = '" + getPassword() + "'").getSingleResult());
            setGroup1((Usergroup) getEm().createQuery("select u from Usergroup u where u.idgroups = " + getUser().getGroupID() + "").getSingleResult());
            getUtx().begin();
            getAudit().setAction("logged into the system at  " + new Date());
            getAudit().setCreatedby(1);
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            getUtx().commit();
            return "index2.xhtml?faces-redirect=true";
        } catch (Exception e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "!ERROR!", "Please provide correct credentials");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("loginInfoMessages", message);
            e.printStackTrace();
            return null;
        }

    }

    public String logout() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getAttributes().clear();
        return "/index.xhtml?faces-redirect=true";
    }

    public String registerBrowserHistory() {
        try {

            getUtx().begin();
            getAudit().setAction("created browser history");
            getAudit().setCreatedby(1);
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            getEm().persist(Browser);
            getUtx().commit();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", getBrowser().getBrowser() + " saved successfully."));
            setBrowser(new Browserhistory());
        } catch (Exception ex) {
            ex.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", ex.getMessage()));
        }
        setBrowser(new Browserhistory());
        return null;
    }

    public String createUser() {
        try {

            getUser().setCreatedAt(new java.util.Date());
            getUser().setCreatedBy(1);
            getUser().setLastLogin(new java.util.Date());
            getUser().setStatusID(1);
            getUser().setPword("1234");
            getUser().setDepartment(1);
            getUtx().begin();
            getAudit().setAction("saved user " + getUser().getUsername());
            getAudit().setCreatedby(1);
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            getEm().persist(getUser());
            getUtx().commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", getUser().getName() + " saved successfully."));
            setUser(new User());
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", ex.getMessage()));
        }
        setUser(new User());
        return null;
    }

    public String updateUser() {
        try {

            User user2 = getEm().find(User.class,
                    user.getIdusers());
            user2.setCreatedAt(new java.util.Date());
            user2.setCreatedBy(1);
            user2.setDepartment(1);
            user2.setEmail(user.getEmail());
            user2.setGroupID(user.getGroupID());
            user2.setLastLogin(new java.util.Date());
            user2.setName(user.getName());
            user2.setPhone(user.getPhone());
            user2.setPword("1234");
            user2.setStaffID(user.getStaffID());
            user2.setStatusID(1);
            user2.setUsername(user.getUsername());

            getUtx().begin();
            getAudit().setAction("updated user " + user.getIdusers());
            getAudit().setCreatedby(1);
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            getEm().merge(user2);
            getUtx().commit();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", user.getName() + " Updated successfully."));
            user = new User();
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Could not update a user."));
        }
        setUser(new User());
        return null;
    }

    public String deleteUser(User user) {
        try {

            getUtx().begin();
            getAudit().setAction("Deleted user");
            getAudit().setCreatedby(user.getIdusers());
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            User toBeRemoved = (User) getEm().merge(user);
            getEm().remove(toBeRemoved);
            getUtx().commit();
            user = new User();
            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_ERROR, "User deleted", "User deleted");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("User", success);

            return null;
        } catch (Exception e) {
            e.printStackTrace();

            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage());
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("User", success);
        }
        user = new User();
        return null;
    }

    public String createUserGroup() {
        try {

            getUtx().begin();
            getAudit().setAction("saved group " + group1.getName());
            getAudit().setCreatedby(1);
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            getEm().persist(group1);
            getUtx().commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", group1.getName() + " saved successfully."));
            setGroup1(new Usergroup());
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", ex.getMessage()));
        }
        setGroup1(new Usergroup());
        return null;
    }

    public String updateUserGroup() {
        try {

            Usergroup user2 = getEm().find(Usergroup.class,
                    group1.getIdgroups());
            user2.setCreatedAt(new java.util.Date());
            user2.setCreatedBy(new User(1));
            user2.setName(group1.getName());
            user2.setStatusID(new Status(1));
            user2.setResponsibilities(group1.getResponsibilities());

            getUtx().begin();
            getAudit().setAction("updated user group " + user.getIdusers());
            getAudit().setCreatedby(1);
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            getEm().merge(user2);
            getUtx().commit();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", user.getName() + " Updated successfully."));
            group1 = new Usergroup();
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Could not update a user."));
        }
        group1 = new Usergroup();
        return null;
    }

    public String deleteUserGroup(Usergroup user) {
        try {

            getUtx().begin();
            getAudit().setAction("Deleted user group");
            getAudit().setCreatedby(1);
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            Usergroup toBeRemoved = (Usergroup) getEm().merge(group1);
            getEm().remove(toBeRemoved);
            getUtx().commit();
            group1 = new Usergroup();
            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_ERROR, "UserGroup deleted", "UserGroup deleted");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("UserGroup", success);

            return null;
        } catch (Exception e) {
            e.printStackTrace();

            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage());
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("UserGroup", success);
        }
        group1 = new Usergroup();
        return null;
    }

    public String createStatus() {
        try {

            getStatus().setCreatedBy(getUser());
            getUtx().begin();
            getAudit().setAction("created status");
            getAudit().setCreatedby(1);
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            getEm().persist(getStatus());
            getUtx().commit();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", getStatus().getName() + " saved successfully."));
            setStatus(new Status());
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", ex.getMessage()));
        }
        setStatus(new Status());
        return null;
    }

    public String updateStatus() {
        try {

            Status statu = getEm().find(Status.class,
                    this.status.getIdstatus());

            statu.setCreatedBy(user);
            statu.setDescription(status.getDescription());
            statu.setName(status.getName());

            getUtx().begin();
            getAudit().setAction("updated status");
            getAudit().setCreatedby(1);
            getAudit().setTimer(new Date());

            getEm().persist(getAudit());
            getEm().merge(statu);
            getUtx().commit();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", status.getName() + " Updated successfully."));
            status = new Status();
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Could not update a status."));
        }
        setStatus(new Status());
        return null;
    }

    public String deleteStatus(Status status) {
        try {

            getUtx().begin();
            getAudit().setAction("Deleted status");
            getAudit().setCreatedby(1);
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            Status toBeRemoved = (Status) getEm().merge(status);
            getEm().remove(toBeRemoved);
            getUtx().commit();
            status = new Status();
            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Status deleted", "Status deleted");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("Status", success);

            return null;
        } catch (Exception e) {
            e.printStackTrace();

            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage());
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("Status", success);
        }
        status = new Status();
        return null;
    }

    /**
     * @return the em
     */
    public EntityManager getEm() {
        return em;
    }

    /**
     * @param em the em to set
     */
    public void setEm(EntityManager em) {
        this.em = em;
    }

    /**
     * @return the utx
     */
    public javax.transaction.UserTransaction getUtx() {
        return utx;
    }

    /**
     * @param utx the utx to set
     */
    public void setUtx(javax.transaction.UserTransaction utx) {
        this.utx = utx;
    }

    /**
     * @return the group1
     */
    public Usergroup getUsergroup() {
        return getGroup1();
    }

    /**
     * @param group1 the group1 to set
     */
    public void setUsergroup(Usergroup group1) {
        this.setGroup1(group1);
    }

    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @return the userList
     */
    public List<User> getUserList() {
        userList = getEm().createQuery("select u from User u").getResultList();
        return userList;
    }

    /**
     * @param userList the userList to set
     */
    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    /**
     * @return the status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * @return the statusList
     */
    public List<Status> getStatusList() {
        statusList = getEm().createQuery("select s from Status s").getResultList();
        return statusList;
    }

    /**
     * @param statusList the statusList to set
     */
    public void setStatusList(List<Status> statusList) {
        this.statusList = statusList;
    }

    /**
     * @return the audit
     */
    public Audit getAudit() {
        return audit;
    }

    /**
     * @param audit the audit to set
     */
    public void setAudit(Audit audit) {
        this.audit = audit;
    }

    /**
     * @return the auditList
     */
    public List<Audit> getAuditList() {
        AuditList = getEm().createQuery("select a from Audit a").getResultList();
        return AuditList;
    }

    /**
     * @param auditList the auditList to set
     */
    public void setAuditList(List<Audit> auditList) {
        this.AuditList = auditList;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    public LineChartModel getBrowserModel() {
        return browserHistoryModel;
    }

    /**
     * @param browserHistoryModel the browserHistoryModel to set
     */
    public void setBrowserModel(LineChartModel browserHistoryModel) {
        this.browserHistoryModel = browserHistoryModel;
    }

    /**
     * @return the group1
     */
    public Usergroup getGroup1() {
        return group1;
    }

    /**
     * @param group1 the group1 to set
     */
    public void setGroup1(Usergroup group1) {
        this.group1 = group1;
    }

    /**
     * @return the group1List
     */
    public List<Usergroup> getGroup1List() {
        return group1List;
    }

    /**
     * @param group1List the group1List to set
     */
    public void setGroup1List(List<Usergroup> group1List) {
        this.group1List = group1List;
    }

    public Browserhistory getBrowser() {
        return Browser;
    }

    /**
     * @param Browser the Browser to set
     */
    public void setBrowser(Browserhistory Browser) {
        this.Browser = Browser;
    }

    /**
     * @return the BrowserList
     */
    public List<Browserhistory> getBrowserList() {
        BrowserList = em.createQuery("select b from Browserhistory b").getResultList();
        return BrowserList;
    }

    /**
     * @param BrowserList the BrowserList to set
     */
    public void setBrowserList(List<Browserhistory> BrowserList) {
        this.BrowserList = BrowserList;
    }

    /**
     * @return the remember
     */
    public Boolean getRemember() {
        return remember;
    }

    /**
     * @param remember the remember to set
     */
    public void setRemember(Boolean remember) {
        this.remember = remember;
    }

}
