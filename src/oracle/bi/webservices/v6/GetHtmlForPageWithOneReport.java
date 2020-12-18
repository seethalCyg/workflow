/**
 * GetHtmlForPageWithOneReport.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package oracle.bi.webservices.v6;

public class GetHtmlForPageWithOneReport  implements java.io.Serializable {
    private java.lang.String reportID;

    private oracle.bi.webservices.v6.ReportRef report;

    private java.lang.String reportViewName;

    private oracle.bi.webservices.v6.ReportParams reportParams;

    private oracle.bi.webservices.v6.ReportHTMLOptions reportOptions;

    private oracle.bi.webservices.v6.StartPageParams pageParams;

    private java.lang.String sessionID;

    public GetHtmlForPageWithOneReport() {
    }

    public GetHtmlForPageWithOneReport(
           java.lang.String reportID,
           oracle.bi.webservices.v6.ReportRef report,
           java.lang.String reportViewName,
           oracle.bi.webservices.v6.ReportParams reportParams,
           oracle.bi.webservices.v6.ReportHTMLOptions reportOptions,
           oracle.bi.webservices.v6.StartPageParams pageParams,
           java.lang.String sessionID) {
           this.reportID = reportID;
           this.report = report;
           this.reportViewName = reportViewName;
           this.reportParams = reportParams;
           this.reportOptions = reportOptions;
           this.pageParams = pageParams;
           this.sessionID = sessionID;
    }


    /**
     * Gets the reportID value for this GetHtmlForPageWithOneReport.
     * 
     * @return reportID
     */
    public java.lang.String getReportID() {
        return reportID;
    }


    /**
     * Sets the reportID value for this GetHtmlForPageWithOneReport.
     * 
     * @param reportID
     */
    public void setReportID(java.lang.String reportID) {
        this.reportID = reportID;
    }


    /**
     * Gets the report value for this GetHtmlForPageWithOneReport.
     * 
     * @return report
     */
    public oracle.bi.webservices.v6.ReportRef getReport() {
        return report;
    }


    /**
     * Sets the report value for this GetHtmlForPageWithOneReport.
     * 
     * @param report
     */
    public void setReport(oracle.bi.webservices.v6.ReportRef report) {
        this.report = report;
    }


    /**
     * Gets the reportViewName value for this GetHtmlForPageWithOneReport.
     * 
     * @return reportViewName
     */
    public java.lang.String getReportViewName() {
        return reportViewName;
    }


    /**
     * Sets the reportViewName value for this GetHtmlForPageWithOneReport.
     * 
     * @param reportViewName
     */
    public void setReportViewName(java.lang.String reportViewName) {
        this.reportViewName = reportViewName;
    }


    /**
     * Gets the reportParams value for this GetHtmlForPageWithOneReport.
     * 
     * @return reportParams
     */
    public oracle.bi.webservices.v6.ReportParams getReportParams() {
        return reportParams;
    }


    /**
     * Sets the reportParams value for this GetHtmlForPageWithOneReport.
     * 
     * @param reportParams
     */
    public void setReportParams(oracle.bi.webservices.v6.ReportParams reportParams) {
        this.reportParams = reportParams;
    }


    /**
     * Gets the reportOptions value for this GetHtmlForPageWithOneReport.
     * 
     * @return reportOptions
     */
    public oracle.bi.webservices.v6.ReportHTMLOptions getReportOptions() {
        return reportOptions;
    }


    /**
     * Sets the reportOptions value for this GetHtmlForPageWithOneReport.
     * 
     * @param reportOptions
     */
    public void setReportOptions(oracle.bi.webservices.v6.ReportHTMLOptions reportOptions) {
        this.reportOptions = reportOptions;
    }


    /**
     * Gets the pageParams value for this GetHtmlForPageWithOneReport.
     * 
     * @return pageParams
     */
    public oracle.bi.webservices.v6.StartPageParams getPageParams() {
        return pageParams;
    }


    /**
     * Sets the pageParams value for this GetHtmlForPageWithOneReport.
     * 
     * @param pageParams
     */
    public void setPageParams(oracle.bi.webservices.v6.StartPageParams pageParams) {
        this.pageParams = pageParams;
    }


    /**
     * Gets the sessionID value for this GetHtmlForPageWithOneReport.
     * 
     * @return sessionID
     */
    public java.lang.String getSessionID() {
        return sessionID;
    }


    /**
     * Sets the sessionID value for this GetHtmlForPageWithOneReport.
     * 
     * @param sessionID
     */
    public void setSessionID(java.lang.String sessionID) {
        this.sessionID = sessionID;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetHtmlForPageWithOneReport)) return false;
        GetHtmlForPageWithOneReport other = (GetHtmlForPageWithOneReport) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.reportID==null && other.getReportID()==null) || 
             (this.reportID!=null &&
              this.reportID.equals(other.getReportID()))) &&
            ((this.report==null && other.getReport()==null) || 
             (this.report!=null &&
              this.report.equals(other.getReport()))) &&
            ((this.reportViewName==null && other.getReportViewName()==null) || 
             (this.reportViewName!=null &&
              this.reportViewName.equals(other.getReportViewName()))) &&
            ((this.reportParams==null && other.getReportParams()==null) || 
             (this.reportParams!=null &&
              this.reportParams.equals(other.getReportParams()))) &&
            ((this.reportOptions==null && other.getReportOptions()==null) || 
             (this.reportOptions!=null &&
              this.reportOptions.equals(other.getReportOptions()))) &&
            ((this.pageParams==null && other.getPageParams()==null) || 
             (this.pageParams!=null &&
              this.pageParams.equals(other.getPageParams()))) &&
            ((this.sessionID==null && other.getSessionID()==null) || 
             (this.sessionID!=null &&
              this.sessionID.equals(other.getSessionID())));
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
        if (getReportID() != null) {
            _hashCode += getReportID().hashCode();
        }
        if (getReport() != null) {
            _hashCode += getReport().hashCode();
        }
        if (getReportViewName() != null) {
            _hashCode += getReportViewName().hashCode();
        }
        if (getReportParams() != null) {
            _hashCode += getReportParams().hashCode();
        }
        if (getReportOptions() != null) {
            _hashCode += getReportOptions().hashCode();
        }
        if (getPageParams() != null) {
            _hashCode += getPageParams().hashCode();
        }
        if (getSessionID() != null) {
            _hashCode += getSessionID().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetHtmlForPageWithOneReport.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">getHtmlForPageWithOneReport"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reportID");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "reportID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("report");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "report"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "ReportRef"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reportViewName");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "reportViewName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reportParams");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "reportParams"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "ReportParams"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reportOptions");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "reportOptions"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "ReportHTMLOptions"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pageParams");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "pageParams"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "StartPageParams"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sessionID");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "sessionID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
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
