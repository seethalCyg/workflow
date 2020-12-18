/**
 * JobStats.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package oracle.bi.webservices.v6;

public class JobStats  implements java.io.Serializable {
    private java.math.BigInteger jobID;

    private java.lang.String jobType;

    private java.lang.String jobUser;

    private oracle.bi.webservices.v6.JobStatsJobState jobState;

    private java.lang.String jobTotalMilliSec;

    private java.util.Calendar jobStartedTime;

    private java.util.Calendar jobFinishedTime;

    private java.lang.String jobIsCancelling;

    private oracle.bi.webservices.v6.SAWException exception;

    public JobStats() {
    }

    public JobStats(
           java.math.BigInteger jobID,
           java.lang.String jobType,
           java.lang.String jobUser,
           oracle.bi.webservices.v6.JobStatsJobState jobState,
           java.lang.String jobTotalMilliSec,
           java.util.Calendar jobStartedTime,
           java.util.Calendar jobFinishedTime,
           java.lang.String jobIsCancelling,
           oracle.bi.webservices.v6.SAWException exception) {
           this.jobID = jobID;
           this.jobType = jobType;
           this.jobUser = jobUser;
           this.jobState = jobState;
           this.jobTotalMilliSec = jobTotalMilliSec;
           this.jobStartedTime = jobStartedTime;
           this.jobFinishedTime = jobFinishedTime;
           this.jobIsCancelling = jobIsCancelling;
           this.exception = exception;
    }


    /**
     * Gets the jobID value for this JobStats.
     * 
     * @return jobID
     */
    public java.math.BigInteger getJobID() {
        return jobID;
    }


    /**
     * Sets the jobID value for this JobStats.
     * 
     * @param jobID
     */
    public void setJobID(java.math.BigInteger jobID) {
        this.jobID = jobID;
    }


    /**
     * Gets the jobType value for this JobStats.
     * 
     * @return jobType
     */
    public java.lang.String getJobType() {
        return jobType;
    }


    /**
     * Sets the jobType value for this JobStats.
     * 
     * @param jobType
     */
    public void setJobType(java.lang.String jobType) {
        this.jobType = jobType;
    }


    /**
     * Gets the jobUser value for this JobStats.
     * 
     * @return jobUser
     */
    public java.lang.String getJobUser() {
        return jobUser;
    }


    /**
     * Sets the jobUser value for this JobStats.
     * 
     * @param jobUser
     */
    public void setJobUser(java.lang.String jobUser) {
        this.jobUser = jobUser;
    }


    /**
     * Gets the jobState value for this JobStats.
     * 
     * @return jobState
     */
    public oracle.bi.webservices.v6.JobStatsJobState getJobState() {
        return jobState;
    }


    /**
     * Sets the jobState value for this JobStats.
     * 
     * @param jobState
     */
    public void setJobState(oracle.bi.webservices.v6.JobStatsJobState jobState) {
        this.jobState = jobState;
    }


    /**
     * Gets the jobTotalMilliSec value for this JobStats.
     * 
     * @return jobTotalMilliSec
     */
    public java.lang.String getJobTotalMilliSec() {
        return jobTotalMilliSec;
    }


    /**
     * Sets the jobTotalMilliSec value for this JobStats.
     * 
     * @param jobTotalMilliSec
     */
    public void setJobTotalMilliSec(java.lang.String jobTotalMilliSec) {
        this.jobTotalMilliSec = jobTotalMilliSec;
    }


    /**
     * Gets the jobStartedTime value for this JobStats.
     * 
     * @return jobStartedTime
     */
    public java.util.Calendar getJobStartedTime() {
        return jobStartedTime;
    }


    /**
     * Sets the jobStartedTime value for this JobStats.
     * 
     * @param jobStartedTime
     */
    public void setJobStartedTime(java.util.Calendar jobStartedTime) {
        this.jobStartedTime = jobStartedTime;
    }


    /**
     * Gets the jobFinishedTime value for this JobStats.
     * 
     * @return jobFinishedTime
     */
    public java.util.Calendar getJobFinishedTime() {
        return jobFinishedTime;
    }


    /**
     * Sets the jobFinishedTime value for this JobStats.
     * 
     * @param jobFinishedTime
     */
    public void setJobFinishedTime(java.util.Calendar jobFinishedTime) {
        this.jobFinishedTime = jobFinishedTime;
    }


    /**
     * Gets the jobIsCancelling value for this JobStats.
     * 
     * @return jobIsCancelling
     */
    public java.lang.String getJobIsCancelling() {
        return jobIsCancelling;
    }


    /**
     * Sets the jobIsCancelling value for this JobStats.
     * 
     * @param jobIsCancelling
     */
    public void setJobIsCancelling(java.lang.String jobIsCancelling) {
        this.jobIsCancelling = jobIsCancelling;
    }


    /**
     * Gets the exception value for this JobStats.
     * 
     * @return exception
     */
    public oracle.bi.webservices.v6.SAWException getException() {
        return exception;
    }


    /**
     * Sets the exception value for this JobStats.
     * 
     * @param exception
     */
    public void setException(oracle.bi.webservices.v6.SAWException exception) {
        this.exception = exception;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof JobStats)) return false;
        JobStats other = (JobStats) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.jobID==null && other.getJobID()==null) || 
             (this.jobID!=null &&
              this.jobID.equals(other.getJobID()))) &&
            ((this.jobType==null && other.getJobType()==null) || 
             (this.jobType!=null &&
              this.jobType.equals(other.getJobType()))) &&
            ((this.jobUser==null && other.getJobUser()==null) || 
             (this.jobUser!=null &&
              this.jobUser.equals(other.getJobUser()))) &&
            ((this.jobState==null && other.getJobState()==null) || 
             (this.jobState!=null &&
              this.jobState.equals(other.getJobState()))) &&
            ((this.jobTotalMilliSec==null && other.getJobTotalMilliSec()==null) || 
             (this.jobTotalMilliSec!=null &&
              this.jobTotalMilliSec.equals(other.getJobTotalMilliSec()))) &&
            ((this.jobStartedTime==null && other.getJobStartedTime()==null) || 
             (this.jobStartedTime!=null &&
              this.jobStartedTime.equals(other.getJobStartedTime()))) &&
            ((this.jobFinishedTime==null && other.getJobFinishedTime()==null) || 
             (this.jobFinishedTime!=null &&
              this.jobFinishedTime.equals(other.getJobFinishedTime()))) &&
            ((this.jobIsCancelling==null && other.getJobIsCancelling()==null) || 
             (this.jobIsCancelling!=null &&
              this.jobIsCancelling.equals(other.getJobIsCancelling()))) &&
            ((this.exception==null && other.getException()==null) || 
             (this.exception!=null &&
              this.exception.equals(other.getException())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getJobID() != null) {
            _hashCode += getJobID().hashCode();
        }
        if (getJobType() != null) {
            _hashCode += getJobType().hashCode();
        }
        if (getJobUser() != null) {
            _hashCode += getJobUser().hashCode();
        }
        if (getJobState() != null) {
            _hashCode += getJobState().hashCode();
        }
        if (getJobTotalMilliSec() != null) {
            _hashCode += getJobTotalMilliSec().hashCode();
        }
        if (getJobStartedTime() != null) {
            _hashCode += getJobStartedTime().hashCode();
        }
        if (getJobFinishedTime() != null) {
            _hashCode += getJobFinishedTime().hashCode();
        }
        if (getJobIsCancelling() != null) {
            _hashCode += getJobIsCancelling().hashCode();
        }
        if (getException() != null) {
            _hashCode += getException().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(JobStats.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "JobStats"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("jobID");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "jobID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("jobType");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "jobType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("jobUser");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "jobUser"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("jobState");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "jobState"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">JobStats>jobState"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("jobTotalMilliSec");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "jobTotalMilliSec"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("jobStartedTime");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "jobStartedTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("jobFinishedTime");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "jobFinishedTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("jobIsCancelling");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "jobIsCancelling"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("exception");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "exception"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "SAWException"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
