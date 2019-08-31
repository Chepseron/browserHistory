/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amon.db;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Amon.Sabul
 */
@Entity
@Table(name = "browserhistory")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Browserhistory.findAll", query = "SELECT b FROM Browserhistory b")
    , @NamedQuery(name = "Browserhistory.findByIdbrowserHistory", query = "SELECT b FROM Browserhistory b WHERE b.idbrowserHistory = :idbrowserHistory")
    , @NamedQuery(name = "Browserhistory.findByEmailID", query = "SELECT b FROM Browserhistory b WHERE b.emailID = :emailID")
    , @NamedQuery(name = "Browserhistory.findByBrowser", query = "SELECT b FROM Browserhistory b WHERE b.browser = :browser")
    , @NamedQuery(name = "Browserhistory.findByStatus", query = "SELECT b FROM Browserhistory b WHERE b.status = :status")
    , @NamedQuery(name = "Browserhistory.findByCreatedOn", query = "SELECT b FROM Browserhistory b WHERE b.createdOn = :createdOn")})
public class Browserhistory implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idbrowserHistory")
    private Integer idbrowserHistory;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "emailID")
    private String emailID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 300)
    @Column(name = "browser")
    private String browser;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "status")
    private String status;
    @Basic(optional = false)
    @NotNull
    @Column(name = "createdOn")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;

    public Browserhistory() {
    }

    public Browserhistory(Integer idbrowserHistory) {
        this.idbrowserHistory = idbrowserHistory;
    }

    public Browserhistory(Integer idbrowserHistory, String emailID, String browser, String status, Date createdOn) {
        this.idbrowserHistory = idbrowserHistory;
        this.emailID = emailID;
        this.browser = browser;
        this.status = status;
        this.createdOn = createdOn;
    }

    public Integer getIdbrowserHistory() {
        return idbrowserHistory;
    }

    public void setIdbrowserHistory(Integer idbrowserHistory) {
        this.idbrowserHistory = idbrowserHistory;
    }

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idbrowserHistory != null ? idbrowserHistory.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Browserhistory)) {
            return false;
        }
        Browserhistory other = (Browserhistory) object;
        if ((this.idbrowserHistory == null && other.idbrowserHistory != null) || (this.idbrowserHistory != null && !this.idbrowserHistory.equals(other.idbrowserHistory))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.amon.db.Browserhistory[ idbrowserHistory=" + idbrowserHistory + " ]";
    }
    
}
